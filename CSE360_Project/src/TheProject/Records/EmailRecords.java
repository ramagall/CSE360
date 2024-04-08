package TheProject.Records;

public class EmailRecords {

	// implemented the exact same as everything else but for emails
	
	// T_username_emails.txt
	// T = type being P or N or D
	// contains inbox for that user
	// goes into /UserRecords/Emails
	
	/* file contains sent emails and recieved emails
	 * Sent: email_data
	 * Recieved: email_data
	*/
	
	
	
	// keep file storing emails like a stack in order to keep in chronological order
	// if (send email)
		// if (is reply)
			// delete original email from file
			// store in recipient's file
		// else
			// store in recipient's file
}
