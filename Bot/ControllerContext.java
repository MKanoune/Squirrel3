package Bot;

import Core.Board.Board;
import Entities.Entity;
import Help.EntityType;
import Help.XY;

public interface ControllerContext {
	
	
	public XY getViewLowerLeft();
	
	public XY getViewUpperRight();
	
	public Entity getEntityAt(int x,int y);
	
	public EntityType getEntityTypeAt(int x, int y);
	
	public void move(XY direction);
	
	public void spawnMiniBot(XY direction, int energy);
	
	public int getEnergy();
	
	public XY getPosition();
	
	//public default void implode();
	
	
	
}
