package Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javax.management.BadAttributeValueExpException;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import Core.Board.Board;
import Core.Board.BoardConfig;
import Core.Board.FlattenedBoard;
import Core.Game.Game;
import Core.Game.GameImpl;
import Core.Game.State;
import Core.UI.ConsoleUI;
import Core.UI.FxUI;
import Scanner.ScanException;
import Help.MoveCommand;

public class Launcher extends Application{
	public static Logger logger = Logger.getLogger(Launcher.class.getName());
	static Game game;
	
    public static void main(String[] args) throws ScanException, IOException, InterruptedException{	
    	 System.setProperty("java.util.logging.config.file", "logging.properties");
         try{LogManager.getLogManager().readConfiguration();}
         catch ( Exception e ) { e.printStackTrace(); }


        Game gameImpl;
        logger.log(Level.INFO, "Start Application");
    	
    	int i = 0;
        switch(i){
        
        	case 0:
        		Application.launch(args);
        		break;
        	case 1:
        		gameImpl = new GameImpl(new State(new Board()),new ConsoleUI());
        		gameImpl.run();
        		break;
        }
    }
    

	@Override
    public void start(Stage primaryStage) throws ScanException{
		Board board = new Board();
		State state = new State(board);
    	
		FxUI fxUI = FxUI.createInstance(board.getSize());
    	
    	game = new GameImpl(state,fxUI);
    	Timer timer = new Timer();
    	timer.schedule(new Task(), 0);
    	
        primaryStage.setScene(fxUI);
        primaryStage.setTitle("Diligent Squirrel");
        fxUI.repaintBoardCanvas(board.flatten());
        
        fxUI.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
        	public void handle(WindowEvent event) {
            	System.exit(-1);
        		

            }

        });
        primaryStage.show();   
    }
    
   
	private static class Task extends TimerTask{
		@Override
		public void run(){
			try{
				startGame();
			} catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ScanException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void startGame() throws InterruptedException, ScanException {
        game.run();
    }
    
}