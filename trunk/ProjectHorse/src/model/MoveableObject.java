package model;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
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

    protected double rotationSpeed = 0;

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    private boolean isOutOfBounds(double zoneSize){
        return coordinate.getX() < 0 || coordinate.getX() > zoneSize ||
                coordinate.getY() < 0 || coordinate.getY() > zoneSize;
    }

    @Override
    public void update(World world) {
        updatePosition();

        if(isOutOfBounds(world.getZoneSize())){
            world.removeWorldObject(this);
            updateZone(world.getZoneSize());
            world.addWorldObject(this);
        }

        super.update(world);
    }

    public void updatePosition(){
        coordinate.setX(coordinate.getX() + velocityX);
        coordinate.setY(coordinate.getY() + velocityY);
        
        this.rotationAngle = this.rotationAngle + this.rotationSpeed;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
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

    public double getAbsoluteVelocity(){
        return model.utility.math.StandardMath.pyth(velocityX, velocityY);
    }
}
