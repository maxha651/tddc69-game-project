package model.world.spawners;

import model.utility.shape.ZoneCoordinate;
import model.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public interface WorldObjectSpawner {
    public void spawnWorldObjects(World world, ZoneCoordinate zoneCoordinate);
}
