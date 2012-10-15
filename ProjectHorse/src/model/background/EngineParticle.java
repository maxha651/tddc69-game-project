package model.background;

import model.character.AbstractCharacter;
import model.character.Player;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObjectState;

/**
 * Engine particle constructs itself behind an engine in the world.
 */
public class EngineParticle extends Particle {	
	static final int MAX_SIZE = 3;
	static final int MAX_VEL = 3;

	private static final int MIN_LIFE_SPAN = 15;
	private static final int LIFE_SPAN_SPREAD = 8;

	/**
	 * Basic constructor for an EngineParticle.
	 * Takes an AbstractCharacter p and uses its coordinates to spawn particles behind the spacecraft.
	 */
    public EngineParticle(AbstractCharacter p){
    	 super(getParticleCoordinate(p), 
           	  new ZoneCoordinate(p.getZoneCoordinate()), 
           	  getParticleVelocityX(),
           	  getParticleVelocityY(),
           	  MAX_SIZE,
           	  getParticleLifeSpan());       	
    }
    
    //private methods used for basic calculations
    private static double getParticleVelocityX(){
    	return Randomizer.randomDouble(0, MAX_VEL) - Randomizer.randomDouble(0, MAX_VEL);    
    }
    
    private static double getParticleVelocityY(){
    	return Randomizer.randomDouble(0, MAX_VEL) - Randomizer.randomDouble(0, MAX_VEL);
    }
    
    private static short getParticleLifeSpan(){
		return (short) (MIN_LIFE_SPAN + Randomizer.randomInt(0, LIFE_SPAN_SPREAD));
	}
    
    private static Coordinate getParticleCoordinate(AbstractCharacter p){
    	return new Coordinate(p.getCoordinate().getX() - Math.cos(p.getRotationAngle()) * p.getWidth()/2,
    			p.getCoordinate().getY() - Math.sin(p.getRotationAngle()) * p.getHeight()/2);       
    }
}
