package TheProject.Users;

import java.util.HashMap;
import java.time.*;
import java.util.Map;

public class Patient {
	  /* Attributes */
	  private String firstName;
	  private String lastName;
	  private String username;
	  private String password;
	  private String dob;
	  private String insuranceType;
	  private int age;
	  public Map <String, String[]> visits;
	  
	  public Patient(String username, String password) {
		  this.username = username;
		  this.password = password;
		  visits = new HashMap<String, String[]>();
	  }
	  
	  public Patient(String firstName, String lastName, String username, String password, String dob, String insuranceType){
		  this.firstName = firstName;
		  this.lastName = lastName;
		  this.username = username;
		  this.password = password;
		  this.dob = dob;
		  this.insuranceType = insuranceType;
		  visits = new HashMap<String, String[]>();
	  }
	  
	  
	  public Patient(String [] data) {
		 firstName = data[0];
		 lastName = data[1];
		 username = data[2];
		 password = data[3];
		 dob = data[4];
		 insuranceType = data[5];
		 visits = new HashMap<String, String[]>();
	  }
	  
	  public void setVisit(String [] data) { 
		  String[] visit = new String[11];
		  visit[0] = data[0]; // username
		  visit[1] = data[1]; // date
		  visit[2] = data[2]; // weight
		  visit[3] = data[3]; // temp
		  visit[4] = data[4]; // height
		  visit[5] = data[5]; // B.P.
		  visit[6] = data[6]; // Allergies
		  visit[7] = data[7]; // History
		  visit[8] = data[8]; // vax records
		  visit[9] = data[9]; //  prescriptions.
		  visit[10] = data[10]; // reason for visit
		  visits.put(visit[1], visit);
	  }
	  
	  public String[] getVisit(String date) {
		  
		  return visits.get(date);
	  }
	  
	  /* Methods */
	  public Map <String, String[]> getAllVisits()
	  {
		  return visits;
	  }
	  
	  public String getPatientInfo() {
		  return firstName + "~" + lastName + "~" + username + "~" + password + "~" + dob + "~" + insuranceType;
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
	  

	  public int getAge(){
		  
		  LocalDate currentDate = LocalDate.now();
	       
      	String[] values = dob.split("/");
  	  int month = Integer.parseInt(values[0]);
       int day = Integer.parseInt(values[1]);
      int year = Integer.parseInt(values[2]);
  		  LocalDate birthday = LocalDate.of(year, month, day);
  		  Period dateDifference = Period.between(birthday, currentDate);      	
		  
  		  age = dateDifference.getYears();
  		  return age;
	  }
	  
	  public void setInsuranceType(String insuranceType) {
		  this.insuranceType = insuranceType;
	  }
	  
	  public String getInsuranceType() {
		  return insuranceType;
	  }
	}
