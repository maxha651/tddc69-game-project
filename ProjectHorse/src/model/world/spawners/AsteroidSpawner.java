package model.world.spawners;

import model.background.Asteroid;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */
public class AsteroidSpawner implements WorldObjectSpawner{

    private static final double SPACING_BETWEEN_ASTEROIDS = 200;

    @Override
    public void spawnWorldObjects(World world, ZoneCoordinate zoneCoordinate) {
        double zoneSize = world.getZoneSize();
        double space = SPACING_BETWEEN_ASTEROIDS;

        for(int x=0; x < zoneSize; x+=space){
            for (int y=0; y < zoneSize; y+=space){
                world.addWorldObject(new Asteroid(new Coordinate(x, y), new ZoneCoordinate(zoneCoordinate)));
            }
        }
    }
}
