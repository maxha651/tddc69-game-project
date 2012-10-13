package model.spacecraft.parts.types.engine;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 13:16
 * To change this template use File | Settings | File Templates.
 */
public interface EngineType {
    public double getAcceleration();
    public double getMaxVelocity();
    public double getRotationSpeeed();
    public boolean isSideStep();
}
