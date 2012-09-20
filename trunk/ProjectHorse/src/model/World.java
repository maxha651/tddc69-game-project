package model;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-21
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
public class World {

    private int height;
    private int width;
    private SpaceGrid<SpaceGrid<Bucket>> spaceGrid;

    public World(int height, int width) {
        this.height = height;
        this.width = width;
        /*
        SpaceGrid and Bucket implements "contains WorldObject" SpaceGrid Stores "contains WorldObject"
         Adding an object will mean it'll fall down through the grid into a bucket
         */

    }
}
