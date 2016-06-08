package Core.Board;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigValues {
	String result = "";
	InputStream inputStream;
	
	public String getPropValues() throws IOException{
		try{
			Properties props = new Properties();
			String propFileName = "config.properties";
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			
			if(inputStream != null){
				props.load(inputStream);
			}else{
				throw new FileNotFoundException("file not found");
			}
			
			String BadBeast = props.getProperty("BadBeast");
			String GoodBeast = props.getProperty("GoodBeast");
			int BadBeastCount = Integer.valueOf(props.getProperty("badBeastCount"));
			int GoodBeastCount = Integer.valueOf(props.getProperty("goodBeastCount"));
			
			
			
		}catch(Exception e){
			
			System.out.println("Exception" +e);
		}finally{
			inputStream.close();
		}
		
		return result;
	}
}
