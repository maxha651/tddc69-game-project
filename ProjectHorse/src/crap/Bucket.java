package crap;

import model.world.WorldObject;
import model.utility.shape.Coordinate;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-21
 * Time: 01:07
 * To change this template use File | Settings | File Templates.
 */
public class Bucket {

    private Coordinate spaceGridPos; // Ändra till egen representation
    private ArrayList<WorldObject> worldObjects;
    private double size;

    public Bucket(Coordinate spaceGridPos, ArrayList<WorldObject> worldObjects, double size) {
        this.spaceGridPos = spaceGridPos;
        this.worldObjects = worldObjects;
        this.size = size;
    }

    public void addWorldObject(WorldObject worldObject){
        worldObjects.add(worldObject);
    }

    public void setSpaceGridPos(Coordinate spaceGridPos) {
        this.spaceGridPos = spaceGridPos;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public ArrayList<WorldObject> getWorldObjects() {
        return worldObjects;
        //Fixa funktion som returnerar iterator?
    }

    public double getSize() {
        return size;
    }

    public Coordinate getSpaceGridPos() {
        return spaceGridPos;
    }

    public void reset(){
        worldObjects.clear();
    }
}
