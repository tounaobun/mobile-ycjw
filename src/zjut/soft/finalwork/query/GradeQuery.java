/**
  * @Author Benson
  * @Time 2013-11-21
  */
package zjut.soft.finalwork.query;

import java.util.List;

import zjut.soft.finalwork.beans.CourseInfo;

/**
 * ѧ���ɼ���ѯ
 * @author tsw
 *
 */
public interface GradeQuery {

	/**
	 * ��ѧ�ڲ�ѯ��ͨ�ɼ�
	 * @param time ѧ��
	 * @return ���޿γ���Ϣ�б�
	 */
	List<CourseInfo> gradeQueryBySemester(String time);
	
	/**
	 * ��ѧ���ѯ��ͨ�ɼ�
	 * @param time ѧ��
	 * @return ���޿γ���Ϣ�б�
	 */
	List<CourseInfo> gradeQueryByAcademic(String time);	
}
