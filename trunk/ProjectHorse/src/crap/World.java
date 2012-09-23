package crap;

import model.character.Player;
import model.utility.shape.Coordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-21
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
public class World { // Make to update when player moves

    private double size;
    private int numberOfLevels;
    private Player player;

    private Coordinate origo;

    private Zone center;
    private Zone north;
    private Zone northEast;
    private Zone east;
    private Zone southEast;
    private Zone south;
    private Zone southWest;
    private Zone west;
    private Zone northWest;

    public World(double size, int numberOfLevels) {
        this.size = size;
        this.origo = new Coordinate(0.0, 0.0);
        this.numberOfLevels = numberOfLevels;
        // place player with collision check

        Coordinate temp = new Coordinate(origo.getX(), origo.getY());
        center =    new Zone(temp, size, numberOfLevels);
        temp.setY(origo.getY() - size);
        north =     new Zone(temp, size, numberOfLevels);
        temp.setX(origo.getX() - size);
        northWest = new Zone(temp, size, numberOfLevels);
        temp.setX(origo.getX() + size);
        northEast = new Zone(temp, size, numberOfLevels);
        temp.setY(origo.getY());
        east =      new Zone(temp, size, numberOfLevels);
        temp.setY(origo.getY() + size);
        southEast = new Zone(temp, size, numberOfLevels);
        temp.setX(origo.getX());
        south =     new Zone(temp, size, numberOfLevels);
        temp.setX(origo.getX() - size);
        southWest = new Zone(temp, size, numberOfLevels);
        temp.setY(origo.getY());
        west =      new Zone(temp, size, numberOfLevels);
    }

    public void updateZones(){ //private / only tick/update function?
        if(!center.contains(player.getCoordinate())){
            Coordinate tempPlayerCoordinate = player.getCoordinate();
            Coordinate tempCenterCoordinate;
            tempCenterCoordinate = new Coordinate(center.getPosition());

            if (tempPlayerCoordinate.getX() < tempCenterCoordinate.getX()){

            }
        }
    }

    private void moveWorldLeft(){
        northEast = north;
        east = center;
        southEast = south;
        north = northWest;
        center = west;
        south = southWest;

        Coordinate temp;
        temp = new Coordinate(center.getPosition());
        temp.setX(temp.getX() - size);
        west = new Zone(temp, size, numberOfLevels);
        temp.setY(temp.getY() - size);
        northWest = new Zone(temp, size, numberOfLevels);
        temp.setY(temp.getY() + 2*size);
        southWest = new Zone(temp, size, numberOfLevels);
    }

    private void moveWorldRight(){

    }
}
