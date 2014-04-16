/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.query;

public class StudentQueryManager implements QueryManager {

	@Override
	public GradeQuery getGradeQuery() {
		return new StudentGradeQuery();
	}

	@Override
	public TestArrangementQuery getTestArrangementQuery() {
		return new StudentTestArrangementQuery();
	}

	@Override
	public DevelopmentScheduleQuery getDevelopmentScheduleQuery() {
		return new StudentDevelopmentScheduleQuery();
	}

	@Override
	public CourseTableQuery getStudentCourseTableQuery() {
		return new StudentCourseTableQuery();
	}

}
