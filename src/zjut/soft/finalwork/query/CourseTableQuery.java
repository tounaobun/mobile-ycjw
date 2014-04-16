/**
  * @Author Benson
  * @Time 2013-11-28
  */
package zjut.soft.finalwork.query;

import java.util.List;

import zjut.soft.finalwork.beans.TestArrangeCourse;
import android.content.Context;

public interface CourseTableQuery {

	/**
	 * ����ѧ�ڲ����ſ����
	 * @param context ������
	 * @param semester ѧ��
	 * @return �ſ����HTML
	 * @throws Exception 
	 */
	String getCourseTableBySemester(Context context,String semester) throws Exception;
	
	/**
	 * ����ѧ���б�
	 * @return
	 */
	public List<String> getSemesters(Context context) throws Exception;
}
