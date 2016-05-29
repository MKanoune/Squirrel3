package Entities;

import Core.Board.EntityContext;
import Help.XY;

public class GuidedMasterSquirrel extends MasterSquirrel{
	static int energy = 1000;
	
	
	public GuidedMasterSquirrel(int id,XY xy) {
		super(id, energy, xy);
		timeOut=0;
	}

	@Override
	public void nextStep(EntityContext context) {
		
    	
	}
	
	public void nextGuidedMove(EntityContext context, XY move){
		if(getTimeOut()>0){
			updateTimeOut();
			return;
		}
		context.tryMove(this, move);
	}
	
}
