import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileMgmt {
	
	
	public ArrayList<File> readFiles(String path) {
		ArrayList<File> files = new ArrayList<File>();
	
		//stream list to get file paths
		try (Stream<Path> paths = Files.list(Paths.get(path))) {
			
			paths.forEach(x->files.add(x.toFile()));
		
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
			
	
		return files;
	}
	

	
	public void createFile(ArrayList<String> lines, String folderpath, String filename) throws IOException {
		
		
		File file = new File(folderpath+"\\"+filename);
		  
		//Create the file
		if (file.createNewFile())
		{
		    System.out.println("File is created!");
		} else {
		    System.out.println("File already exists.");
		}
		
		//Write Content
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		
		//Write content
		for(String s:lines) {
			bw.write(s);
			bw.newLine();
		}
		
		
		bw.close();
	
		 
				
	}
	
	public ArrayList<String> getLines(String filename){
		
		ArrayList <String> source = new ArrayList<String>();
		String currentLine;
		
	    try {
	          // FileReader reads text files in the default encoding.
		            FileReader fileReader = 
		                new FileReader(filename);

		            // Always wrap FileReader in BufferedReader.
		            BufferedReader bufferedReader = 
		                new BufferedReader(fileReader);

		            while((currentLine = bufferedReader.readLine()) != null) {
		            	source.add(currentLine);

		            }   

		            // Always close files.
		            bufferedReader.close(); 	            

		        }
		      
		        catch(FileNotFoundException ex) {
		            System.out.println(
		                "Unable to open file '" + 
		                filename + "'");                
		        }
		      
		        catch(IOException ex) {
		            System.out.println(
		                "Error reading file '" 
		                + filename + "'");                  
		            // Or we could just do this: 
		            // ex.printStackTrace();
		        }
		
	    return source;
	}

}
