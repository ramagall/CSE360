package TheProject.Records;

import TheProject.Users.Doctor;
import TheProject.FileHandling.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
//test 
public class DoctorRecords {
	String PATH = "UserRecords/Doctors";
	public Map <String, Doctor> doctorList;
	
	public DoctorRecords() {
		doctorList = new HashMap<String, Doctor>();
		File dir = new File(PATH);
		for(File file : dir.listFiles()) {
			AttemptedLoad load = FileHandler.loadFile(file.getPath());
			if(load.loaded == false) {
				continue;
			}
			Doctor newDoctor = new Doctor(load.data.get(0));
			doctorList.put(newDoctor.getUser(), newDoctor);
		}
	}
	
	public Doctor searchDoctor(String userName) {
		return doctorList.get(userName);
	}
	
}