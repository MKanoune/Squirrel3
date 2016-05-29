package Core.Game;

import Scanner.ScanException;

public abstract class Game {
	protected State state;
	final int FPS = 110;
	
	
	public Game(State state){
		this.state = state;
	}
	
    public void run() throws ScanException, InterruptedException{
        while(true) {
        	Thread.sleep(FPS);
        	render();
            processInput();
            update();
        }
    }
    
    protected abstract void render();
	
	protected abstract void processInput() throws ScanException;
	
	protected void update(){
		state.update();
	}
}
