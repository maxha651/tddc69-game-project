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

    public static final double DEFAULT_VELOCITY_MAX = 7;
    public static final double DEFAULT_ACCELERATION = 0.5;
    public static final double DEFAULT_ROTATION_SPEED = 5;

    boolean sideStep = false;
    public double acceleration = DEFAULT_ACCELERATION;
    public double velocityMax = DEFAULT_VELOCITY_MAX;

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double rotationSpeed = DEFAULT_ROTATION_SPEED;
    public double getVelocityMax() {
        return velocityMax;
    }

    public void setVelocityMax(double velocityMax) {
        this.velocityMax = velocityMax;
    }

    public Engine(){

    }

    public Engine(double acceleration){
        this();
        this.acceleration = acceleration;
    }

    public Engine(double acceleration, boolean b){
        this(acceleration);
        this.sideStep = b;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public boolean isSideStep() {
        return sideStep;
    }

    public void setSideStep(boolean sideStep) {
        this.sideStep = sideStep;
    }

}
