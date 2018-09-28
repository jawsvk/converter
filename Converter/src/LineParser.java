import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Line;

public class LineParser {
	
	 static Map<String,String> typeMap = new HashMap<String,String>();
	 String filename;
	 
	 
	 public LineParser() {
			typeMap.put("boolean", "boolean");
			typeMap.put("char", "> \n<xs:simpleType>\r" + 
					"<xs:restriction base=\"xs:string\"> \r" + 
					"<xs:length value=\"1\" fixed=\"true\"/>\r" + 
					"</xs:restriction>\r" + 
					"</xs:simpleType>");
			typeMap.put("wchar", "> \n<xs:simpleType>\r" + 
					"<xs:restriction base=\"xs:string\"/> \r" + 
					"</xs:simpleType>");
			typeMap.put("double", "double");
			typeMap.put("float", "float"); 
			typeMap.put("octet", "unsignedByte");
			typeMap.put("long", "int");
			typeMap.put("long long", "long");
			typeMap.put("short", "short");
			typeMap.put("string", "string");
			typeMap.put("wstring", "string");
			typeMap.put("unsigned short", "unsignedShort");
			typeMap.put("unsigned long", "unsignedLong");
			typeMap.put("unsigned long long", "unsignedLong");
			
	 }
	 
	 public LineParser(String fileName)
	 {
		 super();
		 this.filename = fileName;
	
	 }
	 
	 public ArrayList<String> parseObject(ArrayList<String> source) {			
		 	
			ArrayList<Line> currentData = new ArrayList<Line >();
			
			for(String s: source) {
				
				Line currentLine;
				
				//check for attributes
				if(s.contains("attribute"))
				{
					String[] output = s.split(" ");
					currentLine = new Line(cleanUp(output[1]),cleanUp(output[2]));
					
					if(s.contains("not required")) currentLine.setIsMandatory(false);
					
					currentData.add(currentLine);
				}
				
			}
			
			return processLines(currentData);
	 }
	 
	 

	public ArrayList<String> parseStruct(ArrayList<String> source) {
		
		ArrayList<Line> currentData = new ArrayList<Line >();
		
	
		for(String s: source) {
	
			Line currentLine;
			
			//skip empty line
			if(s.isEmpty()) continue;
			
			//skip comments
			if(s.trim().startsWith("//")) continue;			
			
			//check for typedef
			if(s.contains("typedef")) {
				
				//sequence typedef
				if(s.contains("<"))
				{
				String type = s.substring(s.indexOf('<')+1, s.indexOf('>'));
				currentLine = new Line(type,"typedef");
				}
				
				//normal typedef
				else {
					String[] output = s.trim().split(" ");
					currentLine = new Line(cleanUp(output[1]),cleanUp(output[2]));
					
				}
				
			}
			else
			{
			String[] output = s.trim().split(" ");
			currentLine = new Line(cleanUp(output[0]),cleanUp(output[1]));
			if(s.contains("not required")) currentLine.setIsMandatory(false);
			}
			
			currentData.add(currentLine); 
			
		}
		
		
		return processLines(currentData);
	
	}
	
	public ArrayList<String> processLines(ArrayList<Line> currentData) {
		
		ArrayList<String> result = new ArrayList<String>();
		
		//add header tags
		result.add(String.format("<xs:complexType name=\"%s\">\r", filename.split("\\.")[0]));
		result.add("\t<xs:sequence>");
		
		for(Line l : currentData)
		{
			result.add(linetoXML(l));
		}
		
		//add closing tags
		result.add("\t</xs:sequence>\r");
		result.add("</xs:complexType>");
		
		return result;
		
	}
	
	public String cleanUp(String s) {
		
		//remove ;		
		s = s.replace(";","");
		
		if(s.contains("//")) s = s.replace("//", "");
		
		return s.trim();
		
	}
	
	public String linetoXML(Line s) {
	
		String output = "";
		StringBuilder sb = new StringBuilder(output);
	
		//to handle typedef
		if(s.getAttributeName()=="typedef") {
			
			sb.append(String.format("<xs:element ref=\"%s\" ",s.getDataType()));
			if(!s.getIsMandatory()) sb.append("minOccurs=\"0\"");
			sb.append("maxOccurs=\"unbounded\"/>");
		}
		
		else { 
			//handle primitive types and user-generated types
			
			String type = s.getDataType();
			
			sb.append(String.format("<xs:element name=\"%s\" ",s.getAttributeName()));
		
			if(!s.getIsMandatory()) sb.append("minOccurs=\"0\"");
			
			//add user-generated type to map
			if(!typeMap.containsKey(s.getDataType().toLowerCase())) 
				{
					typeMap.put(type.toLowerCase(), type);
					sb.append(" maxOccurs=\"unbounded\" ");
				}
			
			//if char or wchar
			if(typeMap.get(type.toLowerCase()).contains("simpleType")) {
				
					sb.append(typeMap.get(type.toLowerCase()));
					sb.append(" </xs:element>");
				}
			else {
			
					sb.append(String.format("type=\"xs:%s\" ", typeMap.get(type.toLowerCase())));  //normal primitive types		
				 
				sb.append("/>");
			}
		
		}
		
		return sb.toString();
	}
	

	

}
