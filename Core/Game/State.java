package Core.Game;

import Core.Board.Board;
import Core.Board.FlattenedBoard;

public class State {
    public Board board;
    

    public State(Board board) {
    	this.board = board;
    	
    }

    public void update() {
    	board.update();
    }
    
    public FlattenedBoard flattenedBoard(){
		return board.flatten();
    }
    
    public Board getBoard(){
    	return board;
    }
}
