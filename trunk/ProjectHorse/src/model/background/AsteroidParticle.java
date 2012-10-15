package model.background;

import model.character.AbstractCharacter;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObjectState;

/**
 * Standard class for asteroids particles.
 */
public class AsteroidParticle extends Particle {
	
	//default constants
	private static final int MAX_VEL = 3; //max velocityX and max velocityY of the asteroid particles
	private static final int MAX_SIZE = 8;

	private static final int MIN_LIFE_SPAN = 50;
	private static final int LIFE_SPAN_SPREAD = 40;
	
	/**
	 * Basic constructor for an AsteroidParticle.
	 * Spawns the particle at the asteroids position with a randomized velocity and life span.
	 * @param a
	 */
    public AsteroidParticle(Asteroid a){
        super(new Coordinate(a.getCoordinate()), 
        	  new ZoneCoordinate(a.getZoneCoordinate()), 
        	  getParticleVelocityX(a),
        	  getParticleVelocityY(a),
        	  MAX_SIZE,
        	  getParticleLifeSpan());
    }
    
    //private methods used for basic calculations
    private static double getParticleVelocityX(Asteroid a){
    	return Randomizer.randomDouble(0, MAX_VEL) - Randomizer.randomDouble(0, MAX_VEL);    
    }
    
    private static double getParticleVelocityY(Asteroid a){
    	return Randomizer.randomDouble(0, MAX_VEL) - Randomizer.randomDouble(0, MAX_VEL);
    }
    
    private static short getParticleLifeSpan(){
		return (short) (MIN_LIFE_SPAN + Randomizer.randomInt(0, LIFE_SPAN_SPREAD));
	}
}
