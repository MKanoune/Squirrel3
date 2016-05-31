package Entities;

import java.util.logging.Level;
import java.util.logging.Logger;

import Bot.BotController;
import Bot.ControllerContextWImplode;
import Bot.ControllerFactory;
import Bot.BotImpl.miniBotController;
import Core.Board.EntityContext;
import Help.EntityType;
import Help.XY;


public class MiniSquirrelBot extends Squirrel {
	BotController controller;
	MasterSquirrel master;
	private int moveCount;
	private int counter =0;
	
	
	
	
	public MiniSquirrelBot(int miniID, int miniEnergy, XY xy, MasterSquirrel master) {
		super(miniID, miniEnergy, xy);
		this.master = master;
		ControllerFactory factory = new ControllerFactory();
		BotController controller = factory.createMiniBotController();
		this.controller = controller;
		moveCount = 3;
		
	}

	@Override
	public void nextStep(EntityContext context) {
	 	this.updateEnergy(-1);
	   	 if(timeOut > 0){
	        	updateTimeOut();
	        	return;
	     }
	   	ControllerContextImplMini view = this.new ControllerContextImplMini(context);
	   	counter++;
		System.out.println("Counter: "+counter);
		if(counter >=50){
			view.implodeI();
		}
	   	
		controller.nextStep(view);
		
		
	}
	
	public Squirrel getMaster(){
		return master;
	}
	
	
	public boolean checkSlave(MasterSquirrel object) {
        if(getMaster()==this) return true; 
		return false;
	}
	
	public miniBotController getController(){
		return (miniBotController) controller;
	}
	
	public int getMoveCount(){
		return moveCount;
	}
	
	
	public void setMoveCount(int x){
		this.moveCount += x;
	}

	
/*InnerClass ControllerContextImplMini*/
	
	public class ControllerContextImplMini implements ControllerContextWImplode {
		public Logger logger = Logger.getLogger(ControllerContextImplMini.class.getName());
		EntityContext context;
		private final XY viewXY = new XY (10,10);
//		private Entity [][] view; 
		
		
		public ControllerContextImplMini(EntityContext context){
			this.context = context;
		}
		

		
		@Override
		public XY getViewLowerLeft() {
			XY pos = xy;
			int x = pos.getX()-((viewXY.getX()-1)/2);
			if(x < 0){x = 0;}
			int y = pos.getY()+((viewXY.getY()-1)/2);
			if(y > context.getSize().getY()){y = context.getSize().getY();}
			XY xy = new XY(x,y);
			return xy;
			
//			XY lL = new XY(xy.getX()-viewXY.getX(),xy.getY()+viewXY.getY());
//			return lL;
//			
			
		}

		@Override
		public XY getViewUpperRight() {
			XY pos = xy;
			int x = pos.getX()+((viewXY.getX()-1)/2);
			if(x > context.getSize().getX()){
				x = context.getSize().getX();
			}
			int y = pos.getY()-((viewXY.getY()-1)/2);
			if(y <0){y=0;}
			XY xy = new XY(x,y);
			return xy;
			
//			XY uR = new XY(xy.getX()+viewXY.getX(),xy.getY()-viewXY.getY());
//			return uR;
		}


		@Override
		public Entity getEntityAt(int x, int y) {
			if(x > context.getSize().getX() | y > context.getSize().getY() | x < 0 | y < 0){
				return null;
			}
			Entity e = context.getEntity(x, y); 
			return e;
		}

		@Override
		public EntityType getEntityTypeAt(int x, int y) {
			if(x < 0 || y < 0 || x > context.getSize().getX()|| y > context.getSize().getY()){
				return EntityType.Null;
			}
			return context.getEntityType(x, y);
		}

		@Override
		public void move(XY direction) {
			context.tryMove(MiniSquirrelBot.this, direction);
		}

		@Override
		public void spawnMiniBot(XY direction, int energy) {
			//do nothing		
		}

		@Override
		public int getEnergy() {
			return MiniSquirrelBot.this.getEnergy();
		}
		
