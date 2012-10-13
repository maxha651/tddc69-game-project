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
    private Coordinate origo;
    private double size;

    private WorldObjectContainer worldObjects;

    public Zone(double size) {
        this.origo = new Coordinate(0.0, 0.0);
        this.size = size;
        worldObjects = new WorldObjectContainer();
    }

    public void add(WorldObject object) throws IndexOutOfBoundsException {
        if (!isWithinBoundaries(object)){
            throw new IndexOutOfBoundsException();
        }
        worldObjects.add(object);
    }

    public boolean removeWorldObject(WorldObject object){
        return worldObjects.remove(object);
    }

    public void clear(){
        worldObjects.clear();
    }

    /**
     * Returns all objects within a rectangle with its upper left corner at start
     * and its lower right corner at stop
     *
     * @param start The upper left corner of the search area
     * @param stop The lower right corner of the search area
     * @return Returns all objects in the rectangular area from start to stop
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

    private boolean isWithinBoundaries(Coordinate position){
        if (position.getX() < origo.getX() || position.getX() > origo.getX() + size){
            return false;
        }
        if (position.getY() < origo.getY() || position.getY() > origo.getY() + size){
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
