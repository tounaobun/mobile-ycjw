/**
 * @Author Benson
 * @Time 2013-11-28
 */
package zjut.soft.finalwork.query;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import zjut.soft.finalwork.beans.TestArrangeCourse;
import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.util.Constant;
import android.content.Context;

public class StudentTestArrangementQuery implements TestArrangementQuery {

	
	@Override
	public List<TestArrangeCourse> getTestArrangementFirstTime(Context context) throws Exception {
		try {
			
			YCApplication app = (YCApplication) context.getApplicationContext();
			String url = (String) app.get("selectedIp")
					+ Constant.testArrangeQuery;
			HttpGet request = new HttpGet(url);
			
			HttpResponse response = app.getClient().execute(request);
			InputStream is = response.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					Constant.ENCODING));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				System.out.println(temp);
			}
			
			List<TestArrangeCourse> courses = new ArrayList<TestArrangeCourse>();
			
			Document doc = Jsoup.parse(sb.toString());
			Elements table = doc.select("#DG_PTHasselect");
			Elements trs = table.select("tr");
			if(trs.size() > 1) {
				for(int i = 1; i < trs.size(); i++) {
					Elements spans = trs.get(i).select("span");
					if(spans.size() == 6) {
						TestArrangeCourse course = new TestArrangeCourse();
						course.setClassName(spans.get(0).html());
						course.setTeacherName(spans.get(1).html());
						course.setClassroomName(spans.get(5).html());
						course.setDayPeriod(spans.get(4).html());
						course.setTestDate(spans.get(3).html());
						course.setCourseName(spans.get(2).html());
						courses.add(course);
					}
				}
			}
			return courses;
		} catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<TestArrangeCourse> getTestArrangementBySemester(
			Context context, String semester) throws Exception {

		try {
			YCApplication app = (YCApplication) context.getApplicationContext();
			String url = (String) app.get("selectedIp")
					+ Constant.testArrangeQuery;
			HttpGet request = new HttpGet(url);

			HttpResponse response = app.getClient().execute(request);
			InputStream is = response.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					Constant.ENCODING));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				if (temp.contains("<script language")) {
					break; // 节约时间
				}
				sb.append(temp);
			}

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>(); // post  传入参数
			urlParameters.add(new BasicNameValuePair("Button1", "查询"));
			urlParameters.add(new BasicNameValuePair("Cbo_Xueqi", semester));
			
			Document doc = Jsoup.parse(sb.toString());
			Elements elements = doc.select("input");
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
			}  // post传参结束
			
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(urlParameters,
					Constant.ENCODING));

			response  = app.getClient().execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent(), Constant.ENCODING), 8192);

			sb = new StringBuilder();
			while ((temp = rd.readLine()) != null) {
				sb.append(temp);
				System.out.println(temp);
			}
			List<TestArrangeCourse> courses = new ArrayList<TestArrangeCourse>();
			
			doc = Jsoup.parse(sb.toString());
			Elements table = doc.select("#DG_PTHasselect");
			Elements trs = table.select("tr");
			if(trs.size() > 1) {
				for(int i = 1; i < trs.size(); i++) {
					Elements spans = trs.get(i).select("span");
					if(spans.size() == 6) {
						TestArrangeCourse course = new TestArrangeCourse();
						course.setClassName(spans.get(0).html());
						course.setTeacherName(spans.get(1).html());
						course.setClassroomName(spans.get(5).html());
						course.setDayPeriod(spans.get(4).html());
						course.setTestDate(spans.get(3).html());
						course.setCourseName(spans.get(2).html());
						courses.add(course);
					}
				}
			}
			return courses;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getSemesters(Context context) throws Exception {
		try {
			
			YCApplication app = (YCApplication) context.getApplicationContext();
			String url = (String) app.get("selectedIp")
					+ Constant.testArrangeQuery;
			HttpGet request = new HttpGet(url);
			
			HttpResponse response = app.getClient().execute(request);
			InputStream is = response.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					Constant.ENCODING));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				System.out.println(temp);
			}
			Document doc = Jsoup.parse(sb.toString());
			Elements table = doc.select("#Cbo_Xueqi");
			Elements options = table.select("option");
			
			List<String> semesterItems = new ArrayList<String>();
			for(int i = 0; i < options.size(); i++) {
				semesterItems.add(options.get(i).html());
				if(options.get(i).attr("selected").equals("selected")) {
					app.put("selected_semester_index", i);
				}
			}
			
			// 存储默认学期
			
			return semesterItems;
		} catch(Exception e) {
			throw new Exception(e);
		}
	}
}
