package model.world;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.spawners.WorldObjectSpawner;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Uses Zones to store WorldObjects. Dynamically allocates new Zones when needed
 * Uses WorldObjectSpawner to spawn new WorldObjects in newly created zones
 *
 * Is Threadsafe
 */
public class World {
    double zoneSize;
    ConcurrentSkipListMap<ZoneCoordinate, Zone> zoneMap;
    Collection<WorldObjectSpawner> spawners;

    public int getNumberOfWorldObjects() {
        int numberOfWorldObjects = 0;

        for(Zone zone : zoneMap.values()){
            numberOfWorldObjects += zone.getNumberOfWorldObjects();
        }
        return numberOfWorldObjects;
    }

    public World(double zoneSize){
        this.zoneSize = zoneSize;
        this.zoneMap = new ConcurrentSkipListMap<ZoneCoordinate, Zone>();
        this.spawners = new LinkedList<WorldObjectSpawner>();
    }

    public void addWorldObjectSpawner(WorldObjectSpawner spawner){
        spawners.add(spawner);
    }

    /**
     * Spawns WorldObjects using WorldObjectSpawner
     * @param zoneCoordinate
     */
    private void spawnWorldObjects(ZoneCoordinate zoneCoordinate){
        for (WorldObjectSpawner spawner : spawners){
            spawner.spawnWorldObjects(this, zoneCoordinate);
        }
    }

    /**
     * Returns Zone if exists, otherwise creates the zone and returns it.
     *
     * @param coordinate
     * @return
     */
    private Zone getZone(ZoneCoordinate coordinate){
        Zone zone = zoneMap.get(coordinate);

        if (zone == null){
            zone = new Zone(zoneSize);
            zoneMap.put(new ZoneCoordinate(coordinate), zone);
            spawnWorldObjects(coordinate);
        }
        return zone;
    }

    /**
     * Adds a WorldObject to the world. Checks whether it really is in the right zone before adding
     *
     */
    public void addWorldObject(WorldObject worldObject){
        // Checks which zone to put WorldObject in
        if (worldObject.isOutOfBounds(zoneSize)){
            worldObject.updateZone(zoneSize);
        }

        ZoneCoordinate zoneCoord = worldObject.getZoneCoordinate();
        getZone(zoneCoord).add(worldObject);
    }

    public void removeWorldObject(WorldObject worldObject){
        ZoneCoordinate zoneCoordinate = worldObject.getZoneCoordinate();
        if (zoneExists(zoneCoordinate)){
            getZone(worldObject.getZoneCoordinate()).removeWorldObject(worldObject);
        }
    }

    public double getZoneSize(){
        return zoneSize;
    }

    private void moveOneZoneUp(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        start.setY(start.getY() + zoneSize);
        stop.setY(stop.getY() + zoneSize);
        zoneCoordinate.setY(zoneCoordinate.getY() - 1);
    }
    private void moveOneZoneDown(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        start.setY(start.getY() - zoneSize);
        stop.setY(stop.getY() - zoneSize);
        zoneCoordinate.setY(zoneCoordinate.getY() + 1);
    }
    private void moveOneZoneLeft(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        start.setX(start.getX() + zoneSize);
        stop.setX(stop.getX() + zoneSize);
        zoneCoordinate.setX(zoneCoordinate.getX() - 1);
    }
    private void moveOneZoneRight(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        start.setX(start.getX() - zoneSize);
        stop.setX(stop.getX() - zoneSize);
        zoneCoordinate.setX(zoneCoordinate.getX() + 1);
    }

    /**
     * Changes zoneCoordinate to the upper left zone of the area of which
     * we want to check, while keeping the start and stop coordinates the same globally
     * by only changing the value to be relative to the new zoneCoordinate
     *
     * @param zoneCoordinate Relative zone
     * @param start The upper left corner of the search area
     * @param stop The lower right corner of the search area
     */
    private void moveToFirstZoneToCheck(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        while (start.getY() < 0){
            moveOneZoneUp(zoneCoordinate, start, stop);
        }
        while (start.getY() > zoneSize){
            moveOneZoneDown(zoneCoordinate, start, stop);
        }
        while (start.getX() < 0){
            moveOneZoneLeft(zoneCoordinate, start, stop);
        }
        while (start.getX() > zoneSize){
            moveOneZoneRight(zoneCoordinate, start, stop);
        }
    }

    /**
     * Returns all objects within a rectangle with its upper left corner at start
     * and its lower right corner at stop. Start and stop is relative to ZoneCoordinate
     *
     * @param zoneCoordinate Checks relative to this coordinate
     * @param start The upper left corner of the search area
     * @param stop The lower right corner of the search area
     * @return Returns all objects in the rectangular area from start to stop
     */
    public WorldObjectContainer getAllObjectsInArea(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        WorldObjectContainer resObjects = new WorldObjectContainer();
        ZoneCoordinate tempZoneCoordinate = new ZoneCoordinate(zoneCoordinate);
        Coordinate tempStart = new Coordinate(start);
        Coordinate tempStop = new Coordinate(stop);

        moveToFirstZoneToCheck(tempZoneCoordinate, tempStart, tempStop);
        double tempStartY = tempStart.getY();
        double tempStopY = tempStop.getY();
        int tempZoneY = tempZoneCoordinate.getY();

        //Iterates through x-coordinates
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

    /**
     * Updates ALL created zones
     */
    public void update(){
        for(Zone zone : zoneMap.values()){
            update(zone);
        }
    }

    /**
     * Updates all zones with zoneCoordinates ranging
     * from start to stop
     *
     * @param start Updates from this zone to stop zone
     * @param stop Updates to this zone from start zone
     */
    public void update(ZoneCoordinate start, ZoneCoordinate stop){
        for(int x = start.getX(); x <= stop.getX(); x++){
            for(int y = start.getY(); y <= stop.getY(); y++){
                update(new ZoneCoordinate(x, y));
            }
        }
        clearAdjacentZones(start, stop);
    }

    /**
     * Clears all objects from zone. Will spawn new objects if called by getZone
     *
     * @param zoneCoordinate
     */
    private void clearZone(ZoneCoordinate zoneCoordinate){
        zoneMap.remove(zoneCoordinate);
    }

    /**
     * Clears zones two steps away from the updated area. Objects moving out of the
     * updated area will get stuck in the area inbetween before getting cleared from the
     * game or gets into the updated zone again
     *
     * @param start The upper left corner of the updated area
     * @param stop The lower left corner of the updated area
     */
    private void clearAdjacentZones(ZoneCoordinate start, ZoneCoordinate stop){
        for (int x = start.getX() -2; x <= stop.getX() +2; x++){
            ZoneCoordinate tempCoord = new ZoneCoordinate(x, start.getY() -2);
            clearZone(tempCoord);
            tempCoord.setY(stop.getY() +1); // doesn't work with +1
            clearZone(tempCoord);
        }
        for (int y = start.getY() -2; y <= stop.getY() +2; y++){
            ZoneCoordinate tempCoord = new ZoneCoordinate(start.getX() -2, y);
            clearZone(tempCoord);
            tempCoord.setX(stop.getX() +2); // doesn't work with +1
            clearZone(tempCoord);
        }
    }

    public void update(ZoneCoordinate zoneCoordinate){
        update(getZone(zoneCoordinate));
    }

    private void update(Zone zone){
        for(WorldObject object : zone.getWorldObjects()){
            object.update(this);
        }
    }

    public int getNumOfZones(){
        return zoneMap.size();
    }

    private boolean zoneExists(ZoneCoordinate zoneCoordinate){
        return zoneMap.get(zoneCoordinate) != null;
    }
}
