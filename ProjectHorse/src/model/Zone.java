package model;

import model.utility.shape.Coordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-22
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class Zone {

    private final static int NUM_OF_ZONES_PER_LEVEL = 3; // per row i.e. 10 equals 10^2 = 100 zones



    private Coordinate pos;
    private double size;
    private int numberOfLevels;

    private ArrayList<Zone> nextLevelZones;
    private HashMap<Coordinate, LinkedList<WorldObject>> worldObjects;

    public Zone(Coordinate position, double size, int numberOfLevels) {
        this.pos = position;
        this.size = size;
        this.numberOfLevels = numberOfLevels;

        if (!isLastLevel()){
            nextLevelZones = new ArrayList<Zone>();
            double sizeOfNewLevel = size / NUM_OF_ZONES_PER_LEVEL;

            for (int i = 0; i < Math.pow(NUM_OF_ZONES_PER_LEVEL, 2); i++){
                position.add(sizeOfNewLevel);
                nextLevelZones.add(new Zone(position, sizeOfNewLevel, numberOfLevels -1));
            }
        }
        else{
            worldObjects = new HashMap<Coordinate, LinkedList<WorldObject>>();
        }
    }

    public boolean contains(Coordinate coordinate){
        if (!withinBoundaries(coordinate)){
            return false;
        }

        if(isLastLevel()){
            return worldObjects.containsKey(coordinate);
        }
        else{
            Zone temp = getZone(coordinate);
            return temp.contains(coordinate);
        }
    }

    public void add(WorldObject object) throws IndexOutOfBoundsException {
        if (!withinBoundaries(object)){
            throw new IndexOutOfBoundsException();
        }

        if(isLastLevel()){
            LinkedList<WorldObject> list = new LinkedList<WorldObject>();
            list.add(object);
            worldObjects.put(object.getCoordinate(), list);
        }
        else{
            Zone temp = getZone(object);
            temp.add(object);
        }
    }

    public LinkedList<WorldObject> get(Coordinate position){
        if (!withinBoundaries(position)){
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
     * @param start The upper left corner of the search area
     * @param stop The lower right corner of the search area
     * @return Returns all objects in the rectangular area from start to stop
     */

    public LinkedList<WorldObject> getAll(Coordinate start, Coordinate stop){
        if (!withinBoundaries(start) && !withinBoundaries(stop)){
            throw new IndexOutOfBoundsException();
        }

        if (isLastLevel()){
            LinkedList<LinkedList<WorldObject>> allObjects;
            allObjects = (LinkedList<LinkedList<WorldObject>>) worldObjects.values();

            LinkedList<WorldObject> resObjects = new LinkedList<WorldObject>();
            for (LinkedList<WorldObject> object : allObjects){
                Coordinate tempCoordinate = object.getFirst().getCoordinate(); // same coordinate for all in object
                if (tempCoordinate.getX() >= start.getX() && tempCoordinate.getX() <= stop.getX() &&
                        tempCoordinate.getY() >= start.getY() && tempCoordinate.getY() <= stop.getY()){

                    resObjects.addAll(object);
                }
            }
            return resObjects;
        }
        else{
            Zone tempStart = getZone(start);
            Zone tempStop = getZone(stop);
            if (tempStart == tempStop){
                return tempStart.getAll(start, stop);
            }
            else{
                LinkedList<WorldObject> resObjects = tempStart.getAll(start, stop);
                resObjects.addAll(tempStop.getAll(start, stop));
                return resObjects;
            }
        }
    }

    public void update(){
        if(isLastLevel()){
            for(LinkedList<WorldObject> objects : worldObjects.values()){
                for(WorldObject object : objects){
                    LinkedList<WorldObject> tempObjects;
                    if(!withinBoundaries(object)){
                        if (true){ // sic!
                            tempObjects = new LinkedList<WorldObject>();
                        }
                        tempObjects.add(object);
                        objects.remove(object);
                        // Will change things
                        if (objects.isEmpty()){
                            worldObjects.remove(tempObjects.getFirst().getCoordinate());
                        }
                    }
                }
            }
        }
    }

    private Zone getZone(Coordinate position){
        if(isLastLevel()){
            return this;
        }
        position.subtract(pos);
        int index = (int) (position.getX() / size) * NUM_OF_ZONES_PER_LEVEL;
        index += ((int) (position.getY() / size) * NUM_OF_ZONES_PER_LEVEL) * NUM_OF_ZONES_PER_LEVEL;
        return nextLevelZones.get(index);
    }

    private Zone getZone(WorldObject object){
        return getZone(object.getCoordinate());
    }

    private boolean withinBoundaries(Coordinate position){
        if (position.getX() < pos.getX() || position.getX() > pos.getX() + size -1){
            return false;
        }
        if (position.getY() < pos.getY() || position.getY() > pos.getY() + size -1){
            return false;
        }
        return true;
    }


    private boolean withinBoundaries(WorldObject object){
        return withinBoundaries(object.getCoordinate());
    }

    public boolean isLastLevel(){
        return numberOfLevels == 0;
    }

    public Coordinate getCoordinate() {
        return pos;
    }

    public double getSize() {
        return size;
    }
}
