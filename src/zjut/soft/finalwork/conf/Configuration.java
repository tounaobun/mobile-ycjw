/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.conf;

/**
 * �û�����
 * @author tsw
 *
 */
public interface Configuration {

	/**
	 * �޸�����
	 * @param oldPass ������
	 * @param newPass ������
	 * @return �޸ĳɹ�����true
	 */
	boolean modifyPassword(String oldPass,String newPass);
}
