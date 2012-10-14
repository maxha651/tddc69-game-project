package model;

import model.properties.Collideable;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObject;
import model.world.WorldObjectContainer;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 10/14/12
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class CollideableObject extends MoveableObject {

    protected boolean isCollideable(Object o){
        return CollideableObject.class.isAssignableFrom(o.getClass());
    }

    protected void setToCollide(CollideableObject object){
        return;
    }

    protected boolean isColliding(CollideableObject collideable, double zoneSize){
        ZoneCoordinate thisZoneCoord = new ZoneCoordinate(zoneCoordinate);
        Coordinate thisCoord = new Coordinate(coordinate);

        ZoneCoordinate thatZoneCoord = new ZoneCoordinate(collideable.getZoneCoordinate());
        Coordinate thatCoord = new Coordinate(collideable.getCoordinate());

        if(!thatZoneCoord.equals(thisZoneCoord)){
            double yZoneDiff = (thatZoneCoord.getY() - thisZoneCoord.getY()) * zoneSize;
            double xZoneDiff = (thatZoneCoord.getX() - thisZoneCoord.getX()) * zoneSize;

            thisCoord.setY(thisCoord.getY() + yZoneDiff);
            thisCoord.setX(thisCoord.getX() + xZoneDiff);
        }

        double xDiff = Math.abs(thatCoord.getX() - thisCoord.getX());
        double yDiff = Math.abs(thatCoord.getY() - thisCoord.getY());
        double allowedXDiff = Math.abs(this.getBoundingWidth() - collideable.getBoundingWidth());
        double allowedYDiff = Math.abs(this.getBoundingHeight() - collideable.getBoundingHeight());

        if( xDiff < allowedXDiff && yDiff < allowedYDiff){
            //unCollide(this, collideable, allowedXDiff - xDiff, allowedYDiff - yDiff);
            return true;
        }

        return false;
    }

    protected static void unCollide(CollideableObject c1, CollideableObject c2, double xIntersect, double yIntersect){
        Coordinate c1Coord = c1.getCoordinate();
        Coordinate c2Coord = c2.getCoordinate();

        if(xIntersect < yIntersect){
            c1Coord.setX(c1Coord.getX() - xIntersect/2);
            c2Coord.setX(c2Coord.getX() + xIntersect/2);
        }
        else{
            c1Coord.setY(c1Coord.getY() - yIntersect/2);
            c2Coord.setY(c2Coord.getY() + yIntersect/2);
        }
    }

    protected void collisionCheck(World world){

        WorldObjectContainer nearbyObjects;
        int checkDistance = GameModel.COLLIDING_CHECK_DISTANCE;

        nearbyObjects = world.getAllObjectsInArea(zoneCoordinate,
                new Coordinate(coordinate.getX() - checkDistance, coordinate.getY() - checkDistance),
                new Coordinate(coordinate.getX() + checkDistance, coordinate.getY() + checkDistance));

        for(WorldObject nearbyObject : nearbyObjects){
            if (isCollideable(nearbyObject) && nearbyObject != this){
                if(isColliding((CollideableObject) nearbyObject, world.getZoneSize())){
                    setToCollide((CollideableObject) nearbyObject);
                    ((CollideableObject) nearbyObject).setToCollide(this);
                }
            }
        }
    }
}
