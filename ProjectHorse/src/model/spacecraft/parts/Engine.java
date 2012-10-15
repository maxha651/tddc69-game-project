package model.spacecraft.parts;

import model.spacecraft.parts.types.engine.DefaultEngine;
import model.spacecraft.parts.types.engine.EngineType;

/**
 * The engine is used to accelerate a spacecraft/Player.
 */
public class Engine extends SpacecraftPart {

    private EngineType engineType;

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
