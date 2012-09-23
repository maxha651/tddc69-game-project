package model;

import model.character.Player;
import model.utility.shape.Coordinate;
import model.world.CardinalDirection;

import static model.world.CardinalDirection.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-21
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
public class TopLevelZone extends Zone{ // Make to update when player moves

    private Player player;

    public TopLevelZone(Coordinate origo, double size, int numberOfLevels, Player player) {
        super(origo, size, numberOfLevels);
        this.player = player;

        // place player with collision check?
    }

    @Override
    public void update(){
        super.update();

        if(!getZone(CENTER).contains(player.getCoordinate())){
            Coordinate tempPlayerCoordinate = player.getCoordinate();
            Coordinate tempCenterCoordinate;
            tempCenterCoordinate = new Coordinate(getZone(CENTER).getCoordinate());

            if (tempPlayerCoordinate.getX() < tempCenterCoordinate.getX()){
                moveWorldLeft();
            }
            else if (tempPlayerCoordinate.getX() > tempCenterCoordinate.getX() + getSize() / 3){
                moveWorldRight();
            }
            if (tempPlayerCoordinate.getY() < tempCenterCoordinate.getY()){
                moveWorldUp();
            }
            else if (tempPlayerCoordinate.getY() > tempCenterCoordinate.getY() + getSize() / 3){
                moveWorldDown();
            } // call update function?
        }
    }

    private void moveWorldLeft(){
        setZone(NORTHEAST, getZone(NORTH));
        setZone(EAST, getZone(CENTER));
        setZone(SOUTHEAST, getZone(SOUTH));
        setZone(NORTH, getZone(NORTHWEST));
        setZone(CENTER, getZone(WEST));
        setZone(SOUTH, getZone(SOUTHWEST));

        Coordinate tempCoordinate;
        Zone tempZone;
        double tempSize = getSize() / 3;

        tempCoordinate = new Coordinate(getZone(NORTH).getCoordinate());

        tempCoordinate.setX(tempCoordinate.getX() - tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(NORTHWEST, tempZone);
        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(WEST, tempZone);
        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(SOUTHWEST, tempZone);
    }

    private void moveWorldRight(){
        setZone(NORTHWEST, getZone(NORTH));
        setZone(WEST, getZone(CENTER));
        setZone(SOUTHWEST, getZone(SOUTH));
        setZone(NORTH, getZone(NORTHEAST));
        setZone(CENTER, getZone(EAST));
        setZone(SOUTH, getZone(SOUTHEAST));

        Coordinate tempCoordinate;
        Zone tempZone;
        double tempSize = getSize() / 3;

        tempCoordinate = new Coordinate(getZone(NORTH).getCoordinate());

        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(NORTHEAST, tempZone);
        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(EAST, tempZone);
        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(SOUTHEAST, tempZone);
    }

    private void moveWorldUp(){
        setZone(SOUTHWEST, getZone(WEST));
        setZone(SOUTH, getZone(CENTER));
        setZone(SOUTHEAST, getZone(EAST));
        setZone(WEST, getZone(NORTHWEST));
        setZone(CENTER, getZone(NORTH));
        setZone(EAST, getZone(NORTHEAST));

        Coordinate tempCoordinate;
        Zone tempZone;
        double tempSize = getSize() / 3;

        tempCoordinate = new Coordinate(getZone(WEST).getCoordinate());

        tempCoordinate.setY(tempCoordinate.getY() - tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(NORTHWEST, tempZone);
        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(NORTH, tempZone);
        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(NORTHEAST, tempZone);
    }

    private void moveWorldDown(){
        setZone(NORTHWEST, getZone(WEST));
        setZone(NORTH, getZone(CENTER));
        setZone(NORTHEAST, getZone(EAST));
        setZone(WEST, getZone(SOUTHWEST));
        setZone(CENTER, getZone(SOUTH));
        setZone(EAST, getZone(SOUTHEAST));

        Coordinate tempCoordinate;
        Zone tempZone;
        double tempSize = getSize() / 3;

        tempCoordinate = new Coordinate(getZone(WEST).getCoordinate());

        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(SOUTHWEST, tempZone);
        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(SOUTH, tempZone);
        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = new Zone(tempCoordinate, tempSize, getNumberOfLevels() -1);
        setZone(SOUTHEAST, tempZone);
    }
}
