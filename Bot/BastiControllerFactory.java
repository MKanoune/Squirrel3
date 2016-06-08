package Bot;

import Bot.BotImpl.masterBotController;
import Bot.BotImpl.miniBotController;

public class BastiControllerFactory implements BotControllerFactory {
	
	public BastiControllerFactory(){
		
	}
	
	@Override
	public BotController createMasterBotController() {
		BotController controller = new masterBotController();
		return controller;
	}

	@Override
	public BotController createMiniBotController() {
		BotController controller = new miniBotController();
		return controller;
	}
}
