package model.background;

import model.character.Player;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObjectState;

import java.awt.*;

/**
 *  All kind of particles extends from this abstract class.
 *  Contains functionality for constructing standard coordinates,
 *  life span, update and killing the particle when its lifespan has "worn out".
 */

public abstract class Particle extends Effect {

	//standard declarations for lifespan
    private short tick = 0;
    protected short lifeSpan;
    
    //default constants
	private static final int DEFAULT_ROTATION_SPEED = 2;
    
	/**
	 * Standard constructor for a standard particle.
	 * @param c
	 * @param z
	 * @param velX
	 * @param velY
	 * @param maxSize
	 * @param lifeSpan
	 */
    public Particle(Coordinate c, ZoneCoordinate z, double velX, double velY, int maxSize, short lifeSpan) {
    	this.coordinate = c;
    	this.zoneCoordinate = z;
    	this.lifeSpan = lifeSpan;
		this.height = this.width = Randomizer.randomInt(1, maxSize);
		this.setRotationAngle(0);
		this.rotationSpeed = (DEFAULT_ROTATION_SPEED);
		this.velocityX = velX;
		this.velocityY = velY;
    }

    @Override
    public void update(World world){
        super.update(world);
        if(tick < lifeSpan){
            this.tick++;
        } else {
            this.setState(WorldObjectState.DEAD);
        }
    }
}
