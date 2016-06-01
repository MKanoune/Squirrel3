package Core.Game;

import java.lang.reflect.*;

import Bot.BotImpl.MasterSquirrelBot;
import Bot.BotImpl.MiniSquirrelBot;
import Core.Board.Board;
import Core.UI.UI;
import Entities.GuidedMasterSquirrel;
import Help.XY;
import Scanner.*;



public class GameImpl extends Game{
	Board board;
	MiniSquirrelBot mini2;
	protected UI ui;
	
	public GameImpl(State state, UI ui) {
		super(state);
		this.board = state.getBoard();

		this.ui = ui;
	}

	@Override
	protected void render() {
		ui.render(state.flattenedBoard());
	}

	@Override
	protected void processInput() throws ScanException{
		Command command = ui.getLastCommand();
		if(command == null){
			return;
			
		}
		Object[] params = command.getParams();
//		if(command.getCommandType().getName().equals("m")){
//			try{
//				System.out.println("drinnen!");
//				Class cl = Class.forName("Scanner.CommandTypeMethods");
//				Method method = cl.getDeclaredMethod(command.getCommandType().getName(), command.getCommandType().getParamTypes());
//				method.invoke(new CommandTypeMethods(master,board), params);
//				return;
//			}
//			catch (ClassNotFoundException e){System.out.println("err 1");}
//			catch (IllegalAccessException e){System.out.println("err 2");}
//			catch(NoSuchMethodException e){System.out.println("err 4");}
//			catch(InvocationTargetException e){System.out.println("err 4");}
//			catch(IllegalArgumentException e){System.out.println("err spawn");}
//			catch(NullPointerException e){System.out.println("try again");}
//			
//		}
//		
		
		try{
			Class<?> cl = Class.forName("Scanner.CommandTypeMethods");
			Method method = cl.getDeclaredMethod(command.getCommandType().getName(), command.getCommandType().getParamTypes());
			method.invoke(new CommandTypeMethods(board.getMaster(),board), params);
			//method.invoke(obj, args)
			
			
		}
		catch (ClassNotFoundException e){System.out.println("err 1");}
		catch (IllegalAccessException e){System.out.println("err 2");}
		catch(NoSuchMethodException e){System.out.println("Method not Found");}
		catch(InvocationTargetException e){System.out.println("err 4");}
		catch(IllegalArgumentException e){System.out.println("err spawn");}
		catch(NullPointerException e){System.out.println("try again");}
		
		
		}
		

		
	
	
	

}
