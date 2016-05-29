package Core.Board;

import Entities.Entity;
import Help.EntityType;
import Help.XY;

public interface BoardView {
	public EntityType getEntityType(int x, int y);

	public Entity getEntityAt(int x, int y);
	
	public XY getSize();

}
