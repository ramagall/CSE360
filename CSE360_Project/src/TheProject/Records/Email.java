package TheProject.Records;

public class Email{
	public String intendedPerson;
	public String sender;
	public String urgency;
	public String head;
	public String body;
	
	public Email() {
		//empty email
	}
	
	public Email(String intendedPerson, String sender, String urgency, String head, String body) {
		this.intendedPerson = intendedPerson;
		this.sender = sender;
		this.urgency = urgency;
		this.head = head;
		this.body = body;
	}
	
	public Email(String data[]) {
		intendedPerson = data[0];
		sender = data[1];
		urgency = data[2];
		head = data[3];
		body = data[4];
	}
	
	public String getEmail() {
		return intendedPerson + "~" + sender + "~" + urgency + "~" + head + "~" + body;
	}
}