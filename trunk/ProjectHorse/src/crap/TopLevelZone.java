package crap;

import model.character.Player;
import model.utility.enums.CardinalDirection;
import model.utility.shape.Coordinate;
import model.world.*;

import java.util.HashMap;
import java.util.LinkedList;

import static model.utility.enums.CardinalDirection.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-21
 * Time: 01:19
 * To change this template use File | Settings | File Templates.
 */
public class TopLevelZone extends model.world.Zone { // Will not use, probably

    private Player player;
    private HashMap<Coordinate, model.world.Zone> oldZones;

    public TopLevelZone(Coordinate origo, double size, int numberOfLevels, Player player) {
        super(origo, size, numberOfLevels);
        this.player = player;
        this.oldZones = new HashMap<Coordinate, model.world.Zone>();

        // place player with collision check?
    }

    public void update(){

        if(!getZone(CENTER).contains(player.getCoordinate())){
            Coordinate tempPlayerCoordinate = player.getCoordinate();
            Coordinate tempCenterCoordinate;
            tempCenterCoordinate = new Coordinate(getZone(CENTER).getCoordinate());

            if (tempPlayerCoordinate.getX() < tempCenterCoordinate.getX()){
                moveWorldLeft();
            }
            else if (tempPlayerCoordinate.getX() > tempCenterCoordinate.getX() + getSize()){
                moveWorldRight();
            }
            if (tempPlayerCoordinate.getY() < tempCenterCoordinate.getY()){
                moveWorldUp();
            }
            else if (tempPlayerCoordinate.getY() > tempCenterCoordinate.getY() + getSize()){
                moveWorldDown();
            } // call update function?
        }
    }

    private model.world.Zone removeOldZone(Coordinate coordinate){
        return oldZones.remove(coordinate);
    }

    private void moveWorldLeft(){
        oldZones.put(getZone(NORTHEAST).getCoordinate(), getZone(NORTHEAST));
        oldZones.put(getZone(EAST).getCoordinate(), getZone(EAST));
        oldZones.put(getZone(SOUTHEAST).getCoordinate(), getZone(SOUTHEAST));

        setZone(NORTHEAST, getZone(NORTH));
        setZone(EAST, getZone(CENTER));
        setZone(SOUTHEAST, getZone(SOUTH));
        setZone(NORTH, getZone(NORTHWEST));
        setZone(CENTER, getZone(WEST));
        setZone(SOUTH, getZone(SOUTHWEST));

        Coordinate tempCoordinate;
        model.world.Zone tempZone;
        double tempSize = getSize();

        tempCoordinate = new Coordinate(getZone(NORTH).getCoordinate());

        tempCoordinate.setX(tempCoordinate.getX() - tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(NORTHWEST, tempZone);
        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(WEST, tempZone);
        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(SOUTHWEST, tempZone);
    }

    private void moveWorldRight(){
        oldZones.put(getZone(NORTHWEST).getCoordinate(), getZone(NORTHWEST));
        oldZones.put(getZone(WEST).getCoordinate(), getZone(WEST));
        oldZones.put(getZone(SOUTHWEST).getCoordinate(), getZone(SOUTHWEST));

        setZone(NORTHWEST, getZone(NORTH));
        setZone(WEST, getZone(CENTER));
        setZone(SOUTHWEST, getZone(SOUTH));
        setZone(NORTH, getZone(NORTHEAST));
        setZone(CENTER, getZone(EAST));
        setZone(SOUTH, getZone(SOUTHEAST));

        Coordinate tempCoordinate;
        model.world.Zone tempZone;
        double tempSize = getSize();

        tempCoordinate = new Coordinate(getZone(NORTH).getCoordinate());

        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(NORTHEAST, tempZone);
        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(EAST, tempZone);
        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(SOUTHEAST, tempZone);
    }

    private void moveWorldUp(){

        oldZones.put(getZone(SOUTHWEST).getCoordinate(), getZone(SOUTHWEST));
        oldZones.put(getZone(SOUTH).getCoordinate(), getZone(SOUTH));
        oldZones.put(getZone(SOUTHEAST).getCoordinate(), getZone(SOUTHEAST));

        setZone(SOUTHWEST, getZone(WEST));
        setZone(SOUTH, getZone(CENTER));
        setZone(SOUTHEAST, getZone(EAST));
        setZone(WEST, getZone(NORTHWEST));
        setZone(CENTER, getZone(NORTH));
        setZone(EAST, getZone(NORTHEAST));

        Coordinate tempCoordinate;
        model.world.Zone tempZone;
        double tempSize = getSize();

        tempCoordinate = new Coordinate(getZone(WEST).getCoordinate());

        tempCoordinate.setY(tempCoordinate.getY() - tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(NORTHWEST, tempZone);
        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(NORTH, tempZone);
        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(NORTHEAST, tempZone);
    }

    private void moveWorldDown(){
        oldZones.put(getZone(NORTHWEST).getCoordinate(), getZone(NORTHWEST));
        oldZones.put(getZone(NORTH).getCoordinate(), getZone(NORTH));
        oldZones.put(getZone(NORTHEAST).getCoordinate(), getZone(NORTHEAST));

        setZone(NORTHWEST, getZone(WEST));
        setZone(NORTH, getZone(CENTER));
        setZone(NORTHEAST, getZone(EAST));
        setZone(WEST, getZone(SOUTHWEST));
        setZone(CENTER, getZone(SOUTH));
        setZone(EAST, getZone(SOUTHEAST));

        Coordinate tempCoordinate;
        model.world.Zone tempZone;
        double tempSize = getSize();

        tempCoordinate = new Coordinate(getZone(WEST).getCoordinate());

        tempCoordinate.setY(tempCoordinate.getY() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(SOUTHWEST, tempZone);
        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(SOUTH, tempZone);
        tempCoordinate.setX(tempCoordinate.getX() + tempSize);
        tempZone = removeOldZone(tempCoordinate);
        if(tempZone == null){
            tempZone = new model.world.Zone(tempCoordinate, tempSize, getNumberOfLevels());
        }
        setZone(SOUTHEAST, tempZone);
    }
}
