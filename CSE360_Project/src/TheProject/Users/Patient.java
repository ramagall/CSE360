package TheProject.Users;

public class Patient {
	  /* Attributes */
	  private String firstName;
	  private String lastName;
	  private String username;
	  private String password;
	  private String dob;
	  
	  public Patient(String firstName, String lastName, String username, String password, String dob){
		  this.firstName = firstName;
		  this.lastName = lastName;
		  this.username = username;
		  this.password = password;
		  this.dob = dob;
	  }
	  
	  public Patient(String [] data) {
		 firstName = data[0];
		 lastName = data[1];
		 username = data[2];
		 password = data[3];
		 dob = data[4];
	  }
	  
	  /* Methods */
	  
	  public String getPatientInfo() {
		  return firstName + "|" + lastName + "|" + username + "|" + password + "|" + dob;
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