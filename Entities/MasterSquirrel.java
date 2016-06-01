package Entities;

import Bot.BotImpl.MiniSquirrelBot;
import Core.Board.EntityContext;
import Help.XY;

public abstract class MasterSquirrel extends Squirrel{
	
	
	public MasterSquirrel(int ID, int energy, XY xy) {
		super(ID, energy,xy);
	}

	@Override
	public abstract void nextStep(EntityContext context);
	
	public MiniSquirrelBot createSlave(int miniID, int miniEnergy,XY direction) {
		if(getEnergy()<=miniEnergy){
			System.out.println(getEnergy());
			System.out.println("not enough energy!");
			return null;
		}
		if(miniEnergy > getEnergy()-50){
			miniEnergy = getEnergy()-50; 
		}
		MiniSquirrelBot slave = new MiniSquirrelBot(miniID, miniEnergy,direction ,this);
		updateEnergy(-miniEnergy);
        return slave;
	}
	
	public boolean checkSlave(Entity object) {
		if(object instanceof MiniSquirrelBot){
			MiniSquirrelBot mini = (MiniSquirrelBot)object;
			if(mini.getMaster()==this) return true; 
		}
		return false;
	}

}
