package Help;

public enum MoveCommand {
	Up(new XY(0,-1)),
	Down (new XY(0,1)),
	Left(new XY(-1,0)),
	Right(new XY(1,0)),
	UpLeft(new XY(-1,-1)),
	UpRight(new XY(1,-1)),
	DownLeft(new XY(-1,1)),
	DownRight(new XY(1,1)),
	Stay(new XY(0,0));
	
	public final XY xy;
	
	
	private MoveCommand(XY xy){
		this.xy = xy;
	}
	
	
	
}
