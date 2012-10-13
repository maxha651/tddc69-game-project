package model.background;

import model.character.AbstractCharacter;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObjectState;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-10-13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public class AsteroidParticle extends Particle {
    public AsteroidParticle(Asteroid a){
        super(a.getCoordinate(), a.getZoneCoordinate());
        tickToKill = 50 + Randomizer.randomInt(1, 40);
        this.width = Randomizer.randomInt(1, 5);
        this.height = width;
        this.rotationSpeed = (Randomizer.randomInt(0,300) - Randomizer.randomInt(0,300))/3000.0;
        this.velocityX = Randomizer.randomDouble(0, 4)*model.utility.math.StandardMath.reverseSign(a.getVelocityX()) - Randomizer.randomDouble(0, 4)*model.utility.math.StandardMath.reverseSign(a.getVelocityX());
        this.velocityY = Randomizer.randomDouble(0, 4)*model.utility.math.StandardMath.reverseSign(a.getVelocityY()) - Randomizer.randomDouble(0, 4)*model.utility.math.StandardMath.reverseSign(a.getVelocityY());
        this.setRotationAngle(Randomizer.randomDouble(0,10));

        this.coordinate = new Coordinate(a.getCoordinate());

        this.coordinate.setX(coordinate.getX());
        this.coordinate.setY(coordinate.getY());
        this.zoneCoordinate = new ZoneCoordinate(a.getZoneCoordinate());
    }
}
