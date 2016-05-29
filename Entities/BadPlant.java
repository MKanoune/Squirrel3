package Entities;
import Core.Board.EntityContext;
import Help.XY;

public class BadPlant extends Entity {

    private final static int energy = -100;

	public BadPlant(int id, XY xy) {
		super(id, energy, xy);
		token = 'P';
	}

    @Override
    public void nextStep(EntityContext context) {
        //Do nothing
    }

	@Override
	public Entity createNew(int ID, Help.XY pos) {
		return new BadPlant(ID,pos);
	}
}
