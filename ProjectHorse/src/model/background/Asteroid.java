package model.background;

import model.interfaces.Collideable;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-29
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class Asteroid extends AbstractProjectile implements Collideable{

    boolean hasCollided = false;

    public Asteroid(Coordinate c, ZoneCoordinate z){
        this.boundingHeight = Randomizer.randomInt(20, 80);
        this.boundingWidth = this.boundingHeight + Randomizer.randomInt(0, 15) - Randomizer.randomInt(0, 15);
        this.rotationSpeed = (Randomizer.randomInt(0,300) - Randomizer.randomInt(0,300))/5000.0;
        this.velocityX = Randomizer.randomDouble(0, 4) - Randomizer.randomDouble(0, 4);
        this.velocityY = Randomizer.randomDouble(0, 4) - Randomizer.randomDouble(0, 4);
        this.setRotationAngle(Randomizer.randomDouble(0,10));

        this.coordinate = c;
        this.zoneCoordinate = z;

    }

    @Override
    public void updatePosition(double size){
        super.updatePosition(size);
        this.setRotationAngle(this.getRotationSpeed() + this.getRotationAngle());
        hasCollided = false;
    }

    @Override
    public double getWidth(){
        return boundingWidth;
    }

    @Override
    public double getHeight(){
        return boundingHeight;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) this.coordinate.getX(), (int) this.coordinate.getY(), (int) boundingWidth, (int) boundingHeight);
    }

    @Override
    public void collidesWith(Collideable c, double zoneSize) {
        ZoneCoordinate tempZoneCoord = new ZoneCoordinate(c.getZoneCoordinate());
        Coordinate tempCoord = new Coordinate(c.getCoordinate());

        if(!zoneCoordinate.equals(tempZoneCoord)){
            double yZoneDiff = (zoneCoordinate.getY() - tempZoneCoord.getY()) * zoneSize;
            double xZoneDiff = (zoneCoordinate.getX() - tempZoneCoord.getX()) * zoneSize;

            tempCoord.setY(tempCoord.getY() + yZoneDiff);
            tempCoord.setX(tempCoord.getX() + xZoneDiff);
        }

        double xDiff = Math.abs(coordinate.getX() - tempCoord.getX());
        double yDiff = Math.abs(coordinate.getY() - tempCoord.getY());

        if( xDiff < (boundingWidth/2 + c.getBoundingWidth()/2) &&
                yDiff < (boundingHeight/2 + c.getBoundingHeight()/2)){
            double tempvelocityX = c.getVelocityX();
            double tempvelocityY = c.getVelocityY();
            c.setToCollide(this);

            this.velocityX = tempvelocityX;
            this.velocityY = tempvelocityY;
            hasCollided = true;
        }
    }

    @Override
    public void setToCollide(Collideable c) {
        hasCollided = true;
        this.velocityX = c.getVelocityX();
        this.velocityY = c.getVelocityY();
    }

    @Override
    public double getBoundingWidth() {
        return boundingWidth;
    }

    @Override
    public double getBoundingHeight() {
        return boundingHeight;
    }

    @Override
    public boolean hasCollided() {
        return hasCollided;
    }
}