		public XY whereIsMyMaster(){
			XY xy = getMaster().xy;
			return xy;
		}
		
		
		
		
		public void implode(){
			logger.log(Level.INFO,"--------IMPLODE---------\nMasterEnergy before: "+String.valueOf(getMaster().getEnergy()));
			logger.log(Level.INFO, "SquirrelPos: "+xy.toString());
			int impactRadius = 5;
			int impactArea = (int) (impactRadius * impactRadius * Math.PI);
			
			for(int y = getViewUpperRight().getY(); y < getViewLowerLeft().getY(); y++){
				for(int x = getViewLowerLeft().getX(); x < getViewUpperRight().getX(); x++){
					Entity e = getEntityAt(x, y);
					//EntityType type = getEntityTypeAt(x, y);
					if(e!= null && !(e instanceof Wall) &&  e!=(getMaster()) && e!=MiniSquirrelBot.this){
						logger.log(Level.INFO, "Entity: "+e.xy.toString()+" Entity-Energy: "+e.getEnergy());
						int distance = (int)XY.vectorMagnitude(e.xy,xy);
						int energyloss = (-200) * (getEnergy()/impactArea) * (1 - distance /impactRadius);
						if(energyloss <=0){
							energyloss*=-1;
						}
						logger.log(Level.INFO,"energyloss: "+energyloss);
						if(e instanceof BadBeast || e instanceof BadPlant){
							if(e.getEnergy()+energyloss >=0){
								getMaster().updateEnergy(e.getEnergy()*-1);
								context.killAndReplace(e);
							}else{
								e.updateEnergy(energyloss);
							}
						}else if(e instanceof GoodBeast || e instanceof GoodPlant){
							if(e.getEnergy()-energyloss <=0){
								getMaster().updateEnergy(e.getEnergy());
								context.killAndReplace(e);
							}else{
								e.updateEnergy(energyloss*-1);
							}
						}
					}
				}
			}
			logger.log(Level.INFO, "--------EXPLOSION-------"+"\nMasterEnergy after: "+ String.valueOf(getMaster().getEnergy()));
			context.kill(MiniSquirrelBot.this);
		}

		@Override
		public void implodeI() {
			logger.log(Level.INFO,"--------IMPLODE---------\nMasterEnergy before: "+String.valueOf(getMaster().getEnergy()));
			logger.log(Level.INFO, "SquirrelPos: "+xy.toString());
			int impactRadius = 5;
			int impactArea = (int) (impactRadius * impactRadius * Math.PI);
			
			for(int y = getViewUpperRight().getY(); y < getViewLowerLeft().getY(); y++){
				for(int x = getViewLowerLeft().getX(); x < getViewUpperRight().getX(); x++){
					Entity e = getEntityAt(x, y);
					//EntityType type = getEntityTypeAt(x, y);
					if(e!= null && !(e instanceof Wall) &&  e!=(getMaster()) && e!=MiniSquirrelBot.this){
						logger.log(Level.INFO, "Entity: "+e.xy.toString()+" Entity-Energy: "+e.getEnergy());
						int distance = (int)XY.vectorMagnitude(e.xy,xy);
						int energyloss = (-200) * (getEnergy()/impactArea) * (1 - distance /impactRadius);
						if(energyloss <=0){
							energyloss*=-1;
						}
						logger.log(Level.INFO,"energyloss: "+energyloss);
						if(e instanceof BadBeast || e instanceof BadPlant){
							if(e.getEnergy()+energyloss >=0){
								getMaster().updateEnergy(e.getEnergy()*-1);
								context.killAndReplace(e);
							}else{
								e.updateEnergy(energyloss);
							}
						}else if(e instanceof GoodBeast || e instanceof GoodPlant){
							if(e.getEnergy()-energyloss <=0){
								getMaster().updateEnergy(e.getEnergy());
								context.killAndReplace(e);
							}else{
								e.updateEnergy(energyloss*-1);
							}
						}
					}
				}
			}
			logger.log(Level.INFO, "--------EXPLOSION-------"+"\nMasterEnergy after: "+ String.valueOf(getMaster().getEnergy()));
			context.kill(MiniSquirrelBot.this);
			
		}



