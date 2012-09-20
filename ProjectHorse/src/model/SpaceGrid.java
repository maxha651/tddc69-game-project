package model;

import javax.swing.text.Position;
import java.util.ArrayList;

/**
 * A grid containing an arbitrary element in every position. In our
 * case we will have either another SpaceGrid or a bucket. Also
 * contains all objects in the grid as well as their positions
 * in space. Has also its own coordinates which is the upper right corner
 */
public class SpaceGrid<E> {

    private Position spaceGridPos;
    private ArrayList<WorldObject> worldObjects;
    private int height;
    private int width;
    private double size;
    private ArrayList<ArrayList<E>> grid;

    public SpaceGrid(Position spaceGridPos, ArrayList<WorldObject> worldObjects, int height, int width, double size) {
        this.spaceGridPos = spaceGridPos;
        this.worldObjects = worldObjects;
        this.height = height;
        this.width = width;
        this.size = size;

        this.grid = new ArrayList<ArrayList<E>>();
        reset();
    }

    public void reset(){
        grid.clear();

        for (int i = 0; i < height; i++){
            grid.add(new ArrayList<E>());
            ArrayList<E> temp = grid.get(i);
            for (int j = 0; j < width; j++){
                temp.add(null);
            }
        }
    }

    public void addWorldObject(WorldObject worldObject){
        //get position
        //insert into list and add to appropiate bucket
    }

    public ArrayList<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public E getBucket(int x, int y){
        return grid.get(y).get(x);
    }

    public void setBucket(int x, int y, E bucket){
        grid.get(y).set(x, bucket);
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getSize() {
        return size;
    }

    public Position getSpaceGridPos() {
        return spaceGridPos;
    }
}
