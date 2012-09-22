package model;

import model.character.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-21
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
public class World { // Make to update when player moves

    private final static int NUM_OF_LEVELS = 3;

    private int height;
    private int width;
    private Player player;

    private Zone Center;
    private Zone North;
    private Zone NorthEast;
    private Zone East;
    private Zone SouthEast;
    private Zone South;
    private Zone SouthWest;
    private Zone NorthWest;

    public World(int height, int width) {
        this.height = height;
        this.width = width;

        // Initialize all around origo
    }
}
