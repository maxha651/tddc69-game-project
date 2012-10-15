package model.world;

import model.utility.shape.Coordinate;

/**
 * Contains all worldObject in a certain area
 */
public class Zone {
    private final Coordinate origo;
    private final double zoneSize;

    private final WorldObjectContainer worldObjects;

    /**
     * Creates a Zone of size zoneSize
     *
     */
    public Zone(double zoneSize) {
        this.origo = new Coordinate(0.0, 0.0);
        this.zoneSize = zoneSize;
        worldObjects = new WorldObjectContainer();
    }

    public void add(WorldObject object){
        worldObjects.add(object);
    }

    public boolean removeWorldObject(WorldObject object){
        return worldObjects.remove(object);
    }

    public double getZoneSize(){
        return zoneSize;
    }

    public int getNumberOfWorldObjects(){
        return worldObjects.size();
    }

    /**
     * Returns all objects in the zone within a rectangle with its upper left corner at start
     * and its lower right corner at stop.
     *
     * @param start The upper left corner of the search area
     * @param stop The lower right corner of the search area
     * @return All objects in the zone and rectangular area from start to stop
     */
    public WorldObjectContainer getAllObjectsInArea(Coordinate start, Coordinate stop){
        WorldObjectContainer resObjects = new WorldObjectContainer();

        for (WorldObject object : worldObjects){
            Coordinate tempCoordinate = object.getCoordinate();

            if (tempCoordinate.getX() >= start.getX() && tempCoordinate.getX() <= stop.getX() &&
                    tempCoordinate.getY() >= start.getY() && tempCoordinate.getY() <= stop.getY()){

                resObjects.add(object);
            }
        }
        return resObjects;
    }

    public WorldObjectContainer getWorldObjects() {
        return worldObjects;
    }
}
