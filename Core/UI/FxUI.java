package Core.UI;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import Core.Board.BoardView;
import Entities.*;
import Help.EntityType;
import Help.XY;
import Scanner.Command;
import Scanner.CommandScanner;
import Scanner.CommandTypeInfo;
import Scanner.CommandType;
import Scanner.ScanException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.Scene;

public class FxUI extends Scene implements UI {
	private static final int CELL_SIZE =26;
	private static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
	static Command command;
	static Command lastCommand;
	private static CommandTypeInfo[] commandTypeInfos = CommandType.values();
	private Canvas boardCanvas;
	private Label msgLabel;
	
	
	
	public FxUI(Parent parent, Canvas boardCanvas, Label msgLabel){
		super(parent);
		this.boardCanvas = boardCanvas;
		this.msgLabel = msgLabel;
	}
	

	public static FxUI createInstance(XY boardSize){
			Canvas boardCanvas = new Canvas(boardSize.getX()*CELL_SIZE,boardSize.getY()*CELL_SIZE);
			Label statusLabel = new Label();
			Label energyLabel = new Label();
			Button button = new Button("MINI");
	        VBox top = new VBox();
	        top.getChildren().add(button);
	        top.getChildren().add(boardCanvas);
	        top.getChildren().add(statusLabel);
	        top.getChildren().add(energyLabel);
	        statusLabel.setText("hello world!");
	       // energyLabel.setText("Energy: ");
	        
	        
	        final FxUI fxUI = new FxUI(top, boardCanvas, statusLabel); 
	        
	        button.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					lastCommand = new Command(commandTypeInfos[8],null);
				}
	        	
	        });
	        
	        
	        
	        fxUI.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent event) {
					String c = event.getText();
					System.out.println(c);
					statusLabel.setText("last input: " + c);
					
					for(int i = 0; i < commandTypeInfos.length; i++) {
						if(c.equals(commandTypeInfos[i].getName())) {
							lastCommand = new Command(commandTypeInfos[i], null);
							
						}
					}
					
				}   

	         }
	        );
	        return fxUI;
	}


	@Override
	public void getCommand() throws ScanException {
		//do nothing
		

	}

	@Override
	public void render(BoardView view) {
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				repaintBoardCanvas(view);
			}
		});
	}
	public void repaintBoardCanvas(BoardView view){
		GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());
        XY viewSize = view.getSize();
        int xCord = 0;
        int yCord = 0;
        for(int x = 0; x < viewSize.getX(); x++){
        	for(int y = 0; y < viewSize.getY(); y++){
        		EntityType type = view.getEntityType(x, y);
        		gc.setFill(Color.BURLYWOOD);
    			gc.fillRect(xCord, yCord, CELL_SIZE, CELL_SIZE);
    			if(type.paintType.equals("oval")){
    				gc.setFill(type.color);
    				gc.fillOval(xCord, yCord, CELL_SIZE, CELL_SIZE);
    				if(type == EntityType.GuidedMasterSquirrel){
    					gc.setFill(Color.WHITE);
    					gc.fillText(String.valueOf(view.getEnergy(x, y)) ,x*CELL_SIZE, (y*CELL_SIZE)+CELL_SIZE/1.5, CELL_SIZE);
    				}
    			}else if(type.paintType.equals("rect")){
    				gc.setFill(type.color);
    				gc.fillRect(xCord, yCord, CELL_SIZE, CELL_SIZE);
    			}
//        		
        		yCord+=CELL_SIZE;
        	}
        	yCord=0;
        	xCord+=CELL_SIZE;
        }
	}
	
	 public void message(final String msg){
	        Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
	                msgLabel.setText(msg);            
	            }      
	        });         
	 }


	@Override
	public Command getLastCommand() {
		return lastCommand;
	}
	

}
