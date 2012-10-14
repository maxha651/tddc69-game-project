package model.background;

import model.character.Player;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObjectState;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-10-01
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */

public class Particle extends Effect {
    Color c = Color.CYAN;
    int tick = 0;
    int tickToKill = 50;
    public Particle(Coordinate c, ZoneCoordinate z) {
    	this.coordinate = c;
    	this.zoneCoordinate = z;
    }

    @Override
    public void update(World world){
        super.update(world);
        if(tick < tickToKill){
            this.tick++;
        } else {
            this.setState(WorldObjectState.DEAD);
        }
    }
}
