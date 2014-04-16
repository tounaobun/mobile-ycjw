/**
  * @Author Benson
  * @Time 2013-11-28
  */
package zjut.soft.finalwork.query;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import zjut.soft.finalwork.core.YCApplication;
import zjut.soft.finalwork.util.Constant;
import android.content.Context;

public class StudentDevelopmentScheduleQuery implements
		DevelopmentScheduleQuery {

	@Override
	public String getDevelopmentScheduleQueryInfo(Context context) throws Exception {

		try {
			YCApplication app = (YCApplication) context.getApplicationContext();
			String url = (String) app.get("selectedIp")
					+ Constant.developScheduleQuery;
			HttpGet request = new HttpGet(url);
			
			HttpResponse response = app.getClient().execute(request);
			InputStream is = response.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					Constant.ENCODING));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			
			Document doc = Jsoup.parse(sb.toString());
			Elements table = doc.select("#DG_GetGrjh");
			return table.toString();
		} catch(Exception e) {
			throw new Exception(e);
		}
	
	}
	
}
