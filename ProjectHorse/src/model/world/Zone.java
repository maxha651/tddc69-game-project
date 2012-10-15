package model.world;

import model.utility.shape.Coordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-22
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
// Implement Zone as synchronized WorldObjectContainer (?)
public class Zone {
    private final Coordinate origo;
    private final double zoneSize;

    private final WorldObjectContainer worldObjects;

    public Zone(double zoneSize) {
        this.origo = new Coordinate(0.0, 0.0);
        this.zoneSize = zoneSize;
        worldObjects = new WorldObjectContainer();
    }

    public void add(WorldObject object) throws IndexOutOfBoundsException {
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

    private boolean isWithinBoundaries(Coordinate position){
        if (position.getX() < origo.getX() || position.getX() > origo.getX() + zoneSize){
            return false;
        }
        if (position.getY() < origo.getY() || position.getY() > origo.getY() + zoneSize){
            return false;
        }
        return true;
    }

    public boolean isWithinBoundaries(WorldObject object){
        return isWithinBoundaries(object.getCoordinate());
    }

    protected WorldObjectContainer getWorldObjects() {
        return worldObjects;
    }
}
