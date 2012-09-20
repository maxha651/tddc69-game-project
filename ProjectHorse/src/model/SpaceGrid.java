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
    private ArrayList<AbstractGameObject> abstractGameObjects;
    private int height;
    private int width;
    private int size;
    private E[][] grid;

    public SpaceGrid(Position spaceGridPos, ArrayList<AbstractGameObject> abstractGameObjects, int height, int width, int size) {
        this.spaceGridPos = spaceGridPos;
        this.abstractGameObjects = abstractGameObjects;
        this.height = height;
        this.width = width;
        this.size = size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSize() {
        return size;
    }

    public Position getSpaceGridPos() {
        return spaceGridPos;
    }
}
