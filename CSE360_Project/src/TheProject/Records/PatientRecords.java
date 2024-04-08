package TheProject.Records;

import TheProject.Users.Patient;
import TheProject.FileHandling.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PatientRecords {
	String PATH = "UserRecords/Patients";
	public Map <String, Patient> patientList;
	
	public PatientRecords() {
		patientList = new HashMap<String, Patient>();
		File dir = new File(PATH);
		for(File file : dir.listFiles()) {
			AttemptedLoad load = FileHandler.loadFile(file.getPath());
			if(load.loaded == false) {
				continue;
			}
			Patient newPatient = new Patient(load.data.get(0));
			patientList.put(newPatient.getUser(), newPatient);
			
		}
	}
	
	public Patient searchPatient(String userName) {
		return patientList.get(userName);
	}
	
	public void createNewPatient(Patient newPatient) {
		patientList.put(newPatient.getUser(), newPatient);
		File file = FileHandler.getFile(newPatient.getUser() + "_info", PATH);
		FileHandler.writeToFile(file, newPatient.getPatientInfo());
	}
}