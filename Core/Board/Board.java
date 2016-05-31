package Core.Board;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import Entities.BadBeast;
import Entities.BadPlant;
import Entities.Entity;
import Entities.GoodBeast;
import Entities.GoodPlant;
import Entities.MiniSquirrelBot;
import Entities.Wall;
import Help.XY;

public class Board {
	BoardConfig config;
	EntitySet container;
	int pos;
	//public Entity[]container;
	private static int recentID;
	
	public Board(){
		this.config = new BoardConfig();
		container = new EntitySet(config.Size);
		//this.container = new Entity[config.getEntityCount()+1];//+1 for Squirrel
		setStartEntitys();
	}
	
	public int getPos(){return pos;}
	
	public XY getSize(){
		return config.Size;
	}
	
	public void update(){
		for(int i=0;i<container.container.length;i++){
			if(container.container[i]!=null){
			container.container[i].nextStep(flatten());
			}
		}
	}
	
	@Override
	public String toString(){
		 String out = "Size: " + config.Size.getX()*config.Size.getY() + " Entitys: " + config.getEntityCount(); 
		 return out;
	 }
	
	public void setNewEntity(Entity entity){
		for(int i =0; i< container.container.length; i++){
			if(container.container[i]== null){
				container.container[i]= entity;
				break;
			}
		}
	}
	
	public void delete(Entity e){
		container.delete(e);
	}
	
	public void insert(Entity e){
		container.insert(e);
	}
	
	private void setStartEntitys() {
			for(int x = 0; x < config.Size.getX(); x++){
				container.insert(new Wall(getNewID(),new XY(x,0)));
			}
			for(int x = 0; x < config.Size.getX(); x++){
				container.insert(new Wall(getNewID(),new XY(x,config.Size.getY()-1)));
			}
			for(int y = 1; y < config.Size.getY()-1; y++){
				container.insert(new Wall(getNewID(),new XY(0,y)));
			}
			for(int y = 1; y < config.Size.getY()-1; y++){
				container.insert(new Wall(getNewID(),new XY(config.Size.getX()-1,y)));
			}
			for (int i = 0; i < config.badPlantCount; i++){
				container.insert(new BadPlant(getNewID(),rndmPos()));
			}
			for (int i = 0; i < config.goodPlantCount; i++){
				container.insert(new GoodPlant(getNewID(),rndmPos()));
			}
			for (int i = 0; i < config.badBeastCount; i++){
				container.insert(new BadBeast(getNewID(),rndmPos()));
        	}
			for (int i = 0; i < config.goodBeastCount; i++){
				container.insert(new GoodBeast(getNewID(),rndmPos()));
        	}
			for(int i = 0; i < config.wallCount2; i++){
				container.insert(new Wall(getNewID(),rndmPos()));
			}
			try {
				for(int i = 0; i < config.playerCount;i++){
					Class<?> cl = Class.forName(config.Bots[i]);
					Constructor<?> constructor = cl.getConstructor(int.class,XY.class,int.class);
					Object o = constructor.newInstance(getNewID(),rndmPos(),config.energy[i]);
					container.insert((Entity) o);
				}
				
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.out.println("Class not found");
				e.printStackTrace();
			}
	}

	
	public FlattenedBoard flatten(){
		FlattenedBoard fb = new FlattenedBoard(this);
		return fb;
	}
	
	public int getNewID(){
		return recentID++;
	}
	
	public XY rndmPos(){
		XY pos = XY.rndmXY(config.Size.getX(),config.Size.getY());
		if(!isPositionEmpty(pos)){
			pos = rndmPos();
		}
		return pos;
	}
	
	public boolean isPositionEmpty(XY pos){
		return (flatten().getEntityAt(pos.getX(), pos.getY())== null);
	}
	

}
