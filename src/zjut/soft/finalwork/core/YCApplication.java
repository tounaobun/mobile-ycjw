/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Application;
import android.util.Log;

/**
 * ԭ��Ӧ��(һ��Ӧ�ö�Ӧһ��Application���ͳ���ͬ����)
 * @author tsw
 *
 */
public class YCApplication extends Application {

	/**
	 * HttpClient
	 */
	private DefaultHttpClient client;
	
	/**
	 * �������õ����ݣ������û��������룬�����ȡ�
	 * <ul>
	 * 	<li>username</li>
	 *  <li>password</li>
	 *  <li>name</li>
	 *  <li>selectedIp</li>
	 *  <li>className</li>
	 *  <li>portrait</li>
	 *  <li>selected_semester_index</li>
	 * </ul>
	 */
	private Map<String,Object> values;
	
	/**
	 * ϵͳ��ʼ��ʱ���½�һ��Ӧ�ö��󣬿����߲���Ҫ�ֶ��½��ö���
	 * ��ͨ��getApplication����getApplicationContext��ȡϵͳ�½��Ķ���
	 */
    public YCApplication() {
		Log.d(YCApplication.this.getClass().getName(),"ϵͳʵ����һ������Ӧ�ö���");
		
		HttpParams httpParameters = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
	    HttpConnectionParams.setSoTimeout(httpParameters, 10000); 
		client = new DefaultHttpClient(httpParameters);
		
		values = new HashMap<String, Object>();
		
	}

	public DefaultHttpClient getClient() {
		return client;
	}
	
	public void put(String key,Object value) {
		values.put(key, value);
	}
	
	public Object get(String key) {
		return values.get(key);
	}
	
	public void clearAllCachedData() {
		if(values != null) {
			values.clear();
		}
	}
}
