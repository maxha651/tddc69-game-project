package model.spacecraft.parts;

import model.spacecraft.parts.types.engine.DefaultEngine;
import model.spacecraft.parts.types.engine.EngineType;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */
public class Engine extends SpacecraftPart {

    EngineType engineType;

    public Engine(){
        engineType = new DefaultEngine();
    }

    public Engine(EngineType engineType){
        this.engineType = engineType;
    }

    public double getRotationSpeed() {
        return engineType.getRotationSpeeed();
    }

    public double getVelocityMax() {
        return engineType.getMaxVelocity();
    }

    public double getAcceleration() {
        return engineType.getAcceleration();
    }

    public boolean isSideStep() {
        return engineType.isSideStep();
    }
}
