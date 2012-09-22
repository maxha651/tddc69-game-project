package model.character;

import model.interfaces.Boundable;
import model.interfaces.Collideable;
import model.interfaces.Moveable;
import model.spacecraft.Engine;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-23
 * Time: 01:37
 * To change this template use File | Settings | File Templates.
 */
public class MoveableCharacter extends AbstractCharacter implements Boundable, Collideable, Moveable {
    private Spacecraft spacecraft;
    private double rotationAngle = 0;
    Coordinate coordinate = new Coordinate(0,0);



    private void updateAcceleration() {
        Engine e = this.spacecraft.getEngine();


    }

    private void updateVelocity() {
        Engine e = this.spacecraft.getEngine();


    }

    @Override
    public void updatePosition() {
        Engine e = this.spacecraft.getEngine();

        this.updateAcceleration();
        this.updateVelocity();

        this.coordinate.setX(this.coordinate.getX() + e.getVelocityX());
        this.coordinate.setY(this.coordinate.getY() + e.getVelocityY());
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

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
