package model;

import model.character.Player;
import model.utility.shape.Coordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-21
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
public class TopLevelZone extends Zone{ // Make to update when player moves

    private double size;
    private Coordinate origo;
    private int numberOfLevels;
    private Player player;

    private Zone center;
    private Zone north;
    private Zone northEast;
    private Zone east;
    private Zone southEast;
    private Zone south;
    private Zone southWest;
    private Zone west;
    private Zone northWest;


    public TopLevelZone(double size, int numberOfLevels, Coordinate origo, Player player) {
        this.size = size;
        this.origo = origo;
        this.numberOfLevels = numberOfLevels;
        this.player = player;

        // place player with collision check?

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

    @Override
    public void update(){
        super.update();

        if(!center.contains(player.getCoordinate())){
            Coordinate tempPlayerCoordinate = player.getCoordinate();
            Coordinate tempCenterCoordinate;
            tempCenterCoordinate = new Coordinate(center.getCoordinate());

            if (tempPlayerCoordinate.getX() < tempCenterCoordinate.getX()){
                moveWorldLeft();
            }
            else if (tempPlayerCoordinate.getX() > tempCenterCoordinate.getX() + size){
                moveWorldRight();
            }
            if (tempPlayerCoordinate.getY() < tempCenterCoordinate.getY()){
                moveWorldUp();
            }
            else if (tempPlayerCoordinate.getY() > tempCenterCoordinate.getY() + size){
                moveWorldDown();
            } // call update function?
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
        temp = new Coordinate(center.getCoordinate());
        temp.setX(temp.getX() - size);
        west = new Zone(temp, size, numberOfLevels);
        temp.setY(temp.getY() - size);
        northWest = new Zone(temp, size, numberOfLevels);
        temp.setY(temp.getY() + 2*size);
        southWest = new Zone(temp, size, numberOfLevels);
    }

    private void moveWorldRight(){
        northWest = north;
        west = center;
        southWest = south;
        north = northEast;
        center = east;
        south = southEast;

        Coordinate temp;
        temp = new Coordinate(center.getCoordinate());
        temp.setX(temp.getX() + size);
        east = new Zone(temp, size, numberOfLevels);
        temp.setY(temp.getY() - size);
        northEast = new Zone(temp, size, numberOfLevels);
        temp.setY(temp.getY() + 2*size);
        southEast = new Zone(temp, size, numberOfLevels);
    }

    private void moveWorldUp(){
        southWest = west;
        south = center;
        southEast = east;
        west = northWest;
        center = north;
        east = northEast;

        Coordinate temp;
        temp = new Coordinate(center.getCoordinate());
        temp.setY(temp.getY() - size);
        north = new Zone(temp, size, numberOfLevels);
        temp.setX(temp.getX() - size);
        northWest = new Zone(temp, size, numberOfLevels);
        temp.setX(temp.getX() + 2 * size);
        northEast = new Zone(temp, size, numberOfLevels);
    }

    private void moveWorldDown(){
        northWest = west;
        north = center;
        northEast = east;
        west = southWest;
        center = south;
        east = southEast;

        Coordinate temp;
        temp = new Coordinate(center.getCoordinate());
        temp.setY(temp.getY() + size);
        south = new Zone(temp, size, numberOfLevels);
        temp.setX(temp.getX() - size);
        southWest = new Zone(temp, size, numberOfLevels);
        temp.setX(temp.getX() + 2*size);
        southEast = new Zone(temp, size, numberOfLevels);
    }
}
