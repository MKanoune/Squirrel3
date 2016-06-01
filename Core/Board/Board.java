package Core.Board;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import Entities.BadBeast;
import Entities.BadPlant;
import Entities.Entity;
import Entities.GoodBeast;
import Entities.GoodPlant;
import Entities.GuidedMasterSquirrel;
import Entities.MasterSquirrel;
import Entities.MasterSquirrelBot;
import Entities.Wall;
import Entities.MiniSquirrelBot.ControllerContextImplMini;
import Help.EntityType;
import Help.XY;

public class Board {
	BoardConfig config;
//	EntitySet container;
	Map<String, Integer> Highscore;
	Vector<Entity> vectorContainer; 
	private static int recentID;
	public Logger logger = Logger.getLogger(ControllerContextImplMini.class.getName());
	
	public Board(){
		this.config = new BoardConfig();
		//container = new EntitySet(config.Size);
		vectorContainer = new Vector<Entity>();
		setStartEntities();
		setBots();
		for(int i =0; i < vectorContainer.size();i++){
			System.out.println(vectorContainer.get(i));
		}
	}
	
	
	public XY getSize(){
		return config.Size;
	}
	
	public void update(){
		config.duration--;
		System.out.println(config.duration);
//		for(int i=0;i<container.container.length;i++){
//			if(container.container[i]!=null){
//			container.container[i].nextStep(flatten());
		for(int i = 0; i< vectorContainer.size();i++){
			vectorContainer.get(i).nextStep(flatten());
			if(config.duration == 0){
				try {
					Thread.sleep(10000);
					config.duration = config.standardDuration;
					deleteStartEntitys();
					setHighScore();
					printHighScore();
					setStartEntities();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@Override
	public String toString(){
		 String out = "Size: " + config.Size.getX()*config.Size.getY() + " Entitys: " + config.getEntityCount(); 
		 return out;
	 }
	
	public void delete(Entity e){
		vectorContainer.remove(e);
	}
	
	public void insert(Entity e){
		vectorContainer.addElement(e);
	}
	
	private void setStartEntitys() {
//			for(int x = 0; x < config.Size.getX(); x++){
//				container.insert(new Wall(getNewID(),new XY(x,0)));
//			}
//			for(int x = 0; x < config.Size.getX(); x++){
//				container.insert(new Wall(getNewID(),new XY(x,config.Size.getY()-1)));
//			}
//			for(int y = 1; y < config.Size.getY()-1; y++){
//				container.insert(new Wall(getNewID(),new XY(0,y)));
//			}
//			for(int y = 1; y < config.Size.getY()-1; y++){
//				container.insert(new Wall(getNewID(),new XY(config.Size.getX()-1,y)));
//			}
//			for (int i = 0; i < config.badPlantCount; i++){
//				container.insert(new BadPlant(getNewID(),rndmPos()));
//			}
//			for (int i = 0; i < config.goodPlantCount; i++){
//				container.insert(new GoodPlant(getNewID(),rndmPos()));
//			}
//			for (int i = 0; i < config.badBeastCount; i++){
//				container.insert(new BadBeast(getNewID(),rndmPos()));
//        	}
//			for (int i = 0; i < config.goodBeastCount; i++){
//				container.insert(new GoodBeast(getNewID(),rndmPos()));
//        	}
//			for(int i = 0; i < config.wallCount2; i++){
//				container.insert(new Wall(getNewID(),rndmPos()));
//			}
//			
	}

	
	private void setStartEntities(){
		for(int x = 0; x < config.Size.getX(); x++){
			vectorContainer.add(new Wall(getNewID(),new XY(x,0)));
		}
		for(int x = 0; x < config.Size.getX(); x++){
			vectorContainer.add(new Wall(getNewID(),new XY(x,config.Size.getY()-1)));
		}
		for(int y = 1; y < config.Size.getY()-1; y++){
			vectorContainer.add(new Wall(getNewID(),new XY(0,y)));
		}
		for(int y = 1; y < config.Size.getY()-1; y++){
			vectorContainer.add(new Wall(getNewID(),new XY(config.Size.getX()-1,y)));
		}
		for (int i = 0; i < config.badPlantCount; i++){
			vectorContainer.add(new BadPlant(getNewID(),rndmPos()));
		}
		for (int i = 0; i < config.goodPlantCount; i++){
			vectorContainer.add(new GoodPlant(getNewID(),rndmPos()));
		}
		for (int i = 0; i < config.badBeastCount; i++){
			vectorContainer.add(new BadBeast(getNewID(),rndmPos()));
    	}
		for (int i = 0; i < config.goodBeastCount; i++){
			vectorContainer.add(new GoodBeast(getNewID(),rndmPos()));
    	}
		for(int i = 0; i < config.wallCount2; i++){
			vectorContainer.add(new Wall(getNewID(),rndmPos()));
		}
		
	}
	
	
	
	public void setBots(){
		try {
			for(int i = 0; i < config.playerCount;i++){
				Class<?> cl = Class.forName(config.Bots[i]);
				Constructor<?> constructor = cl.getConstructor(int.class,XY.class,int.class);
				vectorContainer.addElement((Entity) constructor.newInstance(getNewID(),rndmPos(),config.energy[i]));
			}
			
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println("Class not found");
			e.printStackTrace();
		}
	}

	public void deleteStartEntitys(){
		for(int i = vectorContainer.size()-1; i>=0;i--){
			if(!(vectorContainer.get(i) instanceof MasterSquirrel))
				vectorContainer.removeElementAt(i);
		}
			
	}
	

	public void setHighScore(){
		Highscore = new HashMap<>();
		for(int i = 0; i<vectorContainer.size();i++){
			if(vectorContainer.get(i) instanceof MasterSquirrel){
				Highscore.put(vectorContainer.get(i).getClass().getName(), vectorContainer.get(i).getEnergy());
			}
		}
	}
	
	public void printHighScore(){
		logger.log(Level.INFO, Highscore.toString());
		System.out.println(Highscore);
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
