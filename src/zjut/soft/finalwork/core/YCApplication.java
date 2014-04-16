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
 * 原创应用(一个应用对应一个Application，和程序同死亡)
 * @author tsw
 *
 */
public class YCApplication extends Application {

	/**
	 * HttpClient
	 */
	private DefaultHttpClient client;
	
	/**
	 * 缓存有用的数据，比如用户名，密码，姓名等。
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
	 * 系统初始化时会新建一个应用对象，开发者不需要手动新建该对象
	 * 可通过getApplication或者getApplicationContext获取系统新建的对象
	 */
    public YCApplication() {
		Log.d(YCApplication.this.getClass().getName(),"系统实例化一个单例应用对象");
		
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
