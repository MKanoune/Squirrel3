package Core.Board;

import Entities.BadBeast;
import Entities.BadPlant;
import Entities.Entity;
import Entities.GoodBeast;
import Entities.GoodPlant;
import Entities.GuidedMasterSquirrel;
import Entities.MasterSquirrel;
import Entities.MasterSquirrelBot;
import Entities.MiniSquirrelBot;
import Entities.Squirrel;
import Entities.Wall;
import Help.EntityType;
import Help.XY;


public class FlattenedBoard implements BoardView, EntityContext {
	Board board;
	private Entity [][] cells;
	private XY Size;
	
	public FlattenedBoard(Board board) {
		this.board = board;
		this.Size = board.config.Size;
		cells = new Entity [this.Size.getY()][this.Size.getX()];
		for(int i = 0; i < board.container.pos; i++){
			if(board.container.container[i]!= null){
				cells[board.container.container[i].xy.getY()][board.container.container[i].xy.getX()] = board.container.container[i];
			}
		}
	}
	
	
	@Override//BoardView
	public XY getSize() {
		return this.board.config.Size;
	}

/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 					
 * 					-TRY MOVES-
 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/    	
	@Override//EntityContext
	public void tryMove(MiniSquirrelBot mini, XY move) {
		XY xy = mini.xy;
		XY newPos = xy.commandVector(move);
		
		if(mini.getEnergy() <= 0){
			kill(mini);
		}
		
		if(isOutOfRange(newPos)){
        	return;
        }
		Entity collision = cells[newPos.getY()][newPos.getX()];

		//+++++++++++++++++++++++-Wall-++++++++++++++++++++++++++++++++++++++++
        if(collision instanceof Wall) {
        	mini.updateEnergy(collision.getEnergy());
        	//tryMove(mini,XY.RandomMoveCommand());
        	mini.setTimeOut(3);

        //++++++++++++++++++++++++-BadBeast-++++++++++++++++++++++++++++++++++++
        } else if(collision instanceof GoodPlant) {
        	mini.updateEnergy(collision.getEnergy());
        	//mini.getController().implode();
        	killAndReplace(collision);

        //++++++++++++++++++++++-GodBeast-++++++++++++++++++++++++++++++++++++++
        } else if(collision instanceof GoodBeast) {
        	mini.updateEnergy(collision.getEnergy());
        	//mini.getController().implode();
        	killAndReplace(collision);

        //++++++++++++++++++++++-BadPlant-++++++++++++++++++++++++++++++++++++++
        } else if(collision instanceof BadPlant) {
        	mini.updateEnergy(collision.getEnergy());
        	//mini.getController().implode();
            killAndReplace(collision);

        //++++++++++++++++++++++-BadBeast-++++++++++++++++++++++++++++++++++++++
        } else if(collision instanceof BadBeast) {
        	mini.updateEnergy(collision.getEnergy());
        	((BadBeast) collision).updateBites();
            if(((BadBeast) collision).getBites()==0){
                killAndReplace(collision);
            	//mini.getController().implode();
            }
        }else if(collision instanceof MasterSquirrel){
        	collision.updateEnergy(mini.getEnergy());
        	kill(mini);
        }
        if(isOutOfRange(newPos)){
        	return;
        }
        if(board.isPositionEmpty(newPos)){
        cells[xy.getY()][xy.getX()] = null;
        mini.xy.setPosition(newPos);
        cells[newPos.getY()][newPos.getX()] = mini;
        }
	}
	
	
	@Override//EntityContext
	public void tryMove(GoodBeast goodBeast, XY move) {
		XY xy = goodBeast.xy;
		XY newPos = xy.commandVector(move);
		
		if (isOutOfRange(newPos)){
			//tryMove(goodBeast,xy.RandomMoveCommand());//TO DO!
            return;
		}
		
		Entity collision = cells[newPos.getY()][newPos.getX()];
		if(collision instanceof Squirrel){
			collision.updateEnergy(goodBeast.getEnergy());
			killAndReplace(goodBeast);
			return;
		}
		if(board.isPositionEmpty(newPos)){
		
		cells[xy.getY()][xy.getX()] = null;
		goodBeast.xy.setPosition(newPos);
        cells[newPos.getY()][newPos.getX()] = goodBeast;
		}
	}
	
	
	@Override//EntityContext
	public void tryMove(BadBeast badBeast, XY move) {
		XY xy = badBeast.xy;
		XY newPos = xy.commandVector(move);
		
		if (isOutOfRange(newPos)){
			tryMove(badBeast,xy.RandomMoveCommand());
            return;
		}
		
		Entity collision = cells[newPos.getY()][newPos.getX()];
		if(collision instanceof Squirrel){
			collision.updateEnergy(badBeast.getEnergy());
			badBeast.updateBites();
            if(badBeast.getBites()==0) {
                killAndReplace(badBeast);
            }
		}
		
		//out of range or taken?
		if (!board.isPositionEmpty(newPos)){
			//tryMove(badBeast,xy.RandomMoveCommand());
            return;
		}
		
		cells[xy.getY()][xy.getX()] = null;
		badBeast.xy.setPosition(newPos);
		cells[newPos.getY()][newPos.getX()] = badBeast;
	}
	
	
	@Override//EntityContext
	public void tryMove(MasterSquirrel masterBot, XY move) {
		//masterBot.nextStep(this);
		XY xy = masterBot.xy;
		XY newPos = xy.commandVector(move);
		
		if(isOutOfRange(newPos)){
        	return;
        }
		
		Entity collision = cells[newPos.getY()][newPos.getX()];
		
		//+++++++++++++++++++++++-Wall-++++++++++++++++++++++++++++++++++++++++
        if(collision instanceof Wall) {
            masterBot.updateEnergy(collision.getEnergy());
            masterBot.setTimeOut(3);

        //++++++++++++++++++++++++-BadBeast-++++++++++++++++++++++++++++++++++++
        } else if(collision instanceof GoodPlant) {
        	masterBot.updateEnergy(collision.getEnergy());
            killAndReplace(collision);

        //++++++++++++++++++++++-GodBeast-++++++++++++++++++++++++++++++++++++++
        } else if(collision instanceof GoodBeast) {
        	masterBot.updateEnergy(collision.getEnergy());
            killAndReplace(collision);

        //++++++++++++++++++++++-BadPlant-++++++++++++++++++++++++++++++++++++++
        } else if(collision instanceof BadPlant) {
        	masterBot.updateEnergy(collision.getEnergy());
            killAndReplace(collision);

        //++++++++++++++++++++++-BadBeast-++++++++++++++++++++++++++++++++++++++
        } else if(collision instanceof BadBeast) {
        	masterBot.updateEnergy(collision.getEnergy());
        	((BadBeast) collision).updateBites();
            if(((BadBeast) collision).getBites()==0)
                killAndReplace(collision);
        }else if(collision instanceof MiniSquirrelBot){
        	if(((MiniSquirrelBot) collision).getMaster() == masterBot){
        		masterBot.updateEnergy(collision.getEnergy());
        		kill(collision);
        	}else{
        		kill(collision);
        	}
        	
        }
        if(!isPositionEmpty(newPos)){
        	return;
        }
        cells[xy.getY()][xy.getX()] = null;
        masterBot.xy.setPosition(newPos);
        cells[newPos.getY()][newPos.getX()] = masterBot;
	}
	
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 					
 * 					- TRY MOVE - END -
 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/    

/*
* prüft die gegebene Position ob es sich noch im spielfeld befindet 
* und ob sie leer ist
*/
	
