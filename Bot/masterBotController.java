package Bot;

import Entities.BadBeast;
import Entities.BadPlant;
import Entities.Entity;
import Entities.GoodBeast;
import Entities.GoodPlant;
import Entities.MasterSquirrel;
import Entities.MasterSquirrelBot;
import Entities.MiniSquirrelBot;
import Help.XY;

public class masterBotController implements BotController {
	private XY lastMove;
	
	
	public masterBotController(){
		this.lastMove = XY.RandomMoveCommand();
	}
	
	
	@Override
	public void nextStep(ControllerContext view) {
		//this.view = view;
		/*Entity target =null;// view.nearestEntity()
		if(target instanceof BadBeast){
			System.out.println("Escape!!!!!!!!!!!");
			XY move = bot.xy.escape(bot.xy, target.xy);
			view.move(move);
		}else if(target instanceof GoodBeast || target instanceof GoodPlant){
			System.out.println("ATTACK Good!!!!!!!");
			XY move = bot.xy.attack(bot.xy, target.xy);
			view.move(move);
		}else if(target instanceof MiniSquirrelBot && !bot.checkSlave(target)){
			System.out.println("ATTACK MINI!!!!!!!!");
			XY move = bot.xy.attack(bot.xy, target.xy);
			view.move(move);	
		}else if(target instanceof BadPlant){
			System.out.println("BADPLANT");
		}else {
			
			System.out.println("RANDOM!!!!");
			view.move(lastMove);			
			if(bot.getMoveCount()<= 0){
				lastMove = XY.RandomMoveCommand();
				bot.setMoveCount(3);
				return;
			}
			bot.setMoveCount(-1);
		}*/
	}
	
}
