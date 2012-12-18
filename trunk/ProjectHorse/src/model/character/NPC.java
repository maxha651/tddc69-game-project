package model.character;


import model.GameModel;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;

/**
 * This class is not being used. NPC is NOT finished.
 */
public class NPC extends Player {

    Behaviour behaviour = new StationaryAggressive();

    /**
     * Standard constructor that initializes 1 NPC.
     */
    public NPC(GameModel gameModel, Coordinate c, ZoneCoordinate zc) {
        super(gameModel, c, zc);
    }

    @Override
    public void update(World world) {
        behaviour.update(world, this);
        super.update(world);
    }
}
