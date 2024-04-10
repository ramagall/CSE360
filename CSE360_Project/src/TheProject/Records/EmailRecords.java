package TheProject.Records;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TheProject.FileHandling.AttemptedLoad;
import TheProject.FileHandling.FileHandler;

public class EmailRecords {
	
	String INBOXPATH = "Emails/Inbox";
	String OUTBOXPATH = "Emails/Outbox";
	public Map <String, ArrayList<Email>> inboxList;
	public Map <String, ArrayList<Email>> outboxList;
	
	public EmailRecords() {
		inboxList = new HashMap<String, ArrayList<Email>>();
		File dir = new File(INBOXPATH);
		for(File file : dir.listFiles()) {
			AttemptedLoad load = FileHandler.loadFile(file.getPath());
			if(load.loaded == false) {
				continue;
			}
			String User = load.data.get(0)[0];
			ArrayList<Email> messages = new ArrayList<Email>();
			for(int i  = 1; i < load.data.size(); i++) {
				Email message = new Email(load.data.get(i));
				messages.add(message);
			}
			inboxList.put(User, messages);
		}
		
		outboxList = new HashMap<String, ArrayList<Email>>();
		File dir2 = new File(OUTBOXPATH);
		for(File file : dir2.listFiles()) {
			AttemptedLoad load2 = FileHandler.loadFile(file.getPath());
			if(load2.loaded == false) {
				continue;
			}
			
			String User = load2.data.get(0)[0];
			ArrayList<Email> messages = new ArrayList<Email>();
			for(int i = 1; i < load2.data.size(); i++) {
				Email message = new Email(load2.data.get(i));
				messages.add(message);
			}
			outboxList.put(User, messages);
		}
		
	}
	
	public void createEmail(Email newEmail) {
		ArrayList<Email> newInbox = inboxList.get(newEmail.intendedPerson);
		newInbox.add(newEmail);
		inboxList.put(newEmail.intendedPerson, newInbox);
		
		ArrayList<Email> newOutbox = outboxList.get(newEmail.sender);
		newOutbox.add(newEmail);
		inboxList.put(newEmail.sender, newOutbox);
		
		File file0 = FileHandler.getFile(newEmail.intendedPerson + "_inbox", INBOXPATH);
		FileHandler.FileReplace(file0.getPath(), newEmail.getEmail());
		
		File file1 = FileHandler.getFile(newEmail.sender + "_outbox", OUTBOXPATH);
		FileHandler.FileReplace(file1.getPath(), newEmail.getEmail());
		
		
	}
}
