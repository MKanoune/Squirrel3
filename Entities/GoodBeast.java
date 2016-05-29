package Entities;

import Core.Board.EntityContext;
import Help.MoveCommand;
import Help.XY;
import Main.*;

public class GoodBeast extends Entity {
	
	private final static int energy = 200;
	//private int moves = 4;
	
	
	public GoodBeast(int id, XY xy) {
		super(id, energy, xy);
		setTimeOut(3);
		token = 'G';
	}
	
	
	
	
	
    @Override
    public void nextStep(EntityContext context) {
    	if(timeOut>0){
    		updateTimeOut();
    		return;
    	}
    	
    	Squirrel Enemy = context.nearestPlayerEntity(this.xy);
    	
    	if(Enemy != null){
    		context.tryMove(this,xy.escape(this.xy,Enemy.xy));
    	}else{
    		context.tryMove(this, XY.RandomMoveCommand());
     
    	}
    	setTimeOut(3);
    }





	@Override
	public Entity createNew(int ID, XY pos) {
		return new GoodBeast(ID,pos);
	}
    
    
}
