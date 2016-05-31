package Core.Game;

import java.lang.reflect.*;
import Core.Board.Board;
import Core.UI.UI;
import Entities.GuidedMasterSquirrel;
import Entities.MasterSquirrelBot;
import Entities.MiniSquirrelBot;
import Help.XY;
import Scanner.*;



public class GameImpl extends Game{
	Board board;
	GuidedMasterSquirrel master;
	MasterSquirrelBot bot;
	MiniSquirrelBot mini2;
	MiniSquirrelBot mini3;
	MiniSquirrelBot mini4;
	protected UI ui;
	
	public GameImpl(State state, UI ui) {
		super(state);
		this.board = state.getBoard();
		master = new GuidedMasterSquirrel(board.getNewID(),board.rndmPos()); //MasterSquirrel wird gesetzt 
		board.insert(master);
		System.out.println(master.getEnergy());
		mini2 = new MiniSquirrelBot(board.getNewID(), 1000, board.rndmPos(), master);
		board.insert(mini2);
//		bot = new MasterSquirrelBot(23562, board.rndmPos(),100);
//		board.insert(bot);
		this.ui = ui;
	}

	@Override
	protected void render() {
		ui.render(state.flattenedBoard());
//		System.out.println("Energy: " + master.getEnergy());
//		System.out.print("   botEnergy1: "+ bot.getEnergy());
//		System.out.print("   miniEnergy2: "+ mini2.getEnergy());
		//System.out.println();
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
			method.invoke(new CommandTypeMethods(master,board), params);
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
