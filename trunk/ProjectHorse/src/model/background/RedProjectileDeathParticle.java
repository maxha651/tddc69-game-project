package model.background;

import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

public class RedProjectileDeathParticle extends Particle {
	public RedProjectileDeathParticle(Projectile p){
		 super(new Coordinate(p.getCoordinate()), new ZoneCoordinate(p.getZoneCoordinate()));
	        tickToKill = 20 + Randomizer.randomInt(1, 20);
	        this.width = Randomizer.randomInt(1, 3);
	        this.height = width;
	        this.rotationSpeed = (Randomizer.randomInt(0,300) - Randomizer.randomInt(0,300))/3000.0;
	        this.velocityX = Randomizer.randomDouble(0, 2)*model.utility.math.StandardMath.reverseSign(p.getVelocityX()) - Randomizer.randomDouble(0, 2)*model.utility.math.StandardMath.reverseSign(p.getVelocityX());
	        this.velocityY = Randomizer.randomDouble(0, 2)*model.utility.math.StandardMath.reverseSign(p.getVelocityY()) - Randomizer.randomDouble(0, 2)*model.utility.math.StandardMath.reverseSign(p.getVelocityY());
	        this.setRotationAngle(Randomizer.randomDouble(0,10));
	}

}
