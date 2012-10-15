package model.spacecraft.parts.types.engine;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 13:22
 * To change this template use File | Settings | File Templates.
 */
public class DefaultEngine implements EngineType {

    double acceleration = 0.4;
    double maxVelocity = 9;
    double rotationSpeed = 3;
    boolean sideStep = false;

    @Override
    public double getAcceleration() {
        return acceleration;
    }

    @Override
    public double getMaxVelocity() {
        return maxVelocity;
    }

    @Override
    public double getRotationSpeeed() {
        return rotationSpeed;
    }

    @Override
    public boolean isSideStep() {
        return sideStep;
    }
}
