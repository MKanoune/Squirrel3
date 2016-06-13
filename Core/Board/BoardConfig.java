package Core.Board;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import Help.XY;



public final class BoardConfig {
	private Map<String,Integer> values = new HashMap<String,Integer>();
	private XY Size;
	private int wallCount;
    private int wallCount2 = 0;
    protected int [] energy = {1000,2000};
    protected String []Bots = {"Bot.ControllerFactory","Bot.ControllerFactory"};
    protected int botCount = 2;
    protected final int standardDuration = 50;
    public int rounds = 2;
    
    
    
    
    public BoardConfig(){
		setDefault();
    	setProp();
    	this.Size = new XY(values.get("sizeX"), values.get("sizeY"));
    	this.wallCount = ((Size.getX()*2)+((Size.getY()-2)*2));
	}
    
    public void setDefault(){
    	values.put("sizeX",40);
    	values.put("sizeY", 25);
    	values.put("badBeastCount", 0);
    	values.put("goodBeastCount", 5);
    	values.put("goodPlantCount", 5);
    	values.put("badPlantCount", 5);
    	values.put("playerMode", 1);
    	
    }
   
    
    public void setProp(){
    	InputStream inputStream;
		try{
			Properties props = new Properties();
			String propFileName = "config.properties";
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			
			if(inputStream != null){
				props.load(inputStream);
			}else{
				System.err.println("Properties not found!");
				for (Entry<String, Integer> e : values.entrySet()){
					String value = String.valueOf(e.getValue());
					props.setProperty(e.getKey(),value);
				}
				FileOutputStream fos = new FileOutputStream(Board.getDir()+"/bin/config.properties");
				
				props.store(fos, null);
				fos.flush();
				return;
			}
			
			for (Entry<String, Integer> e : values.entrySet()){
			    Integer i = Integer.parseInt(props.getProperty(e.getKey()));
			    values.put(e.getKey(),i);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e);
		}
    
    }

    public XY getSize(){
    	return Size;
    }
 
    
    public int getBotCount(){
    	return botCount;
    }

	public int getBadBeastCount() {
		return values.get("badBeastCount");
	}


	public int getBadPlantCount() {
		return values.get("badPlantCount");
	}

	

	public int getGoodPlantCount() {
		return values.get("goodPlantCount");
	}

	

	public int getGoodBeastCount() {
		return values.get("goodBeastCount");
	}

	

	public int getWallCount2() {
		return wallCount2;
	}
	public int getWallCount(){
		return wallCount;
	}
	

	public void setStartProperty(){
//		try{
//			InputStream inputStream = null;
//			Properties props = new Properties();
//			String propFileName = "config.properties";
//			
//		
//		
//			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//		
//			if(inputStream != null){
//				props.load(inputStream);
//			}else{
//				System.err.println("Properties not found!");
//				return;
//			}
//			props.list(System.out);
//			int x = Integer.parseInt(props.getProperty("xValue"));
//			int y = Integer.parseInt(props.getProperty("yValue"));
//			setSize ( new XY(x,y));
//			int badBeastCount = Integer.parseInt(props.getProperty("Entities.BadBeast"));
//			int goodBeastCount = Integer.parseInt(props.getProperty("Entities.GoodBeast"));
//			int goodPlantCount = Integer.parseInt(props.getProperty("Entities.BadPlant"));
//			int badPlantCount = Integer.parseInt(props.getProperty("Entities.GoodPlant"));
//			setBadBeastCount(badBeastCount);
//			setBadPlantCount(badPlantCount);
//			setGoodBeastCount(goodBeastCount);
//			setGoodPlantCount(goodPlantCount);
//			
//			String bot1=props.getProperty("bot1");
//			String bot2=props.getProperty("bot2");
//			int botCount =  Integer.parseInt(props.getProperty("botcount"));
//			this.botCount=botCount;
//			Bots[0]=bot1;
//			Bots[1]=bot2;
//			
//			
//		}catch(Exception e) {
//			System.out.println("Exception: " + e);
//		} finally {
//			
//		}
			
	}

	public void setSize(XY xy) {
		
		
	}

	public boolean isPlayerMode() {
		if(values.get("playerMode")==1){
			return true;
		}else{
			return false;
		}
		
	}

}
