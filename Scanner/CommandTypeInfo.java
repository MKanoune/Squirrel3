package Scanner;


public interface CommandTypeInfo {
	
	
	
	public String getName();
	
	public String getHelpText();
	
	public Class<?>[] getParamTypes();
	
	
}
