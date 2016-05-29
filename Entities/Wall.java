package Entities;
import Core.Board.EntityContext;
import Help.XY;

public class Wall extends Entity{
	private final static int energy = -10;
	
	public Wall(int id, XY xy) {
		super(id, energy, xy);
		token = 'X';
	}

    @Override
    public void nextStep(EntityContext context) {
        //Do nothing
    }

	@Override
	public Entity createNew(int ID, Help.XY pos) {
		// TODO Auto-generated method stub
		return null;
	}
}
