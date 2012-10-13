package model.background;

import model.character.AbstractCharacter;
import model.character.Player;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObjectState;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-10-01
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */
public class EngineParticle extends Particle {
    int tick = 0;
    public EngineParticle(AbstractCharacter p){

        super(p.getCoordinate(), p.getZoneCoordinate());
        this.tickToKill = 25;
        this.width = Randomizer.randomInt(1, 3);
        this.height = width;
        this.setRotationAngle(Randomizer.randomInt(0, 360));
        this.rotationSpeed = (Randomizer.randomInt(0,300) - Randomizer.randomInt(0,300))/3000.0;
        this.velocityX = Randomizer.randomDouble(0, 3)*model.utility.math.StandardMath.reverseSign(p.getVelocityX()) - Randomizer.randomDouble(0, 2)*model.utility.math.StandardMath.reverseSign(p.getVelocityX());
        this.velocityY = Randomizer.randomDouble(0, 3)*model.utility.math.StandardMath.reverseSign(p.getVelocityY()) - Randomizer.randomDouble(0, 2)*model.utility.math.StandardMath.reverseSign(p.getVelocityY());
        this.setRotationAngle(Randomizer.randomDouble(0,10));

        this.coordinate = new Coordinate(p.getCoordinate());

        this.coordinate.setX(coordinate.getX() - Math.cos(p.getRotationAngle()) * p.getWidth()/2);
        this.coordinate.setY(coordinate.getY() - Math.sin(p.getRotationAngle()) * p.getHeight()/2);
        this.zoneCoordinate = new ZoneCoordinate(p.getZoneCoordinate());
    }
}
