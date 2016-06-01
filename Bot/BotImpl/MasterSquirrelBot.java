package Bot.BotImpl;

import Bot.BotController;
import Bot.BotControllerFactory;
import Bot.ControllerContext;
import Bot.ControllerFactory;
import Core.Board.Board;
import Core.Board.EntityContext;
import Entities.Entity;
import Entities.MasterSquirrel;
import Help.EntityType;
import Help.XY;


public class MasterSquirrelBot extends MasterSquirrel {
	BotController controller;
	//BotControllerFactory factory;
	private int moveCount;
	
	
	public MasterSquirrelBot(int id, XY xy) {
		super(id,1000, xy);
		ControllerFactory factory = new ControllerFactory();
		BotController controller = factory.createMasterBotController();
		this.controller = controller;
		moveCount = 3;
	}
	
	
	
	public MasterSquirrelBot(int id, XY xy,int energy) {
		super(id,energy, xy);
		ControllerFactory factory = new ControllerFactory();
		BotController controller = factory.createMasterBotController();
		this.controller = controller;
		moveCount = 3;
	}
	

	@Override
	public void nextStep(EntityContext context) {
		ControllerContextImplMaster view = this.new ControllerContextImplMaster(context);
		controller.nextStep(view);
	}
	
	public int getMoveCount(){
		return moveCount;
	}
	
	
	public void setMoveCount(int x){
		this.moveCount += x;
	}

	
/*InnerClass ControllerContextImpl*/	
	
	public class ControllerContextImplMaster implements ControllerContext{
		EntityContext context;
		XY viewXY = new XY (21,21);
//		private Entity[][] view;
		
		
		
		public ControllerContextImplMaster(EntityContext context){
			this.context = context;
		
		}
		
//		public void view(){
//			view = new Entity[getViewLowerLeft().getY()-getViewUpperRight().getY()][getViewUpperRight().getX()-getViewLowerLeft().getX()];
//			int xCord = getViewLowerLeft().getX();
//			int yCord = getViewUpperRight().getY();
//			for(int x = 0; x < view[0].length; x++){
//				for(int y = 0; y < view.length; y++){
//					view[y][x] = getEntityAt(xCord,yCord);
//						yCord++;
//				}
//				 yCord = getViewUpperRight().getY();
//					xCord++;
//			}
//		}
		
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
			return context.getEntityType(x, y);
		}

		@Override
		public void move(XY direction) {
			context.tryMove(MasterSquirrelBot.this, direction);
		}

		@Override
		public void spawnMiniBot(XY direction, int energy) {
			context.spawnChildBot(MasterSquirrelBot.this, direction, energy);
		}

		@Override
		public int getEnergy() {
			return MasterSquirrelBot.this.getEnergy();
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
		}

		@Override
		public XY getPosition() {
			return MasterSquirrelBot.this.xy;
		}
		

		
		
//		@Override
//		public Entity nearestEntity() {
//			Entity next = null;
//			for(int x = 0; x < view[0].length; x++){
//				for(int y = 0; y < view.length; y++){
//					Entity victim = view[y][x];
//					if(victim != null && !(victim instanceof Wall)&&!(victim instanceof BadPlant)&& !checkSlave(victim) && victim != getThis()){//&& !(victim instanceof Wall)
//						if(next!= null){
//							if(XY.vectorValue2(xy,victim.xy)< XY.vectorValue2(xy, next.xy) ){
//								//System.out.println(XY.vectorValue2(xy,victim.xy) + " " + XY.vectorValue2(xy, next.xy));
//								next = victim;
//							}else if(XY.vectorValue2(xy,victim.xy) == XY.vectorValue2(xy, next.xy)){
//								if((victim instanceof GoodBeast || victim instanceof GoodPlant)&&(next instanceof BadBeast)){
//									
//								}else if((victim instanceof BadBeast)&&(next instanceof GoodBeast || next instanceof GoodPlant)){
//									next = victim;
////								}else if((victim instanceof GoodBeast || victim instanceof GoodPlant)&&(next instanceof GoodBeast || next instanceof GoodPlant)){
////									break;
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

		
		

		

	}

}
