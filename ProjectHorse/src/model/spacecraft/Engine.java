package model.spacecraft;

import model.utility.math.StandardMath;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */
public class Engine extends SpacecraftPart {

    static final double DEFAULT_VELOCITY_MAX = 0.25;
    static final double DEFAULT_ACCELERATION = 0.5;
    static final double DEFAULT_ACCELERATION_MAX = 1;
    static final double DEFAULT_SPACE_FRICTION = 0.995;
    static final double VELOCITY_FLOOR = 0.20;

    boolean sideStep = false;
    double accelerationX = 0;
    double accelerationY = 0;
    double acceleration = DEFAULT_ACCELERATION;
    double maxAcceleration = DEFAULT_ACCELERATION_MAX;
    double spaceFriction = DEFAULT_SPACE_FRICTION;

    double velocityX = 0;
    double velocityY = 0;
    double maxVelocity = DEFAULT_VELOCITY_MAX; //maximum achieveable speed in percentage of constant in GameModel

    public boolean isSideStep() {
        return sideStep;
    }

    public void setSideStep(boolean sideStep) {
        this.sideStep = sideStep;
    }

    public double getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(double accelerationX) {
        this.accelerationX = accelerationX;
    }

    public double getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(double accelerationY) {
        this.accelerationY = accelerationY;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public double getSpaceFriction() {
        return spaceFriction;
    }

    public void setSpaceFriction(double spaceFriction) {
        this.spaceFriction = spaceFriction;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public void accelerate(double angle) {
        if(StandardMath.pyth(velocityX, velocityY) < DEFAULT_VELOCITY_MAX){
            this.velocityX += StandardMath.xPart(acceleration, angle);
            this.velocityY += StandardMath.yPart(acceleration, angle);

            double newVelocity = StandardMath.pyth(velocityX, velocityY);
            if (newVelocity > DEFAULT_VELOCITY_MAX){
                double maxAndNewVelocityRatio = DEFAULT_VELOCITY_MAX / newVelocity;
                this.velocityX *= maxAndNewVelocityRatio;
                this.velocityY *= maxAndNewVelocityRatio;
            }
        }
    }

    public void deaccelerate(double angle) {
        double velocityLength = StandardMath.pyth(velocityX, velocityY);

        if(velocityLength > 0){
            if(velocityLength > VELOCITY_FLOOR){
                this.velocityX *= spaceFriction;
                this.velocityY *= spaceFriction;
            } else {
                this.velocityY = 0;
                this.velocityY = 0;
            }

        }
    }

    public double getAbsoluteVelocity(){
        return model.utility.math.StandardMath.pyth(velocityX, velocityY);
    }
}
