package model.background;

import model.character.AbstractCharacter;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

public class RedProjectileDeathParticle extends Particle {
	private static final int MAX_SIZE = 3;
	private static final int MAX_VEL = 3;
	private static final int MIN_LIFE_SPAN = 15;
	private static final int LIFE_SPAN_SPREAD = 10;
	
	public RedProjectileDeathParticle(Projectile p){
		super(new Coordinate(p.getCoordinate()), 
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
}
