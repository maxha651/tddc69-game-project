package model;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObject;

/**
 * Created with IntelliJ IDEA.
 * User: Askh
 * Date: 2012-09-27
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
public abstract class MoveableObject extends WorldObject{

    protected double velocityX, velocityY;

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    protected double rotationSpeed = 0;

    public void updateZone(double zoneSize){
        ZoneCoordinate zoneCoordinate = getZoneCoordinate();
        Coordinate c = getCoordinate();

        if (c.getX() > zoneSize){
            zoneCoordinate.setX(zoneCoordinate.getX() +1);
            c.setX(c.getX() % zoneSize);
        }
        if (c.getX() < 0.0){
            zoneCoordinate.setX(zoneCoordinate.getX() -1);
            c.setX(zoneSize + c.getX());
        }
        if (c.getY() > zoneSize){
            zoneCoordinate.setY(zoneCoordinate.getY() +1);
            c.setY(c.getY() % zoneSize);
        }
        if (c.getY() < 0.0){
            zoneCoordinate.setY(zoneCoordinate.getY() -1);
            c.setY(zoneSize + c.getY());
        }
    }

    public void updatePosition(double zoneSize){
        Coordinate c = new Coordinate();

        c.setX(coordinate.getX() + velocityX);
        c.setY(coordinate.getY() + velocityY);

        coordinate.setX(c.getX());
        coordinate.setY(c.getY());
        
        updateZone(zoneSize);
        
        this.rotationAngle = this.rotationAngle + this.rotationSpeed;
    };

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double d) {
        this.velocityX = d;
    }

    public void setVelocityY(double d) {
        this.velocityY = d;
    }

    public void setVelocity(double x, double y){
        this.velocityX = x;
        this.velocityY = y;
    }

    public double getVelocityY() {
        return velocityY;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void rotateLeft(double rad){
        rotationAngle -= rad;
        while (rotationAngle < 0){
            rotationAngle = rotationAngle + 2*Math.PI;
        }
    }

    public void rotateRight(double rad){
        rotationAngle += rad;
        while (rotationAngle > Math.PI*2){
            rotationAngle = rotationAngle - 2*Math.PI;
        }
    }

    public void rotateLeftOneDegree(){
        rotationAngle -= (2*Math.PI)/360;
        if(rotationAngle < 0){
            rotationAngle = rotationAngle + 2*Math.PI;
        }
    }

    public void rotateRightOneDegree(){
        rotationAngle += (2*Math.PI)/360;
        if(rotationAngle > Math.PI*2){
            rotationAngle = rotationAngle - 2*Math.PI;
        }
    }

    public double getAbsoluteVelocity(){
        return model.utility.math.StandardMath.pyth(velocityX, velocityY);
    }
}
