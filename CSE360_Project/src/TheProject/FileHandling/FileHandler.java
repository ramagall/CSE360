package TheProject.FileHandling;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
	//Use the loadFile class to UserRecord data.  
	public static AttemptedLoad loadFile(String location) { 
		AttemptedLoad loadResult = new AttemptedLoad();
		File file = new File(location);
		try {
			
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				loadResult.data.add(line.split("~"));
			}
			
			scanner.close();
			loadResult.loaded = true;
		} 
		catch(FileNotFoundException e) {
			loadResult.loaded = false;
		}
		
		return loadResult;
	}
	//Use this class to get a specific file.
	public static File getFile (String fileName, String location) {
		File file = new File(location + "/" + fileName + ".txt");
		
		try {
			file.createNewFile();
		}
		
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
	//Use this class to write a single line to a file.
	public static void writeToFile(File file, String string) {
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(string);
			fileWriter.close();
		}
		
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	//Use this class to write an array of strings to a file.
	public static void writeToFile(File file, String[] stringArray) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			
			for(int i = 0; i < stringArray.length; i++) {
				bufferedWriter.write(stringArray[i]);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
			bufferedWriter.close();
			bufferedWriter.close();
		}
		
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void FileReplace(String file, String lineReplaced, String newInfo)
	
	{
		 List<String> lines = new ArrayList<String>();
		 String line = null;
		   
		 try {
			 File f1 = new File(file);
		     FileReader fr = new FileReader(f1);
		     BufferedReader br = new BufferedReader(fr);
		     	while ((line = br.readLine()) != null) {
		     		if (line.contains(lineReplaced)) {
		     			line = newInfo;
		            }
		            lines.add(line + "\n");
		        }
		        fr.close();
		        br.close();

		        FileWriter fw = new FileWriter(f1);
		        BufferedWriter out = new BufferedWriter(fw);
		        for(String s : lines) {
		                 out.write(s);
		        }
		        out.flush();
		        out.close();
		 }
		 catch (Exception ex) {
		        ex.printStackTrace();
		 }
	}
	
public static void FileReplace(String file, String newInfo)
	{
		 List<String> lines = new ArrayList<String>();
		 String line = null;
		   
		 try {
			 File f1 = new File(file);
		     FileReader fr = new FileReader(f1);
		     BufferedReader br = new BufferedReader(fr);
		     	while ((line = br.readLine()) != null) {
		            lines.add(line + "\n");
		        }
		     	lines.add(newInfo);
		        fr.close();
		        br.close();

		        FileWriter fw = new FileWriter(f1);
		        BufferedWriter out = new BufferedWriter(fw);
		        for(String s : lines) {
		                 out.write(s);
		        }
		        out.flush();
		        out.close();
		 }
		 catch (Exception ex) {
		        ex.printStackTrace();
		 }
	}
}