	public boolean isOutOfRange(XY pos) {
    	if(pos.getX()>Size.getX() || pos.getY()>Size.getY()||pos.getX() < 0 || pos.getY() < 0){
    		return true;
    	}
		return false;
        
    }
	
	public boolean isPositionEmpty(XY pos){
		return (getEntity(pos.getX(), pos.getY())== null);
	}
	
//++++++END+++++++++++++++++++++++++++++	
	
	@Override//EntityContext
	public void kill(Entity entity) {
		cells[entity.xy.getY()][entity.xy.getX()] = null;
		board.delete(entity);
	}
	
	@Override//EntityContext
	public void killAndReplace(Entity e) {
		XY pos = board.rndmPos();
		int id = board.getNewID();
		Entity newEntity = e.createNew(id, pos);
		kill(e);
		board.insert(newEntity);
	}


	
	
	@Override
	public Squirrel nearestPlayerEntity(XY pos) {
		Squirrel next = null;
		int left = pos.getX()-6;
		if(left <0)left = 0;
		
		int right = pos.getX()+6;
		if(right > Size.getX())right = Size.getX();
		
		int up = pos.getY()-6;
		if(up < 0)up = 0;
		
		int down = pos.getY()+6;
		if(down > Size.getY())down = Size.getY();
		
		for(int i = up; i < down; i++){
			for(int j = left; j < right; j++){
				Entity menance = cells[i][j];
				
				if(menance instanceof Squirrel){
					if(next != null){
						if(XY.vectorMagnitude(pos,menance.xy.getXY())<= XY.vectorMagnitude(pos,next.xy.getXY())){
							next = (Squirrel) menance;
						}
												
					}else{
						next = (Squirrel)menance;	//(MasterSquirrel)cells[j][i];
					}
				}
			}
		}
		return next;
	}


