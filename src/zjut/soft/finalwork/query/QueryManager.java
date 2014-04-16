/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.query;

/**
 * ѧ���ɼ���ѯ������
 * @author tsw
 *
 */
public interface QueryManager {

	GradeQuery getGradeQuery();
	
	TestArrangementQuery getTestArrangementQuery();
	
	DevelopmentScheduleQuery getDevelopmentScheduleQuery();
	
	CourseTableQuery getStudentCourseTableQuery();
}
