package model.background;

import model.character.Player;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
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

    }

    @Override
    public void updatePosition(double size){
        super.updatePosition(size);
        if(tick < tickToKill){
            this.tick++;
        } else {
            this.setState(WorldObjectState.DEAD);
        }
    }
}
