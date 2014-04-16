/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.core;

import android.content.Context;

/**
 * 学生管理器
 * @author tsw
 *
 */
public interface StudentManager {

	/**
	 * 学生登录
	 * @param context 应用上下文
	 * @param userName 用户名
	 * @param password 密码
	 * @return 登录返回信息
	 */
	String login(Context context,String url,String userName,String password);
	
}
