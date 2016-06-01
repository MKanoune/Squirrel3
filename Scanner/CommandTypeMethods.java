package Scanner;

import java.lang.reflect.*;

import Bot.BotImpl.MiniSquirrelBot;
import Core.Board.Board;
import Core.Board.FlattenedBoard;
import Entities.Entity;
import Entities.GuidedMasterSquirrel;
import Entities.Squirrel;
import Help.XY;

import java.lang.reflect.*;

public class CommandTypeMethods {
    private GuidedMasterSquirrel master;
    private Board board;
 
    public CommandTypeMethods(GuidedMasterSquirrel master, Board board) {
        this.master = master;
        this.board = board;
    }
    
    //right
    public void d() {
    	master.nextGuidedMove(board.flatten(), new XY(1,0));
    }
 
    //left
    public void a() {
    	master.nextGuidedMove(board.flatten(), new XY(-1,0));
    }
 
    //down
    public void s() {
    	master.nextGuidedMove(board.flatten(), new XY(0,1));
    }
 
    //up
    public void w() {
    	master.nextGuidedMove(board.flatten(), new XY(0,-1));
    }
    
    public void g(){
    	master.nextGuidedMove(board.flatten(), new XY(0,0));
    }
 
    public void mini() throws InterruptedException {
    	if(master == null){return;}
    	int energy = 100;
    	XY pos = XY.addVector(master.xy, XY.RandomMoveCommand());
    	if(!board.flatten().isPositionEmpty(pos)){
    		System.out.println("position is not free!");
    		return;
    	}
        MiniSquirrelBot slave = master.createSlave(board.getNewID(), energy, pos);
        if(slave == null){
        	return;
        }
        System.out.println("new mini at: x" + slave.xy.getX() + " y" +slave.xy.getY());
        board.insert(slave);
    }
    
    public void exit(){
    	System.exit(0);
    }
    
    public void energy(){
    	System.out.println(master.toString());
    }
 
    public void help() {
        String output = "";
        for(int i = 0; i < CommandType.values().length; i++) {
            output += CommandType.values()[i].getName();
            output += CommandType.values()[i].getHelpText();
            output += "\n";
        }
        System.out.println(output);
    }
}