package TheProject.Users;

public class Doctor {
	  /* Attributes */
	  private String firstName;
	  private String lastName;
	  private String username;
	  private String password;
	  
	  public Doctor(String username, String password) {
		  this.username = username;
		  this.password = password;
	  }
	  
	  public Doctor(String firstName, String lastName, String username, String password){
		  this.firstName = firstName;
		  this.lastName = lastName;
		  this.username = username;
		  this.password = password;
	  }
	  
	  public Doctor(String [] data) {
		 firstName = data[0];
		 lastName = data[1];
		 username = data[2];
		 password = data[3];
	  }
	  
	  /* Methods */
	  
	  public String getNurseInfo() {
		  return firstName + "~" + lastName + "~" + username + "~" + password;
	  }
	  public void setFirstName(String update) {
	    firstName = update;
	  }

	  public String getFirstName() {
	    return firstName;
	  }
	  //
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

	  
}