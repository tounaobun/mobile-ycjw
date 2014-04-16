/**
 * @Author Benson
 * @Time 2013-11-22
 */
package zjut.soft.finalwork.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import zjut.soft.finalwork.util.Constant;
import android.content.Context;
import android.util.Log;

/**
 * 原创学生管理器
 * 
 * @author tsw
 * 
 */
public class YCStudentManager implements StudentManager {

	@Override
	public String login(Context context, String url,String userName, String password) {

		try {
			// 设置POST参数
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("Cbo_LX", "学生"));
			urlParameters.add(new BasicNameValuePair("Txt_UserName", userName));
			urlParameters.add(new BasicNameValuePair("Txt_Password", password));
			urlParameters.add(new BasicNameValuePair("Img_DL.x", "0"));
			urlParameters.add(new BasicNameValuePair("Img_DL.y", "0"));
			String loginUrl = url + Constant.loginContext;

			Elements elements = Jsoup.connect(loginUrl).get().select("input");

			for (Element element : elements) {
				if ("__EVENTTARGET".equals(element.attr("name"))) {
					urlParameters.add(new BasicNameValuePair("__EVENTTARGET",
							element.val()));
				} else if ("__EVENTARGUMENT".equals(element.attr("name"))) {
					urlParameters.add(new BasicNameValuePair("__EVENTARGUMENT",
							element.val()));
				} else if ("__VIEWSTATE".equals(element.attr("name"))) {
					urlParameters.add(new BasicNameValuePair("__VIEWSTATE",
							element.val()));
				}
			}

			// 调试信息
			for (NameValuePair pair : urlParameters) {
				Log.i(YCStudentManager.class.getName(), pair.getName() + ":"
						+ pair.getValue());
			}
			// POST参数设置完毕

			HttpPost post = new HttpPost(loginUrl);
			post.setEntity(new UrlEncodedFormEntity(urlParameters,
					Constant.ENCODING));
			HttpResponse response = null;

			YCApplication application = (YCApplication) context
					.getApplicationContext();
			response = application.getClient().execute(post);
			// 打印cookie
			List<Cookie> cookies = application.getClient().getCookieStore()
					.getCookies();
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), Constant.ENCODING), 8192);

			String line = null;
			boolean mark = false; // 判断是否登录标志
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				// 读到title标签就可以判断是否登录成功
				if (line.contains("<title>学生管理</title>")) {
					mark = true;
					// 登录成功保存用户名和密码
					YCApplication app = (YCApplication) context
							.getApplicationContext();
					app.put("username", userName);
					app.put("password", password);
				} else if (line.contains("<title>原创教务管理系统</title>")) {
					mark = false;
					return "密码错误，忘记密码请到院办初始化!";
				}
				// // 登录成功后寻找用户的姓名
				if (mark) {
					if (line.contains("权限:学生")) {
						String name = line.substring(line.indexOf("(") + 1,
								line.indexOf(")"));
						YCApplication app = (YCApplication) context
								.getApplicationContext();
						app.put("name", name);
						break;
					}
				}
			}
		} catch (SocketTimeoutException ste) {
			return "连接超时，请检查网络!";
		} catch (IOException ioe) {
			return ioe.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}

		return "登录成功";
	}

}
