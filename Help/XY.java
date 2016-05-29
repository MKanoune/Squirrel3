package Help;








 public final class XY {
    private int xCord, yCord;
    static java.util.Random r = new java.util.Random();
    
    
    public XY(int x,int y) {
        xCord = x;
        yCord = y;
    }

//    public XY getPosition(Entity entity){
//    	return entity.xy;
//    }
    
    public XY getXY(){
    	return this;
    }
    
    public void setPosition(XY xy){
    	this.xCord = xy.xCord;
    	this.yCord = xy.yCord;
    }
       
    public static XY rndmXY(int maxX, int maxY){
    	XY pos = new XY(r.nextInt(maxX),r.nextInt(maxY));
    	return pos;
    }
    
    @Override
    public String toString(){
    	String e = "("+getX()+","+getY()+")";
    	return e;
    }
 
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 					
 * 					- MoveCommand - 
 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/    

    
    public XY commandVector(XY command){
    	if(command == null){
    		return this;
    	}
    	int x = this.xCord + command.xCord;
    	int y = this.yCord + command.yCord;
    	return new XY(x,y);
    }
    
    
    public static XY RandomMoveCommand(){
		int random = r.nextInt(7);
		XY move = null;
		
		switch(random){
		case 0: move = MoveCommand.Up.xy; break;
		case 1: move = MoveCommand.Down.xy; break;
		case 2: move = MoveCommand.Left.xy; break;
		case 3: move = MoveCommand.Right.xy; break;
		case 4: move = MoveCommand.UpLeft.xy; break;
		case 5: move = MoveCommand.UpRight.xy; break;
		case 6: move = MoveCommand.DownLeft.xy; break;
		case 7: move = MoveCommand.DownRight.xy; break;
		}
		return move;
	}

/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 				- MoveCommand - END -
 * 						- X -
 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public int getX() {
        return xCord;
    }


    public void setX(int x) {
        xCord = x;
    }

/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*				- X - END
*				- Y -
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    
    public int getY() {
    	return yCord;
    }

    public void setY(int y) {
        yCord = y;
    }
    
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*				- Y - END
*				-Vector-
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/    
    //ermittelt den betrag aus einem vektor
    public static double vectorMag(XY a){
    	int x = a.xCord*a.xCord;
    	int y = a.yCord^a.yCord;
    	double ab = Math.sqrt(x+y);    	
    	return ab;
    }    
    
    //ermittelt den betrag aus 2 Koordinaten
    public static double vectorMagnitude(XY base,XY top){
    	XY vec = vector(base,top);
    	double value = vectorMag(vec);
    	return value;
    }
    
    public XY attack(XY base, XY top){
    	XY c = vector(base, top);
    	if(c.xCord >1){
    		c.xCord = 1;
    	}else if(c.xCord < -1){
    		c.xCord = -1;
    	}
    	
    	if(c.yCord >1){
    		c.yCord = 1;
    	}else if(c.yCord < -1){
    		c.yCord = -1;
    	}
    	return c;
    }
    
    
    public XY escape(XY base, XY top){
    	XY c = attack(base,top);
    	c.xCord = c.xCord*-1;
    	c.yCord = c.yCord*-1;
    	return c;
    }
    
    @Override
    public boolean equals (Object o)
    {
        if (o == null)
            return false;

        if (o == this)
            return true;

        if(! o.getClass().equals(getClass()))
            return false;

        XY that = (XY) o;

        return         this.getX() == that.getX()
                && this.getY() == that.getY();



    }
    
    //aus 2 Koordinaten wird 1 vektor erstellt
    public static XY vector (XY base,XY top){
    	XY c = new XY (top.xCord-base.xCord,top.yCord-base.yCord);
    	return c;
    }

    
    
    
    //Vektor addition 
    public static XY addVector(XY a, XY b){
    	int x = a.xCord+b.xCord;
    	int y = a.yCord+b.yCord;
    	XY c = new XY(x,y);
    	return c;
    }
  
    
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*				
*				-Vector-END-
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/    
        
   
    
    
    
    

    
    

//  public XY commandVector(MoveCommand move){
//		if(move == null){
//			return this;
//		}
//		int x = xCord + move.x;
//		int y = yCord + move.y;
//		return new XY(x, y);
//	}
// 



//  public void updateXY(XY xy){
//  	xCord = xy.xCord;
//  	yCord = xy.yCord;
//  }

    
    
//    public XY moveRandom() {
//      XY pos = new XY((r.nextInt(3) - 1),(r.nextInt(3) - 1));
//  	//updateX(r.nextInt(3) - 1);
//      //updateY(r.nextInt(3) - 1);
//      return pos;
//  }
}
