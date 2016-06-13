package Core.Board;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.sun.javafx.css.StyleCacheEntry.Key;

import Bot.BotControllerFactory;
import Bot.ControllerFactory;
import Bot.BotImpl.MasterSquirrelBot;
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
import Scanner.CommandTypeMethods;

public class Board {
	BoardConfig config;
	Map<String, ArrayList<Integer>> Highscore = new HashMap<>();
	ArrayList<Entity> container; 
	private static int recentID;
	public Logger logger = Logger.getLogger(ControllerContextImplMini.class.getName());
	GuidedMasterSquirrel master;
	protected int duration;
	private int rounds = 0;
	String result = "";
	
	
	
	public Board(){
		this.config = new BoardConfig();
		this.duration=config.getStandardDuration();
		container = new ArrayList<Entity>();
		getHighscore();
		setStartEntities();
		setPlayer();
		setBots();
		
	}
	
	
	
	public XY getSize(){
		return config.getSize();
	}
	
	public void update(){
		duration--;
		System.out.println(duration);
		for(int i = 0;i<container.size();i++){
			container.get(i).nextStep(flatten());
		}
		if(duration == 0){
			try {
				if(rounds>=config.getRounds()){
					setNewHighscore2();
					openHighscore();
					System.exit(0);
				}
				Thread.sleep(5000);
				duration=config.getStandardDuration();
				deleteStartEntitys();
				setStartEntities();
				rounds++;
				System.out.println("----Runde "+rounds+"!!----");
			}catch (InterruptedException e) {
				System.out.println("Irgendwas doofes ist passiert :/");
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public String toString(){
		 String out = "Size: " + config.getSize().getX()*config.getSize().getY(); 
		 return out;
	 }
	
	public void delete(Entity e){
		container.remove(e);
	}
	
	public void insert(Entity e){
		container.add(e);
	}
	
	
	
		
	private void setWalls(){
		for(int x = 0; x < config.getSize().getX(); x++){
			container.add(new Wall(getNewID(),new XY(x,0)));
		}
		for(int x = 0; x < config.getSize().getX(); x++){
			container.add(new Wall(getNewID(),new XY(x,config.getSize().getY()-1)));
		}
		for(int y = 1; y < config.getSize().getY()-1; y++){
			container.add(new Wall(getNewID(),new XY(0,y)));
		}
		for(int y = 1; y < config.getSize().getY()-1; y++){
			container.add(new Wall(getNewID(),new XY(config.getSize().getX()-1,y)));
		}
	}
	
	private void setStartEntities(){
		setWalls();
		for (int i = 0; i < config.getBadPlantCount(); i++){
			container.add(new BadPlant(getNewID(),rndmPos()));
		}
		for (int i = 0; i < config.getGoodPlantCount(); i++){
			container.add(new GoodPlant(getNewID(),rndmPos()));
		}
		for (int i = 0; i < config.getBadBeastCount(); i++){
			container.add(new BadBeast(getNewID(),rndmPos()));
    	}
		for (int i = 0; i < config.getGoodBeastCount(); i++){
			container.add(new GoodBeast(getNewID(),rndmPos()));
    	}
		for(int i = 0; i < config.getWallCount2(); i++){
			container.add(new Wall(getNewID(),rndmPos()));
		}
		
	}
	
	public void setBots(){
		for(int i = 0; i < config.botCount;i++){
			Class<?> cl;
			try {
				cl = Class.forName(config.Bots[i]);
				BotControllerFactory instance = (BotControllerFactory) cl.newInstance();
				container.add(new MasterSquirrelBot(getNewID(), rndmPos(),instance));
			} catch ( SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | ClassNotFoundException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		
//		try {
//			FileReader fr = new FileReader(getDir()+"/src/Core/Board/config.txt");
//			BufferedReader br = new BufferedReader(fr);
//				try {
//					br.readLine();
//					String bCount = br.readLine();
//					String[]s = bCount.split("#");
//					int botCount = Integer.parseInt(s[1]);
//					config.setBotCount(botCount);
//					for(int i = 0; i < config.botCount;i++){
//						Class<?> cl = Class.forName(br.readLine());
//						Constructor<?> constructor = cl.getConstructor(int.class,XY.class,int.class);
//						container.add((Entity) constructor.newInstance(getNewID(),rndmPos(),Integer.parseInt(br.readLine())));
//					}
//					br.close();
//				}catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
//					System.out.println("Class not found");
//					e.printStackTrace();
//				}
//
//				}catch (FileNotFoundException g) {
//					try {
//						for(int i = 0; i < config.botCount;i++){
//							Class<?> cl = Class.forName(config.Bots[i]);
//							Constructor<?> constructor = cl.getConstructor(int.class,XY.class,int.class);
//							container.add((Entity) constructor.newInstance(getNewID(),rndmPos(),config.energy[0]));
//							g.printStackTrace();
//						}
//					}catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException d) {
//						System.out.println("Class not found");
//						d.printStackTrace();
//					}
//						
//				}
//				
			
	}

	public void setPlayer(){
		if(config.isPlayerMode()){
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
			fw = new FileWriter(getDir()+"/src/Core/Board/Highscore.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i<container.size();i++){
				if(container.get(i) instanceof MasterSquirrel){
					String key = container.get(i).getClass().getName();
					Integer newHigh = container.get(i).getEnergy();
					
					if(Highscore.get(key)==null){
						ArrayList<Integer> l = new ArrayList<>();
						l.add(newHigh);
						Highscore.put(key, l);
					}else{
						Highscore.get(key).add(newHigh);
						Collections.sort(Highscore.get(key));
						Collections.reverse(Highscore.get(key));
					}
					String s = "";
					for(int j=0;j<Highscore.get(key).size();j++){
						s += Highscore.get(key).get(j);
						s += "\t";
					}
					bw.write(key+"\t"+ s);
					bw.newLine();
					
				}
			}
			bw.close();
		} catch (IOException e1) {
			System.err.println("Datei wurde nicht gefunden werden");
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
			
		}
		
	}
	
	public void setNewHighscore2(){
		FileWriter fw;
		try {
			fw = new FileWriter(getDir()+"/src/Core/Board/Highscore.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i<container.size();i++){
				if(container.get(i) instanceof MasterSquirrel){
					String key = container.get(i).getClass().getName();
					Integer newHigh = container.get(i).getEnergy();
			
					if(Highscore.get(key)==null){
						ArrayList<Integer> list = new ArrayList<>();
						list.add(newHigh);
						Highscore.put(key, list);
					}else{
						Highscore.get(key).add(newHigh);
						Collections.sort(Highscore.get(key));
						Collections.reverse(Highscore.get(key));
					
					}
					
					String s = "";
					for(int j = 0;j < Highscore.get(key).size(); j++){
						s += Highscore.get(key).get(j);
						s += "\t";
					}
					bw.write(key+"\t" + s);
					bw.newLine();
					
				}	
				
			}
			bw.close();
		}catch(IOException e1) {
			System.err.println("err Highscore2");
		}
		
	}
	
	

	public void getHighscore(){
		FileReader fr;
		try {
			fr = new FileReader(getDir()+"/src/Core/Board/Highscore.txt");
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
		}
		
		
	}
	
	
	public void printHighScore(){
		//logger.log(Level.INFO, Highscore.toString());
		System.out.println(Highscore);
	}
	
	public void openHighscore(){
        File file = new File(getDir()+"/src/Core/Board/Highscore.txt");
         
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }
         
        Desktop desktop = Desktop.getDesktop();
        if(file.exists())
			try {
				desktop.open(file);
			} catch (IOException e) {
				System.err.println("can not open Highscore.txt");
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
		XY pos = XY.rndmXY(config.getSize().getX(),config.getSize().getY());
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
	
	
	private void setStart(){
		FileReader fr;
		try {
			fr = new FileReader(getDir()+"/src/Core/Board/config.txt");
			BufferedReader br = new BufferedReader(fr);
			while(true){
				String high = br.readLine();
				if(high.equals("Start:")){
					break;
				}
			}
			String size = br.readLine();
			String[] splitt = size.split("#");
			config.setSize(new XY(Integer.parseInt(splitt[1]),Integer.parseInt(splitt[2])));
			setWalls();
			for(int e = 0; e<4;e++){
				String count = br.readLine();
				String[] spl = count.split("#");
				for(int i =0;i<Integer.parseInt(spl[1]);i++){
					Class<?> cl = Class.forName(spl[0]);
					Constructor<?> constructor = cl.getConstructor(int.class,XY.class);
					container.add((Entity) constructor.newInstance(getNewID(),rndmPos()));
				}
			}
			br.close();
			
		} catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			container.clear();
			setStartEntities();
			System.err.println("Irgendwas doofes ist in Config_Start: passiert :/");
			e.printStackTrace();
		}
	}
	
	
	public void setStartProperty(){
		InputStream inputStream;
		try{
			Properties props = new Properties();
			String propFileName = "config.properties";
			
		
		
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
			if(inputStream != null){
				props.load(inputStream);
			}else{
				System.err.println("Properties not found!");
				this.config = new BoardConfig();
				setStartEntities();
				return;
			}
			
//			int x = Integer.parseInt(props.getProperty("xValue"));
//			int y = Integer.parseInt(props.getProperty("yValue"));
//			config.setSize ( new XY(x,y));
//			int badBeastCount = Integer.parseInt(props.getProperty("Entities.BadBeast"));
//			int goodBeastCount = Integer.parseInt(props.getProperty("Entities.GoodBeast"));
//			int goodPlantCount = Integer.parseInt(props.getProperty("Entities.BadPlant"));
//			int badPlantCount = Integer.parseInt(props.getProperty("Entities.GoodPlant"));
//			config.setBadBeastCount(badBeastCount);
//			config.setBadPlantCount(badPlantCount);
//			config.setGoodBeastCount(goodBeastCount);
//			config.setGoodPlantCount(goodPlantCount);
			
//			String bot1=props.getProperty("bot1");
//			String bot2=props.getProperty("bot2");
//			int botCount =  Integer.parseInt(props.getProperty("botcount"));
//			config.botCount=botCount;
//			config.Bots[0]=bot1;
//			config.Bots[1]=bot2;
//			config.playerMode=Boolean.parseBoolean("playerMode");
//			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e);
			container.clear();
			setStartEntities();
		} finally {
			
			
		}
			
	}
	
	public static String getDir(){
		String dir = System.getProperty("user.dir");
		dir = dir.replace('\\', '/');
		return dir;
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
	
//--------------------------------------
//	try {
//		FileReader fr = new FileReader(getDir()+"/src/Core/Board/config.txt");
//		BufferedReader br = new BufferedReader(fr);
//			try {
//				br.readLine();
//				String bCount = br.readLine();
//				String[]s = bCount.split("#");
//				int botCount = Integer.parseInt(s[1]);
//				config.setBotCount(botCount);
//				for(int i = 0; i < config.botCount;i++){
//					Class<?> cl = Class.forName(br.readLine());
//					Constructor<?> constructor = cl.getConstructor(int.class,XY.class,int.class);
//					container.add((Entity) constructor.newInstance(getNewID(),rndmPos(),Integer.parseInt(br.readLine())));
//				}
//				br.close();
//			}catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
//				System.out.println("Class not found");
//				e.printStackTrace();
//			}
//
//			}catch (FileNotFoundException g) {
//				try {
//					for(int i = 0; i < config.botCount;i++){
//						Class<?> cl = Class.forName(config.Bots[i]);
//						Constructor<?> constructor = cl.getConstructor(int.class,XY.class,int.class);
//						container.add((Entity) constructor.newInstance(getNewID(),rndmPos(),config.energy[0]));
//						g.printStackTrace();
//					}
//				}catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException d) {
//					System.out.println("Class not found");
//					d.printStackTrace();
//				}
//					
//			}
			

	
	
//	Enumeration<?> keys = props.keys();
//	while(keys.hasMoreElements()){
//		String key = (String) keys.nextElement();
//		String value = props.getProperty(key);
//		if(key.startsWith("Entities.")){
//			
//			for(int i = 0; i < Integer.parseInt(value);i++){
//				Class<?> cl = Class.forName(key);
//				Constructor<?> constructor = cl.getConstructor(int.class,XY.class);
//				container.add((Entity) constructor.newInstance(getNewID(),rndmPos()));
//			}
//		}
//	}
//	setWalls();
//	while(keys.hasMoreElements()){
//		String key = (String) keys.nextElement();
//		int value = Integer.parseInt(props.getProperty(key));
//		Class<?> cl = Class.forName("Core.Board.BoardConfig");
//		Method method = cl.getDeclaredMethod("set"+key,int.class);
//		method.invoke(new BoardConfig(), value);
//	}


}
