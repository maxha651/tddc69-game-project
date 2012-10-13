package model.world;

import model.CollideCheck;
import model.GameModel;
import model.MoveableObject;
import model.properties.Collideable;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-26
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class World {
    double zoneSize;
    ConcurrentHashMap<ZoneCoordinate, Zone> zones;

    public int getNumberOfWorldObjects() {
        return numberOfWorldObjects;
    }

    int numberOfWorldObjects = 0;

    public World(double zoneSize){
        this.zoneSize = zoneSize;
        this.zones = new ConcurrentHashMap<ZoneCoordinate, Zone>();
        // creates 9 zones around origo (not needed?)
        createZones();
    }

    private Zone getZone(ZoneCoordinate coordinate){
        Zone zone = zones.get(coordinate);

        if (zone == null){
            zones.put(coordinate, new Zone(zoneSize));
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

    private void clearZone(ZoneCoordinate zoneCoordinate){
        if(zoneExists(zoneCoordinate)){ // create function
            Zone zone = getZone(zoneCoordinate);
            numberOfWorldObjects -= zone.getWorldObjects().size();
            zone.clear();
        }
    }

    private void clearAdjacentZones(ZoneCoordinate start, ZoneCoordinate stop){
        for (int x = start.getX(); x <= stop.getX(); x++){
            ZoneCoordinate tempCoord = new ZoneCoordinate(x, start.getY());
            clearZone(tempCoord);
            tempCoord.setY(stop.getY()); // doesn't work with +1
            clearZone(tempCoord);
        }
        for (int y = start.getY(); y <= stop.getY(); y++){
            ZoneCoordinate tempCoord = new ZoneCoordinate(start.getX(), y);
            clearZone(tempCoord);
            tempCoord.setX(stop.getX()); // doesn't work with +1
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
    }

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
            if(!object.isAlive()){
                zone.removeWorldObject(object);
                numberOfWorldObjects--;
                continue;
            }
            if(isMoveable(object)){
                updateMoveable((MoveableObject) object, zone);
            }
            if(isCollideable(object)){
                updateCollideable((Collideable) object);
            }
        }
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
