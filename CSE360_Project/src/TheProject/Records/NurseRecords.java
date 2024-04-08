package TheProject.Records;

import TheProject.Users.Nurse;
import TheProject.FileHandling.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class NurseRecords {
	String PATH = "UserRecords/Nurses";
	public Map <String, Nurse> nurseList;
	
	public NurseRecords() {
		nurseList = new HashMap<String, Nurse>();
		File dir = new File(PATH);
		for(File file : dir.listFiles()) {
			AttemptedLoad load = FileHandler.loadFile(file.getPath());
			if(load.loaded == false) {
				continue;
			}
			Nurse newNurse = new Nurse(load.data.get(0));
			nurseList.put(newNurse.getUser(), newNurse);
		}
	}
	
	public Nurse searchNurse(String userName) {
		return nurseList.get(userName);
	}
	
}