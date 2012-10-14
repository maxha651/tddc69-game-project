package model.world;

import model.CollideCheck;
import model.GameModel;
import model.MoveableObject;
import model.properties.Collideable;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-26
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class World {
    double zoneSize;
    ConcurrentSkipListMap<ZoneCoordinate, Zone> zones;
    LinkedList<WorldObjectSpawner> spawners;

    public int getNumberOfWorldObjects() {
        return numberOfWorldObjects;
    }

    int numberOfWorldObjects = 0;

    public World(double zoneSize){
        this.zoneSize = zoneSize;
        this.zones = new ConcurrentSkipListMap<ZoneCoordinate, Zone>();
        this.spawners = new LinkedList<WorldObjectSpawner>();
    }

    public void addWorldObjectSpawner(WorldObjectSpawner spawner){
        spawners.add(spawner);
    }

    private void spawnWorldObjects(ZoneCoordinate zoneCoordinate){
        for (WorldObjectSpawner spawner : spawners){
            spawner.spawnWorldObjects(this, zoneCoordinate);
        }
    }

    private Zone getZone(ZoneCoordinate coordinate){
        Zone zone = zones.get(coordinate);

        if (zone == null){
            zones.put(new ZoneCoordinate(coordinate), new Zone(zoneSize));
            spawnWorldObjects(coordinate);
        }
        return zones.get(coordinate);
    }

    public void addWorldObject(WorldObject worldObject){
        ZoneCoordinate zoneCoord = worldObject.getZoneCoordinate();
        Zone zone = getZone(zoneCoord);
        if (!zone.isWithinBoundaries(worldObject)){
            worldObject.updateZone(zoneSize);
        }
        zone.add(worldObject);
        numberOfWorldObjects++;
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
                if (zoneExists(tempZoneCoordinate)){
                resObjects.addAll(getZone(tempZoneCoordinate).getAllObjectsInArea(tempStart, tempStop));
                }
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

    private void clearZone(ZoneCoordinate zoneCoordinate){
        if (zoneExists(zoneCoordinate)){
            numberOfWorldObjects -= zones.remove(zoneCoordinate).getWorldObjects().size();
        }
    }

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

    private boolean isCollideable(WorldObject o){
        return Collideable.class.isAssignableFrom(o.getClass());
    }

    private boolean isMoveable(WorldObject o){
        return MoveableObject.class.isAssignableFrom(o.getClass());
    }
/*
    private void updateCollideable(Collideable object){
        Coordinate objCoord = object.getCoordinate();
        WorldObjectContainer nearbyObjects;
        int checkDistance = GameModel.COLLIDING_CHECK_DISTANCE;
        nearbyObjects = getAllObjectsInArea(object.getZoneCoordinate(),
                new Coordinate(objCoord.getX() - checkDistance, objCoord.getY() - checkDistance),
                new Coordinate(objCoord.getX() + checkDistance, objCoord.getY() + checkDistance));

        for(WorldObject nearbyObject : nearbyObjects){
            if (isCollideable(nearbyObject) && nearbyObject != object){
                if(CollideCheck.isColliding(object, (Collideable) nearbyObject, zoneSize)){
                    object.setToCollide((Collideable) nearbyObject);
                    ((Collideable) nearbyObject).setToCollide(object);
                }
            }
        }
    }*/

    private void updateMoveable(MoveableObject object, Zone zone){
        (object).updatePosition(zoneSize);

        if(!zone.isWithinBoundaries(object)){
            zone.removeWorldObject(object);
            numberOfWorldObjects--;
            object.updateZone(zoneSize);
            addWorldObject(object);
        }
    }

    private void update(Zone zone){
        for(WorldObject object : zone.getWorldObjects()){
            object.update(this);
        }
    }

    public int getNumOfZones(){
        return zones.size();
    }

    private boolean zoneExists(ZoneCoordinate zoneCoordinate){
        return zones.get(zoneCoordinate) != null;
    }

    private void createZones(){
        zones.put(new ZoneCoordinate(-1, -1), new Zone(zoneSize));
        zones.put(new ZoneCoordinate(0,-1), new Zone(zoneSize));
        zones.put(new ZoneCoordinate(1,-1), new Zone(zoneSize));
        zones.put(new ZoneCoordinate(-1,0), new Zone(zoneSize));
        zones.put(new ZoneCoordinate(0,0), new Zone(zoneSize));
        zones.put(new ZoneCoordinate(1, 0), new Zone(zoneSize));
        zones.put(new ZoneCoordinate(-1,1), new Zone(zoneSize));
        zones.put(new ZoneCoordinate(0,1), new Zone(zoneSize));
        zones.put(new ZoneCoordinate(1,1), new Zone(zoneSize));
    }
}
