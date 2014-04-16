/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.beans;

/**
 * 课程信息
 * @author tsw
 *
 */
public class CourseInfo {

	/**
	 * 实际学期 
	 */
	private String semester;
	/**
	 * 课程名称
	 */
	private String courseName;
	/**
	 * 考试性质(类型)
	 */
	private String type; 
	/**
	 * 成绩(包括优秀,良好,中等,及格等)
	 */
	private String grade;
	/**
	 * 学时(包括1周，为空等)
	 */
	private String classes;
	/**
	 * 学分(可能为空)
	 */
	private String credit;
	

	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	@Override
	public String toString() {
		return "CourseInfo [semester=" + semester + ", courseName="
				+ courseName + ", type=" + type + ", grade=" + grade
				+ ", classes=" + classes + ", credit=" + credit + "]";
	}
	
}
