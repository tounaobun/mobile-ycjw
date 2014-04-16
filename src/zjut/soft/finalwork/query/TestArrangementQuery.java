/**
  * @Author Benson
  * @Time 2013-11-28
  */
package zjut.soft.finalwork.query;

import java.util.List;

import zjut.soft.finalwork.beans.TestArrangeCourse;
import android.content.Context;

public interface TestArrangementQuery {

	
	/**
	 * ����ѧ�ڲ����ſ����
	 * @param context ������
	 * @param semester ѧ��
	 * @return �ſ�����б�
	 * @throws Exception 
	 */
	List<TestArrangeCourse> getTestArrangementBySemester(Context context,String semester) throws Exception;
	
	/**
	 * ��һ�η��ص��ſ����
	 * @param context ������
	 * @return �ſ�����б�
	 * @throws Exception
	 */
	public List<TestArrangeCourse> getTestArrangementFirstTime(Context context) throws Exception;
	
	/**
	 * ����ѧ���б�
	 * @return
	 */
	public List<String> getSemesters(Context context) throws Exception;
}
