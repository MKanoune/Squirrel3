package Bot.BotImpl;


import Bot.BotController;
import Bot.ControllerContext;
import Bot.ControllerContextProxy;
import Bot.ControllerContextWImplode;
import Help.EntityType;
import Help.XY;


public class miniBotController implements BotController {
	private XY lastMove;

	
	public miniBotController(){
		this.lastMove = XY.RandomMoveCommand();
	}
	
	@Override
	public void nextStep(ControllerContext view){
		//view = (ControllerContextWImplode) ControllerContextProxy.newInstance(view);
		view.move(XY.RandomMoveCommand());
		final int viewMax = 10;
		XY pos = new XY((view.getViewUpperRight().getX() - viewMax ),(view.getViewLowerLeft().getY()-viewMax));
		System.out.println("Controller: "+pos.toString());
		
		
		
		
		
		
		
		
		
		
		/*EntityType next = null;
		for(int y = view.getViewUpperRight().getY(); y < view.getViewLowerLeft().getY(); y++){
			for(int x = view.getViewLowerLeft().getX(); x < view.getViewUpperRight().getX(); x++){
				EntityType victim = view.getEntityTypeAt(x, y);
				if(victim != null && victim != EntityType.Wall&& victim != EntityType.MasterSquirrelBot){// && victim!=EntityType.BadPlant 
					if(next != null){
						
						
					}else{
						next = victim;
//						System.out.println("Victim: "+victim);
//						System.out.println("next:   "+next );
					}
				}
				
			}
		}
		*/
	/*	Entity target = null;//view.nearestEntity();
		System.out.println("mini "+ bot.toString());
		if(target instanceof BadBeast){
			//System.out.println("Escape!!!!!!!!!!!");
			XY move = bot.xy.escape(bot.xy, target.xy);
			view.move(move);
		}else if(target instanceof GoodBeast || target instanceof GoodPlant){
			//System.out.println("ATTACK Good!!!!!!!");
			XY move = bot.xy.attack(bot.xy, target.xy);
			view.move(move);
		}else if(target instanceof MasterSquirrel && target != bot.getMaster()){
			//System.out.println("ESCAPE Squirrel!!!!!!!!");
			XY move = bot.xy.escape(bot.xy, target.xy);
			view.move(move);	
		}else if(target instanceof BadPlant){*/
			//System.out.println("BADPLANT");
//		}else if(target instanceof Wall){
//			System.out.println("ESCAPE Wall!!!!!!!!"+ target.xy.getX() + " " + target.xy.getY());
//			view.move(lastMove);			
//			if(bot.getMoveCount()<= 0){
//				lastMove = XY.RandomMoveCommand();
//				bot.setMoveCount(3);
//				return;
//			}
//			bot.setMoveCount(-1);
		/*}else {
			
			//System.out.println("RANDOM!!!!");
			view.move(lastMove);			
			if(bot.getMoveCount()<= 0){
				lastMove = XY.RandomMoveCommand();
				bot.setMoveCount(3);
				return;
			}
			bot.setMoveCount(-1);
		}*/
//		Entity enemy = view.nearestEnemy();
//		Entity victim = view.nearestVictim();
//		
//		if(enemy != null && victim != null){
//			if(view.isEnemyNext(enemy, victim)){
//				System.out.println("ESCAPE!!!!!!!!");
//				XY move = bot.xy.escape(bot.xy, victim.xy);
//				view.move(move);
//			}else{
//				System.out.println("ATTACK!!!!!!!");
//				XY move = bot.xy.attack(bot.xy, victim.xy);
//				view.move(move);
//			}	
//		}else if(enemy == null && victim != null){
//			XY move = bot.xy.attack(bot.xy, victim.xy);
//			view.move(move);
//		}else if(victim == null && enemy != null){
//			XY move = bot.xy.escape(bot.xy, enemy.xy);
//			view.move(move);
//		}else{
//			System.out.println("RANDOM!!!!");
//			view.move(XY.RandomMoveCommand());
//		}
	}

	
}
