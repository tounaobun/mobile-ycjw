/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.core;

import android.content.Context;

/**
 * ѧ��������
 * @author tsw
 *
 */
public interface StudentManager {

	/**
	 * ѧ����¼
	 * @param context Ӧ��������
	 * @param userName �û���
	 * @param password ����
	 * @return ��¼������Ϣ
	 */
	String login(Context context,String url,String userName,String password);
	
}
