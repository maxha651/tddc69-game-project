package model;

import model.utility.shape.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-22
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class Zone {

    private final static int NUM_OF_ZONES_PER_LEVEL = 10;

    private Coordinate pos;
    private int size; //long?
    private int numberOfLevels;

    private ArrayList<Zone> nextLevelZones;
    private HashMap<Coordinate, WorldObject> worldObjects;

    public Zone(Coordinate position, int size, int numberOfLevels) {
        this.pos = position;
        this.size = size;
        this.numberOfLevels = numberOfLevels;

        if (numberOfLevels > 0){
            nextLevelZones = new ArrayList();
            int sizeOfNewLevel = size/10;
            for (int i = 0; i < NUM_OF_ZONES_PER_LEVEL; i++){
                position.add(sizeOfNewLevel);

                nextLevelZones.add(new Zone(position, sizeOfNewLevel, numberOfLevels -1));
            }
        }
        else if (numberOfLevels == 0) {
            worldObjects = new HashMap<Coordinate, WorldObject>();
        }
        else{
            // throw exception?
        }
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

    public void add(WorldObject object, Coordinate position) throws IndexOutOfBoundsException {
        if (!withinBoundaries(position)){
            throw new IndexOutOfBoundsException();
        }

        if(nextLevelZones == null){
            worldObjects.put(position, object);
        }
        else{
            position.subtract(pos);
            int index = (int) position.getX() / NUM_OF_ZONES_PER_LEVEL;
            Zone temp = nextLevelZones.get(index);
            temp.add(object, position);
        }
    }

    public WorldObject get(Coordinate position){
        if (!withinBoundaries(position)){
            throw new IndexOutOfBoundsException();
        }

        if (nextLevelZones == null){
            return worldObjects.get(position); // Not sure if works...
        }
        else{
            position.subtract(pos);
            int index = (int) position.getX() / NUM_OF_ZONES_PER_LEVEL;
            Zone temp = nextLevelZones.get(index);
            return temp.get(position);
        }
    }

    public Coordinate getPosition() {
        return pos;
    }

    public int getSize() {
        return size;
    }
}
