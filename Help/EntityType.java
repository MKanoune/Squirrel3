package Help;

import Bot.BotImpl.MasterSquirrelBot;
import Bot.BotImpl.MiniSquirrelBot;
import Entities.*;
import javafx.scene.paint.Color;



public enum EntityType {
	Wall('X',Color.DARKSLATEGRAY,"oval"),
	GoodPlant('T',Color.MEDIUMSEAGREEN,"rect"),
	BadPlant('P',Color.DARKGREEN,"rect"),
	GuidedMasterSquirrel('S',Color.ROYALBLUE,"oval"),	
	MiniSquirrelBot('m',Color.DEEPSKYBLUE,"oval"),
	BadBeast('B',Color.INDIANRED,"oval"),
	GoodBeast('G',Color.CHOCOLATE,"oval"),
	MasterSquirrelBot('s',Color.MIDNIGHTBLUE,"oval"),
	BastiBot('b',Color.RED,"oval"),
	Null('.',Color.BURLYWOOD,"rect");

	
	//public Entity entity;
	public char token;
	public Color color;
	public String paintType;
	
	private EntityType(char entity,Color color,String paintType){
		this.token = entity;
		this.color = color;
		this.paintType = paintType;
		
		
	}
	
	 public static EntityType getEntityType(Entity entity)
	    {
	        if (entity != null)
	        {
	            if(entity.getClass() == Wall.class)
	            {
	                return Wall;
	            }
	            else if(entity.getClass() == GoodBeast.class)
	            {
	                return GoodBeast;
	            }
	            else if(entity.getClass() == BadBeast.class)
	            {
	                return  BadBeast;
	            }
	            else if(entity.getClass() == GoodPlant.class)
	            {
	                return  GoodPlant;
	            }
	            else if(entity.getClass() == BadPlant.class)
	            {
	                return BadPlant;
	            }
	            else if(entity.getClass() == MasterSquirrelBot.class)
	            {
	                return MasterSquirrelBot;
	            }
	            else if(entity.getClass() == GuidedMasterSquirrel.class)
	            {
	                return  GuidedMasterSquirrel;
	            }
	            else if(entity.getClass() == MiniSquirrelBot.class)
	            {
	                return MiniSquirrelBot;
	            
	            }

	        }
	        return Null;
	    }
	
}
