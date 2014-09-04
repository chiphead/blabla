package chiphead.model;

public class Person {
	private int emp_id;
	private int emp_yst_id;
	private String emp_name;
	private String emp_pwd;
	private int emp_aut = 2;
	private int team_id;
	private int inner_no;
	
	@Override 
	public String toString() {
	return this.emp_id + "#" + this.emp_yst_id + "#" + 
			this.emp_name + "#" + this.emp_pwd + "#" +
			this.emp_aut + "#" + this.team_id + "#" +
			this.inner_no; 
	}
	
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_yst_id(int emp_yst_id) {
		this.emp_yst_id = emp_yst_id;
	}
	public int getEmp_yst_id() {
		return emp_yst_id;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_pwd(String emp_pwd) {
		this.emp_pwd = emp_pwd;
	}
	public String getEmp_pwd() {
		return emp_pwd;
	}
	public void setEmp_aut(int emp_aut) {
		this.emp_aut = emp_aut;
	}
	public int getEmp_aut() {
		return emp_aut;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getInner_no() {
		return inner_no;
	}

	public void setInner_no(int innerNo) {
		inner_no = innerNo;
	}
	
}
