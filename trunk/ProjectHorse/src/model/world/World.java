package model.world;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-26
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class World {
    Coordinate zonesRelativeOrigo = new Coordinate(0.0, 0.0);

    int numberOfLevels;
    double size;
    HashMap<ZoneCoordinate, Zone> zones;

    public World(int numberOfLevels, double size){
        this.numberOfLevels = numberOfLevels;
        this.size = size;

        // creates 9 zones around origo to start with
        createZones();
    }

    public void addWorldObject(WorldObject worldObject){
        getZone(worldObject.getCoordinate()).add(worldObject);
    }

    private void createZones(){
        zones.put(new ZoneCoordinate(-1,-1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(0,-1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(1,-1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(-1,0), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(0,0), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(1,0), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(-1,1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(0,1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(1,1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
    }

    private ZoneCoordinate toZoneCoordinate(Coordinate coordinate){
        int tempX;
        int tempY;

        tempX = (int) Math.floor(coordinate.getX() / size);
        tempY = (int) Math.floor(coordinate.getY() / size);

        return new ZoneCoordinate(tempX, tempY);
    }

    public Zone getZone(Coordinate coordinate){
        return getZone(toZoneCoordinate(coordinate));
    }

    private Zone getZone(ZoneCoordinate coordinate){
        Zone zone = zones.get(coordinate);

        if (zone == null){
            zones.put(coordinate, new Zone(zonesRelativeOrigo, size, numberOfLevels));
        }
        return zone;
    }
}
