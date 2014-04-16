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
 * ԭ��ѧ��������
 * 
 * @author tsw
 * 
 */
public class YCStudentManager implements StudentManager {

	@Override
	public String login(Context context, String url,String userName, String password) {

		try {
			// ����POST����
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("Cbo_LX", "ѧ��"));
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

			// ������Ϣ
			for (NameValuePair pair : urlParameters) {
				Log.i(YCStudentManager.class.getName(), pair.getName() + ":"
						+ pair.getValue());
			}
			// POST�����������

			HttpPost post = new HttpPost(loginUrl);
			post.setEntity(new UrlEncodedFormEntity(urlParameters,
					Constant.ENCODING));
			HttpResponse response = null;

			YCApplication application = (YCApplication) context
					.getApplicationContext();
			response = application.getClient().execute(post);
			// ��ӡcookie
			List<Cookie> cookies = application.getClient().getCookieStore()
					.getCookies();
			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName() + ":" + cookie.getValue());
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), Constant.ENCODING), 8192);

			String line = null;
			boolean mark = false; // �ж��Ƿ��¼��־
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				// ����title��ǩ�Ϳ����ж��Ƿ��¼�ɹ�
				if (line.contains("<title>ѧ������</title>")) {
					mark = true;
					// ��¼�ɹ������û���������
					YCApplication app = (YCApplication) context
							.getApplicationContext();
					app.put("username", userName);
					app.put("password", password);
				} else if (line.contains("<title>ԭ���������ϵͳ</title>")) {
					mark = false;
					return "����������������뵽Ժ���ʼ��!";
				}
				// // ��¼�ɹ���Ѱ���û�������
				if (mark) {
					if (line.contains("Ȩ��:ѧ��")) {
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
			return "���ӳ�ʱ����������!";
		} catch (IOException ioe) {
			return ioe.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}

		return "��¼�ɹ�";
	}

}
