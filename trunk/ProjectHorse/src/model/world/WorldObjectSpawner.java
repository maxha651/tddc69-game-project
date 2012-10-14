package model.world;

import model.utility.shape.ZoneCoordinate;

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
