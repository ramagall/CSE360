package TheProject.Users;

import java.util.HashMap;
import java.util.Map;

public class Patient {
	  /* Attributes */
	  private String firstName;
	  private String lastName;
	  private String username;
	  private String password;
	  private String dob;
	  private Map <String, String[]> visits;
	  
	  public Patient(String username, String password) {
		  this.username = username;
		  this.password = password;
		  visits = new HashMap<String, String[]>();
	  }
	  
	  public Patient(String firstName, String lastName, String username, String password, String dob){
		  this.firstName = firstName;
		  this.lastName = lastName;
		  this.username = username;
		  this.password = password;
		  this.dob = dob;
		  visits = new HashMap<String, String[]>();
	  }
	  
	  public Patient(String [] data) {
		 firstName = data[0];
		 lastName = data[1];
		 username = data[2];
		 password = data[3];
		 dob = data[4];
		 visits = new HashMap<String, String[]>();
	  }
	  
	  public void getVisit(String [] data) { 
		  String[] visit = new String[10];
		  visit[0] = data[0]; // date
		  visit[1] = data[1]; // temp
		  visit[2] = data[2]; // height
		  visit[3] = data[3]; // B.P.
		  visit[4] = data[4]; // allergies
		  visit[5] = data[5]; // history
		  visit[6] = data[6]; // vax records.
		  visit[7] = data[7]; // prescriptions
		  visit[8] = data[8]; // weight
		  visits.put(visit[0], visit);
	  }
	  
	  /* Methods */
	  
	  public String getPatientInfo() {
		  return firstName + "~" + lastName + "~" + username + "~" + password + "~" + dob;
	  }
	  public void setFirstName(String update) {
	    firstName = update;
	  }

	  public String getFirstName() {
	    return firstName;
	  }
	  
	  public void setlastName(String update) {
		    lastName = update;
		  }

		  public String getLastName() {
		    return lastName;
		  }

	  public void setUser(String update) {
	    username = update;
	  }

	  public String getUser() {
	    return username;
	  }

	  public void setPass(String update) {
	    password = update;
	  }

	  public String getPass() {
	    return password;
	  }

	  public void setDOB(String update) {
	    dob = update;
	  }

	  public String getDOB() {
	    return dob;
	  }
	}
