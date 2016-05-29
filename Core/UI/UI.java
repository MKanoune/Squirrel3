package Core.UI;

import Core.Board.BoardView;
import Entities.Squirrel;
import Scanner.Command;
import Scanner.ScanException;

public interface UI {
   
	public Command getLastCommand();
	
	public void getCommand() throws ScanException;
	 
	
	public void render(BoardView view);


	

	
}
