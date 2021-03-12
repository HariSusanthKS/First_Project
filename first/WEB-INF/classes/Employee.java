import java.sql.Date;
public class Employee {
private int id,phone;
private String firstName,lastName,email,fathername,mothername,address;
private Date dob;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public int getPhno(){
		return phone;
	}
	public void setPhno(int phone){
		this.phone=phone;
	}
	public String getMotherName() {
		return mothername;
	}
	public void setMotherName(String mothername) {
		this.mothername = mothername;
	}
	public String getFatherName() {
		return fathername;
	}
	public void setFatherName(String fathername) {
		this.fathername = fathername;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDOB() {
		return dob;
	}
	public void setDOB(Date dob) {
		this.dob = dob;
	}
}
