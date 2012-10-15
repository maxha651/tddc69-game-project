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
 * Date: 2012-10-13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public class SpacecraftDeathParticle extends Particle {
	static final int M_V = 3; //max velocity
	
    public SpacecraftDeathParticle(Player player){
        super(new Coordinate(player.getCoordinate()), new ZoneCoordinate(player.getZoneCoordinate()));
        tickToKill = (short) (50 + Randomizer.randomInt(1, 40));
        this.height = this.width = Randomizer.randomInt(1, 5);
        this.rotationSpeed = (Randomizer.randomInt(0,300) - Randomizer.randomInt(0,300))/3000.0;
        this.velocityX = Randomizer.randomDouble(0, M_V)*model.utility.math.StandardMath.reverseSign(player.getVelocityX()) - Randomizer.randomDouble(0, M_V)*model.utility.math.StandardMath.reverseSign(player.getVelocityX());
        this.velocityY = Randomizer.randomDouble(0, M_V)*model.utility.math.StandardMath.reverseSign(player.getVelocityY()) - Randomizer.randomDouble(0, M_V)*model.utility.math.StandardMath.reverseSign(player.getVelocityY());
        this.setRotationAngle(Randomizer.randomDouble(0,10));
    }
}
