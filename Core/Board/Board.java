package Core.Board;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import Bot.BotImpl.MiniSquirrelBot.ControllerContextImplMini;
import Entities.BadBeast;
import Entities.BadPlant;
import Entities.Entity;
import Entities.GoodBeast;
import Entities.GoodPlant;
import Entities.GuidedMasterSquirrel;
import Entities.MasterSquirrel;
import Entities.Wall;
import Help.XY;

public class Board {
	BoardConfig config;
	Map<String, ArrayList<Integer>> Highscore = new HashMap<>();//arraylist
	ArrayList<Entity> container; 
	private static int recentID;
	public Logger logger = Logger.getLogger(ControllerContextImplMini.class.getName());
	GuidedMasterSquirrel master;
	int rounds = 2;
	int r = 1;
	
	
	public Board(){
		this.config = new BoardConfig();
		container = new ArrayList<Entity>();
		setStartEntities();
		setPlayer();
		setBots();
		getHighscore();
	}
	
	
	public XY getSize(){
		return config.Size;
	}
	
	public void update(){
		config.duration--;
		System.out.println(config.duration);
		for(int i = 0;i<container.size();i++){
			container.get(i).nextStep(flatten());
		}
		if(config.duration == 0){
			System.out.println("Zwischenstand: "+Highscore);
			try {
				if(r>=rounds){
					setNewHighscore();
					System.exit(0);
				}
				Thread.sleep(10000);
				config.duration = config.standardDuration;
				deleteStartEntitys();
				setStartEntities();
				r++;
				System.out.println("----Runde "+r+"!!----");
			}catch (InterruptedException e) {
				System.out.println("Irgendwas doofes ist passiert :/");
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public String toString(){
		 String out = "Size: " + config.Size.getX()*config.Size.getY() + " Entitys: " + config.getEntityCount(); 
		 return out;
	 }
	
	public void delete(Entity e){
		container.remove(e);
	}
	
	public void insert(Entity e){
		container.add(e);
	}
	

	
	private void setStartEntities(){
		for(int x = 0; x < config.Size.getX(); x++){
			container.add(new Wall(getNewID(),new XY(x,0)));
		}
		for(int x = 0; x < config.Size.getX(); x++){
			container.add(new Wall(getNewID(),new XY(x,config.Size.getY()-1)));
		}
		for(int y = 1; y < config.Size.getY()-1; y++){
			container.add(new Wall(getNewID(),new XY(0,y)));
		}
		for(int y = 1; y < config.Size.getY()-1; y++){
			container.add(new Wall(getNewID(),new XY(config.Size.getX()-1,y)));
		}
		for (int i = 0; i < config.badPlantCount; i++){
			container.add(new BadPlant(getNewID(),rndmPos()));
		}
		for (int i = 0; i < config.goodPlantCount; i++){
			container.add(new GoodPlant(getNewID(),rndmPos()));
		}
		for (int i = 0; i < config.badBeastCount; i++){
			container.add(new BadBeast(getNewID(),rndmPos()));
    	}
		for (int i = 0; i < config.goodBeastCount; i++){
			container.add(new GoodBeast(getNewID(),rndmPos()));
    	}
		for(int i = 0; i < config.wallCount2; i++){
			container.add(new Wall(getNewID(),rndmPos()));
		}
		
	}
	
	
	
	public void setBots(){
		try {
			FileReader fr = new FileReader("C:/Users/basti_000/workspace/Squirrel/src/Core/Board/config.txt");
			BufferedReader br = new BufferedReader(fr);
				try {
					int botCount = Integer.parseInt(br.readLine());
					config.setBotCount(botCount);
					for(int i = 0; i < botCount;i++){
						Class<?> cl = Class.forName(br.readLine());
						Constructor<?> constructor = cl.getConstructor(int.class,XY.class,int.class);
						container.add((Entity) constructor.newInstance(getNewID(),rndmPos(),Integer.parseInt(br.readLine())));
					}
					br.close();
				}catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
					System.out.println("Class not found");
					e.printStackTrace();
				}

				}catch (FileNotFoundException g) {
					try {
						for(int i = 0; i < config.botCount;i++){
							Class<?> cl = Class.forName(config.Bots[i]);
							Constructor<?> constructor = cl.getConstructor(int.class,XY.class,int.class);
							container.add((Entity) constructor.newInstance(getNewID(),rndmPos(),config.energy[0]));
							g.printStackTrace();
						}
					}catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException d) {
						System.out.println("Class not found");
						d.printStackTrace();
					}
						
				}
				
			
	}

	
	public void setPlayer(){
		if(config.playerMode){
			master = new GuidedMasterSquirrel(getNewID(),rndmPos()); 
			insert(master);
		}
	}
	
	public void deleteStartEntitys(){
		for(Iterator<Entity> iterator = container.iterator();iterator.hasNext();){
			Entity e = iterator.next();
			if(!(e instanceof MasterSquirrel)){
				iterator.remove();
			}
		}

	}
	
	
	

	public void setNewHighscore(){
		FileWriter fw;
		try {
			fw = new FileWriter("C:/Users/basti_000/workspace/Squirrel/src/Core/Board/Highscore.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i<container.size();i++){
				if(container.get(i) instanceof MasterSquirrel){
					String key = container.get(i).getClass().getName();
					Integer highscore = container.get(i).getEnergy();
					ArrayList<Integer> l = new ArrayList<>();
					if(Highscore.get(key)==null){
						l.add(highscore);
						Highscore.put(key, l);
					}else{
						l = Highscore.get(key);
						l.add(highscore);
						Collections.sort(l);
						Collections.reverse(l);
						Highscore.put(key, l);
					}
					String s = "";
					for(int j=0;j<l.size();j++){
						s += l.get(j);
						s += "\t";
					}
					bw.write(key+"\t"+ s);
					bw.newLine();
					
				}
			}
			bw.close();
		} catch (IOException e1) {
			System.err.println("Datei konnte nicht erstellt werden");
			for(int i = 0; i<container.size();i++){
				if(container.get(i) instanceof MasterSquirrel){
					String key = container.get(i).getClass().getName();
					ArrayList<Integer> l = new ArrayList<>();
					for(int j = 0; j<Highscore.get(key).size(); j++){
						l.add(Highscore.get(key).get(i));
					}
					Integer highscore = container.get(i).getEnergy();
					l.add(highscore);
					Highscore.put(key,l);
				}
			}
			e1.printStackTrace();
		}
		
	}
	
	
	

	public void getHighscore(){
		FileReader fr;
		try {
			fr = new FileReader("C:/Users/basti_000/workspace/Squirrel/src/Core/Board/Highscore.txt");
			BufferedReader br = new BufferedReader(fr);
			while(true){
				String high = br.readLine();
				if(high == null){
					break;
				}
				String [] splitted = high.split("\t");
				ArrayList<Integer> list = new ArrayList<>();
				for(int i = 1;i<splitted.length;i++){
					list.add(Integer.parseInt(splitted[i]));
				}
				Highscore.put(splitted[0],list);
			}
			System.out.println("Bisheriger Highscore: "+Highscore.toString());
			br.close();
			
		} catch (IOException e) {
			System.err.println("Kein bisheriger Highscore vorhanden");
			e.printStackTrace();
		}
		
		
	}
	
	
	public void printHighScore(){
		//logger.log(Level.INFO, Highscore.toString());
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
	
	public GuidedMasterSquirrel getMaster(){
		return master;
	}
	
	
//	public void setHighscore(){
//	try{
//		PrintWriter writer = new PrintWriter("C:/Users/basti_000/workspace/Squirrel/src/Core/Board/Highscore.txt");
//		for(int i = 0; i<container.size();i++){
//			if(container.get(i) instanceof MasterSquirrel){
//				String key = container.get(i).getClass().getName();
//				Integer highscore = container.get(i).getEnergy();
//				Highscore.put(key,highscore);
//				writer.write(key+" "+highscore);
//				writer.append(System.getProperty("line.separator"));
//			}
//		}
//		writer.close();
//	}catch(FileNotFoundException e){
//		System.err.println("Datei konnte nicht erstellt werden");
//		for(int i = 0; i<container.size();i++){
//			if(container.get(i) instanceof MasterSquirrel){
//				String key = container.get(i).getClass().getName()+i;
//				Integer highscore = container.get(i).getEnergy();
//				Highscore.put(key,highscore);
//			}
//		}
//		e.printStackTrace();
//	}
//}
//

	
	
	
	
	
	
	
	
	
	
//	private void setStartEntitys() {
//	for(int x = 0; x < config.Size.getX(); x++){
//		container.insert(new Wall(getNewID(),new XY(x,0)));
//	}
//	for(int x = 0; x < config.Size.getX(); x++){
//		container.insert(new Wall(getNewID(),new XY(x,config.Size.getY()-1)));
//	}
//	for(int y = 1; y < config.Size.getY()-1; y++){
//		container.insert(new Wall(getNewID(),new XY(0,y)));
//	}
//	for(int y = 1; y < config.Size.getY()-1; y++){
//		container.insert(new Wall(getNewID(),new XY(config.Size.getX()-1,y)));
//	}
//	for (int i = 0; i < config.badPlantCount; i++){
//		container.insert(new BadPlant(getNewID(),rndmPos()));
//	}
//	for (int i = 0; i < config.goodPlantCount; i++){
//		container.insert(new GoodPlant(getNewID(),rndmPos()));
//	}
//	for (int i = 0; i < config.badBeastCount; i++){
//		container.insert(new BadBeast(getNewID(),rndmPos()));
//	}
//	for (int i = 0; i < config.goodBeastCount; i++){
//		container.insert(new GoodBeast(getNewID(),rndmPos()));
//	}
//	for(int i = 0; i < config.wallCount2; i++){
//		container.insert(new Wall(getNewID(),rndmPos()));
//	}
//	
//}


}
