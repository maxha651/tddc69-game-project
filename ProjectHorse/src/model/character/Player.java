package model.character;


import model.interfaces.Collideable;
import model.interfaces.Moveable;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;

import java.awt.*;

public class Player extends AbstractCharacter implements Moveable, Collideable {





    private Spacecraft spacecraft;

    Coordinate position = new Coordinate(0,0);

    public Player(){
        this.setSpacecraft(new Spacecraft());
    }

    @Override
    public boolean collidesWith(Rectangle r) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public void setSpacecraft(Spacecraft spacecraft) {
        this.spacecraft = spacecraft;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
