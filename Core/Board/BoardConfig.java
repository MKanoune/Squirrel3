package Core.Board;
import java.io.InputStream;
import java.util.Properties;

import Help.XY;



public class BoardConfig {
	
	private XY Size = new XY(40, 25);
	private int wallCount = (Size.getX()*2)+((Size.getY()-2)*2);
    private int badBeastCount = 0;
    private int goodBeastCount = 5;
    private int goodPlantCount = 5;
    private int badPlantCount = 5;
    private int wallCount2 = 0;
    protected int [] energy = {1000,2000};
    protected String []Bots = {"Bot.ControllerFactory","Bot.ControllerFactory"};
    protected int botCount = 2;
    
    
    protected boolean playerMode = true;
    protected final int standardDuration = 50;
    public int rounds = 2;
    
    

    public XY getSize(){
    	return Size;
    }
    
    public void setSize(XY size){
    	this.Size=size;
    }
   
    public void setBotCount(int count){
    	this.botCount = count;
    }
    
    public int getBotCount(){
    	return botCount;
    }

	public int getBadBeastCount() {
		return badBeastCount;
	}

	public void setBadBeastCount(int badBeastCount) {
		this.badBeastCount = badBeastCount;
	}

	public int getBadPlantCount() {
		return badPlantCount;
	}

	public void setBadPlantCount(int badPlantCount) {
		this.badPlantCount = badPlantCount;
	}

	public int getGoodPlantCount() {
		return goodPlantCount;
	}

	public void setGoodPlantCount(int goodPlantCount) {
		this.goodPlantCount = goodPlantCount;
	}

	public int getGoodBeastCount() {
		return goodBeastCount;
	}

	public void setGoodBeastCount(int goodBeastCount) {
		this.goodBeastCount = goodBeastCount;
	}

	public int getWallCount2() {
		return wallCount2;
	}

	public void setWallCount2(int wallCount2) {
		this.wallCount2 = wallCount2;
	}

	public void setStartProperty(){
		try{
			InputStream inputStream = null;
			Properties props = new Properties();
			String propFileName = "config.properties";
			
		
		
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
			if(inputStream != null){
				props.load(inputStream);
			}else{
				System.err.println("Properties not found!");
				return;
			}
			props.list(System.out);
			int x = Integer.parseInt(props.getProperty("xValue"));
			int y = Integer.parseInt(props.getProperty("yValue"));
			setSize ( new XY(x,y));
			int badBeastCount = Integer.parseInt(props.getProperty("Entities.BadBeast"));
			int goodBeastCount = Integer.parseInt(props.getProperty("Entities.GoodBeast"));
			int goodPlantCount = Integer.parseInt(props.getProperty("Entities.BadPlant"));
			int badPlantCount = Integer.parseInt(props.getProperty("Entities.GoodPlant"));
			setBadBeastCount(badBeastCount);
			setBadPlantCount(badPlantCount);
			setGoodBeastCount(goodBeastCount);
			setGoodPlantCount(goodPlantCount);
			
			String bot1=props.getProperty("bot1");
			String bot2=props.getProperty("bot2");
			int botCount =  Integer.parseInt(props.getProperty("botcount"));
			this.botCount=botCount;
			Bots[0]=bot1;
			Bots[1]=bot2;
			
			
		}catch(Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			
		}
			
	}
}
