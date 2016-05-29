package Entities;
import Core.Board.EntityContext;
import Help.XY;

public class GoodPlant extends Entity{

	private final static int energy = 100;
	
	public GoodPlant(int id,XY xy) {
		super(id, 100, xy);
		token = 'T';
	}

    @Override
    public void nextStep(EntityContext context) {
        //Do nothing
    }

	@Override
	public Entity createNew(int ID, Help.XY pos) {
		return new GoodPlant(ID,pos);
	}
}
