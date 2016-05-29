package Scanner;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class CommandScanner {
	private CommandTypeInfo[] commandTypeInfos;
	private BufferedReader inputReader;
	private PrintStream outputStream;
	
	public CommandScanner(CommandTypeInfo[] commandTypeInfos, BufferedReader inputReader){
		this.commandTypeInfos = commandTypeInfos;
		this.inputReader = inputReader;
	}
	
	public Command next() throws ScanException{
		String input;
        String[] inputSplit;
        Command command;
        Object[] params;

        try {
            input = inputReader.readLine();
            inputSplit = input.split(" ");

            for (int i = 0; i < commandTypeInfos.length; i++) {
                if (commandTypeInfos[i].getName().equals(inputSplit[0])) {
                    Class<?>[] paramTypes = commandTypeInfos[i].getParamTypes();
                    if(paramTypes == null) {
                        command = new Command(commandTypeInfos[i], null);
                    }
                    else {
                        params = new Object[paramTypes.length];
                        for(int j = 0; j < paramTypes.length; j++) {
                            params[j] = inputSplit[j + 1];
                            if(paramTypes[j] == int.class) {
                                params[j] = (Integer.parseInt((String)params[j]));
                            }
                            //Add more ifs here when you have more values
                        }
                        command = new Command(commandTypeInfos[i], params);
                    }
                    return command;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            System.out.println("Null");
        }

        return null;
//	String input;
//	Command command = null;
//	try {
//		input = inputReader.readLine();
//		String [] split = input.split(" ");
//		String commandString = split[0];
//		Object[] params = new Object[split.length-1];
//		
//		for(int j = 0; j< split.length-1;j++){//Parameter werden gesplittet und in ein Object array gesetzt
//			params[j]=split[j+1];
//		}
//		
//		for(int i = 0; i < commandTypeInfos.length;i++){
//			if(commandString.equals(commandTypeInfos[i].getName())){//commando wird aus der liste gelesen
//				command = new Command(commandTypeInfos[i],params);	
//			}
//		}
//		
//		for(int p = 0; p < params.length;p++){				//parameter wird wenn es ein integer ist gecasted
//			if(params[p]==int.class){
//				params[p]=(Integer.parseInt((String)params[p]));
//			}
//		}
//		
//		
//				
//		
//		
//		
//	}catch (IOException e) {
//		e.printStackTrace();
//	}
//	return command;
//	
	}
	
	
	
	
	
	
}