		@Override
		public XY getPosition() {
			return xy;
		}
		
		
		
		
		
		
		

//		@Override
//		public Entity nearestEnemy() {
//			Entity next = null;
//			for(int x = 0; x < view[0].length; x++){
//				for(int y = 0; y < view.length; y++){
//					Entity enemy = view[y][x];
//					if(enemy instanceof BadBeast || (enemy instanceof Squirrel && enemy !=master)){
//						if(next!= null){
//							if(XY.vectorValue2(xy,enemy.xy)<= XY.vectorValue2(xy, next.xy)){
//								next = enemy;
//							}
//						
//						}else{
//							next = enemy;
//						}
//					}
//				}
//			}
//			return next;
//		}
//
//		@Override
//		public Entity nearestVictim() {
//			Entity next = null;
//			for(int x = 0; x < view[0].length; x++){
//				for(int y = 0; y < view.length; y++){
//					Entity victim = view[y][x];
//					if(victim instanceof GoodBeast || victim instanceof GoodPlant){
//						if(next!= null){
//							if(XY.vectorValue2(xy,victim.xy)<= XY.vectorValue2(xy, next.xy)){
//								next = victim;
//							}
//						
//						}else{
//							next = victim;
//						}
//					}
//				}
//			}
//			return next;
//		}

//		@Override
//		public boolean isEnemyNext(Entity enemy, Entity victim) {
			
			
//			if(XY.vectorValue2(xy, enemy.xy) <= XY.vectorValue2(xy, victim.xy)){
//				return true;
//			}
//			return false;
			
			
//			if(victim == null && enemy == null){
//				return null;
//			}
//			else if(victim == null){
//				return enemy;
//			}else if(enemy == null){
//				return victim;
//			}
//			if(XY.vectorValue2(xy, enemy.xy) <= XY.vectorValue2(xy, victim.xy)){
//				return enemy;
//			}
//			return victim;
//		}

//		@Override
//		public Entity nearestEntity() {
//			Entity next = null;
//			for(int x = 0; x < view[0].length; x++){
//				for(int y = 0; y < view.length; y++){
//					Entity victim = view[y][x];
//					if(victim != null && !(victim instanceof Wall)&&!(victim instanceof BadPlant)&& victim != master && victim != getThis()){//&& !(victim instanceof Wall)
//						if(next!= null){
//							if(XY.vectorValue2(xy,victim.xy)< XY.vectorValue2(xy, next.xy) ){
//								next = victim;
//							}else if(XY.vectorValue2(xy,victim.xy) == XY.vectorValue2(xy, next.xy)){
//								if((victim instanceof GoodBeast || victim instanceof GoodPlant)&&(next instanceof BadBeast)){
//									next = victim;
//								}else if((victim instanceof BadBeast)&&(next instanceof GoodBeast || next instanceof GoodPlant)){
//									break;
//								}else if((victim instanceof GoodBeast || victim instanceof GoodPlant)&&(next instanceof GoodBeast || next instanceof GoodPlant)){
//									break;
//								}
//							}
//						
//						}else{
//							next = victim;
//						}
//					}
//				}
//			}
//			return next;
//		}

//		public void view(){
//		view = new Entity[getViewLowerLeft().getY()-getViewUpperRight().getY()][getViewUpperRight().getX()-getViewLowerLeft().getX()];
//		int xCord = getViewLowerLeft().getX();
//		int yCord = getViewUpperRight().getY();
//		for(int x = 0; x < view[0].length; x++){
//			for(int y = 0; y < view.length; y++){
//				view[y][x] = getEntityAt(xCord,yCord);
//					yCord++;
//			}
//			 yCord = getViewUpperRight().getY();
//				xCord++;
//		}
//	}
	
	
	}	
	

}
