package model;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObject;

/**
 * A WorldObject which can alter it's position and rotate
 */
public abstract class MoveableObject extends WorldObject{

    protected double velocityX, velocityY;

    protected double rotationSpeed = 0;

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    @Override
    /**
     * Besides calling the update of WorldObject, updates the object's
     * position, rotation and introduces an isOutOfBoundsCheck
     */
    public void update(World world) {
        updatePosition();
        updateRotation();

        if(isOutOfBounds(world.getZoneSize())){
            world.removeWorldObject(this);
            updateZone(world.getZoneSize());
            world.addWorldObject(this);
        }

        super.update(world);
    }

    /**
     * Updates position according to current velocity
     */
    public void updatePosition(){
        coordinate.setX(coordinate.getX() + velocityX);
        coordinate.setY(coordinate.getY() + velocityY);
    }

    /**
     * Updates rotation according to current rotation speed
     */
    public void updateRotation(){
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
