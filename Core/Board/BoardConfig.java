package Core.Board;
import Help.XY;



public class BoardConfig {
	
	protected XY Size = new XY(40, 25);
	protected final int wallCount = (Size.getX()*2)+((Size.getY()-2)*2);
    protected final int badBeastCount = 0;
    protected final int goodBeastCount = 5;
    protected final int goodPlantCount = 5;
    protected final int badPlantCount = 5;
    protected final int wallCount2 = 0;

    protected int [] energy = {1000,2000};
    protected String []Bots = {"Bot.BotImpl.MasterSquirrelBot","Bot.BotImpl.BastiBot"};
    protected int botCount = 2;
    protected boolean playerMode = true;
    protected final int standardDuration = 50;
    public int duration = standardDuration;
    public int rounds;
    
    
   
	public int EntityCount(){
		return wallCount + badBeastCount + goodBeastCount +
                goodPlantCount + badPlantCount;
	}
    
    public int getEntityCount(){
    	return EntityCount();
    }
    
    
    public int getSize(){
    	return Size.getX()*Size.getY();
    }
   
    public void setBotCount(int count){
    	this.botCount = count;
    }
    
    public int getBotCount(){
    	return botCount;
    }
}
