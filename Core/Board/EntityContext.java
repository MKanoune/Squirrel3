package Core.Board;

import Bot.BotImpl.MiniSquirrelBot;
import Entities.BadBeast;
import Entities.Entity;
import Entities.GoodBeast;
import Entities.MasterSquirrel;
import Entities.Squirrel;
import Help.EntityType;
import Help.XY;

public interface EntityContext {
	XY getSize();
	
	void tryMove(MiniSquirrelBot miniSquirrel, XY move);
	
	void tryMove(GoodBeast goodBeast, XY move);
	
	void tryMove(BadBeast badBeast, XY move);
	
	void tryMove(MasterSquirrel masterBot, XY move);
	
	public Squirrel nearestPlayerEntity(XY pos);
	
	public Entity nearestEntity(XY pos);
	
	public void spawnChildBot(MasterSquirrel parent, XY direction, int energy);
	
	public void kill(Entity entity);
	
	public void killAndReplace(Entity entity);

	public EntityType getEntityType(int x,int y);
	
	public Entity getEntity(int x, int y);
	
	public void implode(MiniSquirrelBot bot);

	int getDuration();
}
