package model.spacecraft;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */
public class Engine extends SpacecraftPart {

    static final double DEFAULT_VELOCITY_MAX = 0.25;
    static final double DEFAULT_ACCELERATION_FACTOR = 1;
    static final double DEFAULT_ACCELERATION_MAX = 1;

    boolean sideStep = false;
    double accelerationX = 0;
    double accelerationY = 0;
    double acceleration = DEFAULT_ACCELERATION_FACTOR;
    double maxAcceleration = DEFAULT_ACCELERATION_MAX;
    double spaceFriction = 0.005; //percentage of total velocity removed each tick

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

    public void accelerate() {
        if(this.velocityX + acceleration < maxAcceleration){
            this.velocityX += acceleration;
        }
    }

    public void deaccelerate() {

    }
}
