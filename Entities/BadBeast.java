package Entities;

import Core.Board.EntityContext;
import Help.XY;

public class BadBeast extends Entity {
	private final static int energy = -150;
	private int bites = 7;
	//private int token;
	
	public BadBeast(int id, XY xy) {
		super(id, energy, xy);
		setTimeOut(3);
		
	}
	
	@Override
    public void nextStep(EntityContext context) {
        if(timeOut > 0){
        	updateTimeOut();
        	return;
        }
        Squirrel Enemy = context.nearestPlayerEntity(this.xy);
        
    	if(Enemy !=null){
    		context.tryMove(this, this.xy.attack(this.xy,Enemy.xy));
    	}else{
    		context.tryMove(this, XY.RandomMoveCommand());
    	}
    	setTimeOut(2);
    }
    
    public void updateBites(){
    	bites--;
    }
    
    public int getBites(){
    	return bites;
    }

	@Override
	public Entity createNew(int ID, XY pos) {
		return new BadBeast(ID,pos);
	}
    
    //public XY attack(){
    
//    public MoveCommand attack(MasterSquirrel sq){
//    	MoveCommand move = null;
//    	if(sq.XY.getX() == this.XY.getX()&& sq.XY.getY() > this.XY.getY())move = MoveCommand.Down;
//    	if(sq.XY.getX() == this.XY.getX()&& sq.XY.getY() < this.XY.getY())move = MoveCommand.Up;
//    	if(sq.XY.getX() >  this.XY.getX()&& sq.XY.getY() == this.XY.getY())move = MoveCommand.Right;
//    	if(sq.XY.getX() < this.XY.getX()&& sq.XY.getY() == this.XY.getY())move = MoveCommand.Left;
//    	if(sq.XY.getX() > this.XY.getX()&& sq.XY.getY() > this.XY.getY())move = MoveCommand.DownRight;
//    	if(sq.XY.getX() < this.XY.getX()&& sq.XY.getY() > this.XY.getY())move = MoveCommand.DownLeft;
//    	if(sq.XY.getX() > this.XY.getX()&& sq.XY.getY() < this.XY.getY())move = MoveCommand.UpRight;
//    	if(sq.XY.getX() < this.XY.getX()&& sq.XY.getY() < this.XY.getY())move = MoveCommand.UpLeft;
//    	return move;
//    	
//    }
}
