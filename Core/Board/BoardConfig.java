package Core.Board;
import Help.XY;



public class BoardConfig {
	
	public final XY Size = new XY(40, 25);
    public final int wallCount = (Size.getX()*2)+((Size.getY()-2)*2);
    public final int badBeastCount = 0;
    public final int goodBeastCount = 25;
    public final int goodPlantCount = 15;
    public final int badPlantCount = 15;
    public final int wallCount2 = 0;
   
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
   

}
