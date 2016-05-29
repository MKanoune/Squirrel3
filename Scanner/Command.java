package Scanner;


public class Command {
	Object[] params;
	CommandTypeInfo commandTypes;
	
	public Command(CommandTypeInfo commandTypes, Object[] params){
		this.params = params;
		this.commandTypes = commandTypes;
	}
	
	public Object[] getParams(){
		return params;
	}

	public CommandTypeInfo getCommandType() {
		return commandTypes;
	}
}
