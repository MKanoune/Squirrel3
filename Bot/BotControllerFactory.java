package Bot;



public interface BotControllerFactory {
	
	public BotController createMasterBotController();
	
	public BotController createMiniBotController();

	
}
