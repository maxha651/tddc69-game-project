package model.background;

import model.character.Player;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-10-01
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class Effect extends MoveableBackgroundObject {

    @Override
    public void update(World world){
        super.update(world);
    }

}
