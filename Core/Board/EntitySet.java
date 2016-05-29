package Core.Board;
import Entities.Entity;
import Entities.GoodPlant;
import Help.XY;

public class EntitySet {
	protected Entity[] container;
	int pos = 0;
	
	public EntitySet(XY Size){
		container = new Entity[Size.getX()*Size.getY()];
	}
	
	public void insert(Entity entity){
		container[pos]= entity;
		pos++;
	}
	
	public void delete(Entity entity){
		for(int i = 0; i<container.length; i++){
			if(container[i] == entity){
				int position = i;
				while(container[position] != null){
					container[position] = container[position+1];
					position++;
				}
				return;
			}
		}
	}
}


