package model.world;

import model.utility.enums.CardinalDirection;
import model.utility.shape.Coordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-22
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class Zone {

    private Zone center;
    private Zone north;
    private Zone northEast;
    private Zone east;
    private Zone southEast;
    private Zone south;
    private Zone southWest;
    private Zone west;
    private Zone northWest;

    private Coordinate pos;
    private double size;
    private int numberOfLevels;

    private WorldObjectContainer worldObjects = new WorldObjectContainer();

    public Zone(Coordinate position, double size, int numberOfLevels) {
        this.pos = position;
        this.size = size;
        this.numberOfLevels = numberOfLevels;

        if (isLastLevel()){
            worldObjects = new WorldObjectContainer();
        }
        else{
            double tempSize = size / 3.0;
            int tempNumberOfLevels = numberOfLevels -1;
            Coordinate temp = new Coordinate(position.getX(), position.getY());
            northWest = new Zone(temp, tempSize, tempNumberOfLevels);
            temp.setX(temp.getX() + tempSize);
            north =     new Zone(temp, tempSize, tempNumberOfLevels);
            temp.setX(temp.getX() + tempSize);
            northEast = new Zone(temp, tempSize, tempNumberOfLevels);
            temp.setY(temp.getY() + tempSize);
            east =      new Zone(temp, tempSize, tempNumberOfLevels);
            temp.setY(temp.getY() + tempSize);
            southEast = new Zone(temp, tempSize, tempNumberOfLevels);
            temp.setX(temp.getX() - tempSize);
            south =     new Zone(temp, tempSize, tempNumberOfLevels);
            temp.setX(temp.getX() - tempSize);
            southWest = new Zone(temp, tempSize, tempNumberOfLevels);
            temp.setY(temp.getY() - tempSize);
            west =      new Zone(temp, tempSize, tempNumberOfLevels);
            temp.setX(temp.getX() + tempSize);
            center =    new Zone(temp, tempSize, tempNumberOfLevels);
        }
    }

    public boolean contains(Coordinate coordinate){
        if (!isWithinBoundaries(coordinate)){
            return false;
        }

        if(isLastLevel()){
            return worldObjects.contains(coordinate);
        }
        else{
            Zone temp = getZone(coordinate);
            return temp.contains(coordinate);
        }
    }

    public void add(WorldObject object) throws IndexOutOfBoundsException {
        if (!isWithinBoundaries(object)){
            throw new IndexOutOfBoundsException();
        }

        if(isLastLevel()){
            worldObjects.add(object);
        }
        else{
            Zone temp = getZone(object);
            temp.add(object);
        }
    }

    public WorldObjectContainer get(Coordinate position){
        if (!isWithinBoundaries(position)){
            throw new IndexOutOfBoundsException();
        }

        if (isLastLevel()){
            return worldObjects.get(position);
        }
        else{
            Zone temp = getZone(position);
            return temp.get(position);
        }
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
      /*  if (!isWithinBoundaries(start) && !isWithinBoundaries(stop)){
            throw new IndexOutOfBoundsException();
        } */

        if (isLastLevel()){
            return getAllObjectsInAreaLastLevel(start, stop);
        }
        else{
            if (start.getX() - stop.getX() >= size || // if larger than subzones, return all objects in zone
                    start.getY() - stop.getY() >= size){
                return getAllObjectsInAreaLastLevel(start, stop);
            }
            else{

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
        }
    }

    private WorldObjectContainer getAllObjectsInAreaLastLevel(Coordinate start, Coordinate stop){
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

    protected WorldObjectContainer removeObjectsToReinsert(){
        WorldObjectContainer resObjects = new WorldObjectContainer();

        if(isLastLevel()){
            for(WorldObject object : worldObjects){ // check if isMoveable
                if(!isWithinBoundaries(object)){
                    resObjects.add(object);
                    worldObjects.remove(object);
                }
            }
        }
        else{
            resObjects.addAll(northWest.removeObjectsToReinsert());
            resObjects.addAll(north.removeObjectsToReinsert());
            resObjects.addAll(northEast.removeObjectsToReinsert());
            resObjects.addAll(west.removeObjectsToReinsert());
            resObjects.addAll(center.removeObjectsToReinsert());
            resObjects.addAll(east.removeObjectsToReinsert());
            resObjects.addAll(southWest.removeObjectsToReinsert());
            resObjects.addAll(south.removeObjectsToReinsert());
            resObjects.addAll(southEast.removeObjectsToReinsert());
        }
        return resObjects;
    }
    /* Broken, fix later
    public void update(){
        if(isLastLevel()){
            WorldObjectContainer objects;
            objects = removeObjectsToReinsert();

            for (WorldObject object : objects){
                this.add(object);
            }
        }
    }

    public void update(Coordinate start, Coordinate stop){

        WorldObjectContainer objects;
        objects = getAllObjectsInArea(start, stop);

        for (WorldObject object : objects){
            this.add(object);
        }
    }*/

    protected Zone getZone(Coordinate position){
        if(isLastLevel()){
            return this;
        }
        position.subtract(pos);
        int index = (int) (position.getX() / size) * 3;
        index += ((int) (position.getY() / size) * 3) * 3;

        switch (index){
            case 0 : return northWest;
            case 1 : return north;
            case 2 : return northEast;
            case 3 : return west;
            case 4 : return center;
            case 5 : return east;
            case 6 : return southWest;
            case 7 : return south;
            case 8 : return southEast;
        }
        throw new IndexOutOfBoundsException();
    }

    protected Zone getZone(WorldObject object){
        return getZone(object.getCoordinate());
    }

    private boolean isWithinBoundaries(Coordinate position){
        if (position.getX() < pos.getX() || position.getX() > pos.getX() + size){
            return false;
        }
        if (position.getY() < pos.getY() || position.getY() > pos.getY() + size){
            return false;
        }
        return true;
    }

    private boolean isWithinBoundaries(WorldObject object){
        return isWithinBoundaries(object.getCoordinate());
    }

    public boolean isLastLevel(){
        return numberOfLevels == 0;
    }

    protected Zone getZone(CardinalDirection direction){
        switch(direction){
            case CENTER:    return center;
            case NORTH:     return north;
            case SOUTH:     return south;
            case EAST:      return east;
            case WEST:      return west;
            case NORTHEAST: return northEast;
            case NORTHWEST: return northWest;
            case SOUTHEAST: return southEast;
            case SOUTHWEST: return southWest;
        }
        //never happens
        return null;
    }

    protected void setZone(CardinalDirection direction, Zone zone){
        switch(direction){
            case CENTER:    center = zone;
            case NORTH:     north = zone;
            case SOUTH:     south = zone;
            case EAST:      east = zone;
            case WEST:      west = zone;
            case NORTHEAST: northEast = zone;
            case NORTHWEST: northWest = zone;
            case SOUTHEAST: southEast = zone;
            case SOUTHWEST: southWest = zone;
        }
    }

    protected WorldObjectContainer getWorldObjects() {
        return worldObjects;
    }

    protected int getNumberOfLevels() {
        return numberOfLevels;
    }

    public Coordinate getCoordinate() {
        return pos;
    }

    public double getSize() {
        return size;
    }
}
