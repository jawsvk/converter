package model;

public class Line {
	
		String dataType;
		String attributeName;
		Boolean isMandatory;
		
		
		public Line (String type, String name)
		{
			this.dataType= type;
			this.attributeName=name;	
			isMandatory = true;
		}
		


		public String getDataType() {
			return dataType;
		}


		public String getAttributeName() {
			return attributeName;
		}
		
		public Boolean getIsMandatory()	{
			return isMandatory;
		}
		
		public Boolean setIsMandatory(Boolean flag) {
			return isMandatory=flag;
		}
		
	}


