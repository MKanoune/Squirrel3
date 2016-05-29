package Scanner;

public enum CommandType implements CommandTypeInfo{
	HELP("help", "  * list all commands"),
    EXIT("exit", "  * exit program"),
    UP("w", " * move up"),
    DOWN("s", " * move down"),
    LEFT("a", " * move left"),
    RIGHT("d", " * move right"),
    STAY("g", " * stays"),
    MASTER_ENERGY("energy", " * displays the Energy of your Squirrel"),
    SPAWN_MINI("mini", " * spawns Mini Squirrel");//, int.class);
 
    String command, help;
    Class<?>[] params;
 
    CommandType(String command, String help, Class<?>... params) {
        this.command = command;
        this.help = help;
        this.params = params;
    }
 
    @Override
    public String getName() {
        return command;
    }
 
    @Override
    public String getHelpText() {
        return help;
    }
 
    @Override
    public Class<?>[] getParamTypes() {
        return params;
    }
}


