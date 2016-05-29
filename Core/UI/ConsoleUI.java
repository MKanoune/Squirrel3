package Core.UI;

import Scanner.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import Core.Board.BoardView;
import Entities.Entity;
import Entities.Squirrel;
import Help.EntityType;
import Help.XY;

public class ConsoleUI implements UI{
	private static PrintStream outputStream = System.out;
	private static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	CommandScanner commandScanner = new CommandScanner (CommandType.values(),inputReader);
	private static Command command;
	private static Command lastCommand;
	
	@Override
	public void render(BoardView view) {
		XY size = view.getSize();
        for(int i= 0; i<size.getY(); i++) {
            for(int j=0; j<size.getX(); j++) {
            EntityType type = view.getEntityType(j, i);
            	//Entity entity = view.getEntity(j,i);
                if(type == null){
                    System.out.print(" ");
                }else{
                    System.out.print(type.token);
                }
                System.out.print(" ");
            }
            
            System.out.println();
        }
		
	}
	    
	
	@Override
	public void getCommand() throws ScanException {
		while(true){
			Command command;
			command = commandScanner.next();
			lastCommand = command;
		}
		
		
		
//		CommandScanner commandscanner = new CommandScanner (MyFavoriteCommandType.values(),inputReader);
//		try {
//			command = commandscanner.next();
//			if(command != null && command.getCommandType().getName()!= "spawn" && command.getCommandType().getName()!= "energy"){
//				buffer = command;
//			}else if(command == null){
//				command = buffer;
//			}
//		} catch (ScanException e) {//klappt noch nicht!
//			System.out.println("command not found!");
//			while(true){
//				command = commandscanner.next();
//				if(command != null){
//					break;
//				}
//			}
//			e.printStackTrace();
//		}
//		return command;
    }


	@Override
	public Command getLastCommand() {
		return lastCommand;
	}
	

}
