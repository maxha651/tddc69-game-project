package model.world;

import model.MoveableObject;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

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
    ConcurrentHashMap<ZoneCoordinate, Zone> zones;

    public int getNumberOfWorldObjects() {
        return numberOfWorldObjects;
    }

    int numberOfWorldObjects = 0;

    public World(int numberOfLevels, double size){
        this.numberOfLevels = numberOfLevels;
        this.size = size;
        this.zones = new ConcurrentHashMap<ZoneCoordinate, Zone>();
        // creates 9 zones around origo to start with
        createZones();
    }

    private Zone getZone(WorldObject worldObject){
        return getZone(worldObject.getZoneCoordinate());
    }

    private Zone getZone(ZoneCoordinate coordinate){
        Zone zone = zones.get(coordinate);

        if (zone == null){
            zones.put(coordinate, new Zone(zonesRelativeOrigo, size, numberOfLevels));
        }
        return zones.get(coordinate);
    }

    public void addWorldObject(WorldObject worldObject){
        ZoneCoordinate zoneCoord = worldObject.getZoneCoordinate();
        Zone zone = getZone(zoneCoord);
        zone.add(worldObject);
        numberOfWorldObjects++;
    }

    private void moveOneZoneUp(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        start.setY(start.getY() + size);
        stop.setY(stop.getY() + size);
        zoneCoordinate.setY(zoneCoordinate.getY() - 1);
    }

    private void moveOneZoneDown(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        start.setY(start.getY() - size);
        stop.setY(stop.getY() - size);
        zoneCoordinate.setY(zoneCoordinate.getY() + 1);
    }

    private void moveOneZoneLeft(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        start.setX(start.getX() + size);
        stop.setX(stop.getX() + size);
        zoneCoordinate.setX(zoneCoordinate.getX() - 1);
    }

    private void moveOneZoneRight(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        start.setX(start.getX() - size);
        stop.setX(stop.getX() - size);
        zoneCoordinate.setX(zoneCoordinate.getX() + 1);
    }

    private void moveToFirstZoneToCheck(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        while (start.getY() < 0){
            moveOneZoneUp(zoneCoordinate, start, stop);
        }
        while (start.getY() > size){
            moveOneZoneDown(zoneCoordinate, start, stop);
        }
        while (start.getX() < 0){
            moveOneZoneLeft(zoneCoordinate, start, stop);
        }
        while (start.getX() > size){
            moveOneZoneRight(zoneCoordinate, start, stop);
        }
    }

    public WorldObjectContainer getAllObjectsInArea(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        WorldObjectContainer resObjects = new WorldObjectContainer();
        ZoneCoordinate tempZoneCoordinate = new ZoneCoordinate(zoneCoordinate);
        Coordinate tempStart = new Coordinate(start);
        Coordinate tempStop = new Coordinate(stop);

        moveToFirstZoneToCheck(tempZoneCoordinate, tempStart, tempStop);
        double tempStartY = tempStart.getY();
        double tempStopY = tempStop.getY();
        int tempZoneY = tempZoneCoordinate.getY();

        while (tempStop.getX() > 0){
            tempStart.setY(tempStartY);
            tempStop.setY(tempStopY);
            tempZoneCoordinate.setY(tempZoneY);

            while (tempStop.getY() > 0){
                resObjects.addAll(getZone(tempZoneCoordinate).getAllObjectsInArea(tempStart, tempStop));
                moveOneZoneDown(tempZoneCoordinate, tempStart, tempStop);
            }
            moveOneZoneRight(tempZoneCoordinate, tempStart, tempStop);
        }

        return resObjects;
    }

    public void update(){
        for(Zone zone : zones.values()){
            update(zone);
        }
    }

    public void update(ZoneCoordinate start, ZoneCoordinate stop){
        for(int x = start.getX(); x <= stop.getX(); x++){
            for(int y = start.getY(); y <= stop.getY(); y++){
                update(new ZoneCoordinate(x, y));
            }
        }
        clearAdjacentZones(start, stop);
    }

    private void clearAdjacentZones(ZoneCoordinate start, ZoneCoordinate stop){
        for (int x = start.getX() -1; x <= stop.getX() +1; x++){
            int y = start.getY() -1;
            Zone zone = getZone(new ZoneCoordinate(x, y));
            numberOfWorldObjects -= zone.getWorldObjects().size();
            zone.clear();

            y = stop.getY(); // doesn't work with +1
            zone = getZone(new ZoneCoordinate(x, y));
            numberOfWorldObjects -= zone.getWorldObjects().size();
            zone.clear();
        }
        for (int y = start.getY() -1; y <= stop.getY() +1; y++){
            int x = start.getX();
            Zone zone = getZone(new ZoneCoordinate(x, y));
            numberOfWorldObjects -= zone.getWorldObjects().size();
            zone.clear();

            x = stop.getX(); // doesn't work with +1
            zone = getZone(new ZoneCoordinate(stop.getX(), y));
            numberOfWorldObjects -= zone.getWorldObjects().size();
            zone.clear();
        }
    }

    public void update(ZoneCoordinate zoneCoordinate){
        update(getZone(zoneCoordinate));
    }

    private void update(Zone zone){
        for(WorldObject object : zone.getWorldObjects()){
            if(object.isAlive()){
                if(MoveableObject.class.isAssignableFrom(object.getClass())){
                    ((MoveableObject) object).updatePosition(size);

                    // change zone if needed
                    if(zone != getZone(object)){
                        zone.removeWorldObject(object);
                        numberOfWorldObjects--;
                        addWorldObject(object);
                    }
                }
                else{
                    zone.removeWorldObject(object);
                    numberOfWorldObjects--;
                }
            }
        }
    }

    private void createZones(){
        zones.put(new ZoneCoordinate(-1, -1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(0,-1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(1,-1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(-1,0), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(0,0), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(1, 0), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(-1,1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(0,1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(1,1), new Zone(zonesRelativeOrigo, size, numberOfLevels));

    }
}