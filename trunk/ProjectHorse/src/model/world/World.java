package model.world;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

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
        this.zones = new HashMap<ZoneCoordinate, Zone>();
        // creates 9 zones around origo to start with
        createZones();
    }

    public Zone getZone(Coordinate coordinate){
        return getZone(toZoneCoordinate(coordinate));
    }

    public void addWorldObject(WorldObject worldObject){
        getZone(worldObject.getCoordinate()).add(worldObject);
    }

    public WorldObjectContainer getAllObjectsInArea(Coordinate start, Coordinate stop){
        Coordinate upperRight, upperLeft, lowerRight, lowerLeft;
        upperLeft = start;
        lowerRight = stop;
        upperRight = new Coordinate(stop.getX(), start.getY());
        lowerLeft = new Coordinate(start.getX(), stop.getY());

        Zone upperLeftZone = getZone(upperLeft);
        Zone lowerRightZone = getZone(lowerRight);

        if (upperLeftZone == lowerRightZone){
            return upperLeftZone.getAllObjectsInArea(start, stop);
        }
        else{ // Has to check different zones
            WorldObjectContainer resObjects = new WorldObjectContainer();

            Zone upperRightZone = getZone(upperRight);
            Zone lowerLeftZone = getZone(lowerLeft);

            resObjects.addAll(upperLeftZone.getAllObjectsInArea(start, stop));
            resObjects.addAll(lowerRightZone.getAllObjectsInArea(start, stop));

            if(upperLeftZone != upperRightZone && upperLeftZone != lowerLeftZone){
                resObjects.addAll(lowerLeftZone.getAllObjectsInArea(start, stop));
                resObjects.addAll(upperRightZone.getAllObjectsInArea(start, stop));
            }
            return resObjects;
        }
    }

    public void update(){ // Temporary, should only update used zones
        WorldObjectContainer worldObjects = new WorldObjectContainer();

        for(Zone zone : zones.values()){
            worldObjects.addAll(zone.getObjectsToReinsert());
        }

        for(WorldObject object : worldObjects){
            addWorldObject(object);
        }
    }

    private void createZones(){
        zones.put(new ZoneCoordinate(-1, -1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
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

    private Zone getZone(ZoneCoordinate coordinate){
        Zone zone = zones.get(coordinate);

        if (zone == null){
            zones.put(coordinate, new Zone(zonesRelativeOrigo, size, numberOfLevels));
        }
        return zone;
    }
}
