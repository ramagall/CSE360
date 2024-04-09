package TheProject.Records;

import TheProject.Users.Patient;
import TheProject.FileHandling.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PatientRecords {
	String INFOPATH = "UserRecords/Patients/PatientInfo";
	String VISITPATH = "UserRecords/Patients/PatientVisits";
	public Map <String, Patient> patientList;
	
	public PatientRecords() {
		patientList = new HashMap<String, Patient>();
		File dir = new File(INFOPATH);
		for(File file : dir.listFiles()) {
			AttemptedLoad load = FileHandler.loadFile(file.getPath());
			if(load.loaded == false) {
				continue;
			}
			Patient newPatient = new Patient(load.data.get(0));
			System.out.println(newPatient.getPass());
			patientList.put(newPatient.getUser(), newPatient);
			
		}
		/*
		File dir2 = new File(VISITPATH);
		for(File file : dir2.listFiles()) {
			AttemptedLoad load2 = FileHandler.loadFile(file.getPath());
			if(load2.loaded == false) {
				continue;
			}
		}*/
	}
	
	public Patient searchPatient(String userName) {
		return patientList.get(userName);
	}
	
	public void createNewPatient(Patient newPatient) {
		patientList.put(newPatient.getUser(), newPatient);
		File file = FileHandler.getFile(newPatient.getUser() + "_info", INFOPATH);
		FileHandler.writeToFile(file, newPatient.getPatientInfo());
	}
	
	public void createVisit(Patient newPatient, String[] visit) {
		patientList.put(newPatient.getUser(), newPatient);
		File file = FileHandler.getFile(newPatient.getUser() + "_visit", VISITPATH);
		FileHandler.writeToFile(file, newPatient.getVisit(visit[0]));
	}
	
	public void updateVisit() {
		
	}
}