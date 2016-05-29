package Entities;
import Help.XY;
import Core.Board.EntityContext;
import Entities.GuidedMasterSquirrel;


public abstract class Squirrel extends Entity {
	
	
	//for MasterSquirrel
	public Squirrel(int id,int energy,XY xy) {
		super(id, energy, xy);
		timeOut=0;
	}

	@Override
	public abstract void nextStep(EntityContext context);
	

	@Override
	public Entity createNew(int ID, Help.XY pos) {
		// TODO Auto-generated method stub
		return null;
	}
}
