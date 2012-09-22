package model.character;


import model.interfaces.Collideable;
import model.interfaces.Moveable;
import model.spacecraft.Engine;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;

import java.awt.*;

public class Player extends MoveableCharacter{



    public Player(){
        this.setSpacecraft(new Spacecraft());
    }



    @Override
    public boolean collidesWith(Rectangle r) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }



}
