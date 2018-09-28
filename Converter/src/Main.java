import java.io.File;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		//folder path
		String sourcePath ="C:\\Users\\kakiong.voon\\eclipse-workspace\\Converter\\input\\";
		FileMgmt FileManager = new FileMgmt();

		ArrayList<File> folders = FileManager.readFiles(sourcePath);
		System.out.println("got the list of all folders");
		
		//go through each folder and execute all files in that folder
		for(File f: folders) {
					
				System.out.println("printing folder of "+ f.getName());
				String folderPath = sourcePath + f.getName();
				ArrayList<File> files = FileManager.readFiles(folderPath);
				execute(files, f.getName());
			
					
		}

	 
	   
	}
		
	public static void  execute(ArrayList<File> files, String folderName) {
		FileMgmt FileManager = new FileMgmt();
		
		//create new folder
		String outputPath = "C:\\Users\\kakiong.voon\\eclipse-workspace\\Converter\\output\\";
		String folderpath = outputPath + folderName;
		File newfolder = new File(folderpath);
		newfolder.mkdir();
		
		//create xsd files for that folder
		for(File f: files) {
			
			LineParser lineParser = new LineParser(f.getName());				
			ArrayList <String> source = FileManager.getLines(f.getPath());
			ArrayList<String> output = null;
			
			//if Struct folder parseStruct
			if(folderName.contains("Struct"))  
				{
					output = lineParser.parseStruct(source);
				}
			//if Object folder parseObject
			else if(folderName.contains("Object"))
			{
				output = lineParser.parseObject(source);
			}
		   
		     
			try {
					//create output xsd to text file
					System.out.println("creating file..."+ f.getName());
					FileManager.createFile(output,folderpath,f.getName());
				}
		    catch(Exception e){
		    	
		    		System.out.println(e.getMessage());
		    	}
			
		}
		
	}


}
