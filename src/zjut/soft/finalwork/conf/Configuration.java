/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.conf;

/**
 * 用户管理
 * @author tsw
 *
 */
public interface Configuration {

	/**
	 * 修改密码
	 * @param oldPass 旧密码
	 * @param newPass 新密码
	 * @return 修改成功返回true
	 */
	boolean modifyPassword(String oldPass,String newPass);
}