	@Override
	public EntityType getEntityType(int x, int y) {
		return EntityType.getEntityType(cells[y][x]);
		
//		Entity entity = cells[y][x];
//		if(entity instanceof Wall){
//			return EntityType.Wall;
//		}else if(entity instanceof BadBeast){
//			return EntityType.BadBeast;
//		}else if(entity instanceof BadPlant){
//			return EntityType.BadPlant;
//		}else if(entity instanceof GoodBeast){
//			return EntityType.GoodBeast;
//		}else if(entity instanceof GoodPlant){
//			return EntityType.GoodPlant;
//		}else if(entity instanceof GuidedMasterSquirrel){
//			return EntityType.GuidedMasterSquirrel;
//		}else if(entity instanceof MiniSquirrelBot){
//			return EntityType.MiniSquirrelBot;
//		}else if(entity instanceof MasterSquirrelBot){
//				return EntityType.MasterSquirrelBot;
//		}else{
//			return EntityType.Null;
//		}
	}
	
	public Entity getEntity(int x, int y){
		Entity entity = cells[y][x];
		return entity;
	}
	
	
	@Override
	public Entity nearestEntity(XY pos) {
		Entity next = null;
		int left = pos.getX()-6;
		if(left <0)left = 0;
		
		int right = pos.getX()+6;
		if(right > Size.getX())right = Size.getX();
		
		int up = pos.getY()-6;
		if(up < 0)up = 0;
		
		int down = pos.getY()+6;
		if(down > Size.getY())down = Size.getY();
		
		for(int i = up; i < down; i++){
			for(int j = left; j < right; j++){
				Entity menance = cells[i][j];
				
				if(menance instanceof Squirrel){
					if(next != null){
						if(XY.vectorMagnitude(menance.xy,pos)<= XY.vectorMagnitude(next.xy,pos)){
							next = menance;
						}
						//TO DO: 2 Entitys mit Vektorbetrag vergleich und kürzeren auswählen						
					}else{
						next = menance;	
					}
				}
			}
		}
		return next;
	}


	@Override
	public Entity getEntityAt(int x, int y) {
		Entity entity = cells[y][x];
		return entity;
	}


	@Override
	public void spawnChildBot(MasterSquirrel parent, XY direction, int energy) {
		MiniSquirrelBot mini = parent.createSlave(board.getNewID(), energy, direction);
		board.insert(mini);
	}
	

	

	

	
	
}
