package Entities;

import Core.Board.EntityContext;
import Help.XY;


public abstract class Entity {
	private int id, energy;
    public XY xy;
    public int timeOut;
    protected char token;

	protected Entity(int id, int energy, XY xy) {
		this.id = id;
		this.energy = energy;
        this.xy = xy;
	}

	public void updateEnergy(int delta) {
		energy += delta;
	}

    public int getID() {
        return id;
    }

    public int getEnergy() {
        return energy;
    }

    public abstract void nextStep(EntityContext context);

    public void updateTimeOut(){
		timeOut--;
	}
    
    public void setTimeOut(int i){
		this.timeOut = i;
	}
    
    public int getTimeOut(){
    	return timeOut;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + " - Energy: " + energy + " - Coordinate: " + xy.getX() + "/" + xy.getY();
    }
    
    public char getToken(){
    	return token;
    }
    
    public abstract Entity createNew(int ID, XY pos);
    
}
