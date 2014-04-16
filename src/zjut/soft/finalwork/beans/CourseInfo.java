/**
  * @Author Benson
  * @Time 2013-11-22
  */
package zjut.soft.finalwork.beans;

/**
 * �γ���Ϣ
 * @author tsw
 *
 */
public class CourseInfo {

	/**
	 * ʵ��ѧ�� 
	 */
	private String semester;
	/**
	 * �γ�����
	 */
	private String courseName;
	/**
	 * ��������(����)
	 */
	private String type; 
	/**
	 * �ɼ�(��������,����,�е�,�����)
	 */
	private String grade;
	/**
	 * ѧʱ(����1�ܣ�Ϊ�յ�)
	 */
	private String classes;
	/**
	 * ѧ��(����Ϊ��)
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
