package Bot;

import Bot.BotImpl.masterBotController;
import Bot.BotImpl.miniBotController;
import Entities.MasterSquirrelBot;
import Entities.MiniSquirrelBot;

public class ControllerFactory implements BotControllerFactory{

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
