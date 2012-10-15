package model.background;

import model.character.AbstractCharacter;
import model.character.Player;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObjectState;

/**
 * Spawns itself when a player dies.
 */
public class SpacecraftDeathParticle extends Particle {
	
	//default constats
	private static final int MAX_SIZE = 3;
	private static final int MAX_VEL = 5;

	private static final int MIN_LIFE_SPAN = 25;
	private static final int LIFE_SPAN_SPREAD = 20;
	
	public SpacecraftDeathParticle(Player p){
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
