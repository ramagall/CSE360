package TheProject.FileHandling;

import java.io.*;
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
	
}