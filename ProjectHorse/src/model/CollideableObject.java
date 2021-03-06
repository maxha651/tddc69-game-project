package model;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObject;

import java.util.Collection;

/**
 * Any WorldObject which can collide with other objects
 */
public abstract class CollideableObject extends MoveableObject {

    private final static double ALLOWED_INTERSECTION = 0.9;

    protected int mass;

    public int getMass(){
        return mass;
    }

    protected boolean isCollideable(Object o){
        return CollideableObject.class.isAssignableFrom(o.getClass());
    }

    protected void setToCollide(CollideableObject object){
        return;
     }

    /**
     * Checks if object collides with another object (collideable).
     */
    protected boolean isColliding(CollideableObject collideable, double zoneSize){
        double xDiff = Math.abs(getYDifference(collideable, zoneSize));
        double yDiff = Math.abs(getXDifference(collideable, zoneSize));

        double allowedXDiff = (this.getBoundingWidth() + collideable.getBoundingWidth())/2 * ALLOWED_INTERSECTION;
        double allowedYDiff = (this.getBoundingHeight() + collideable.getBoundingHeight())/2 * ALLOWED_INTERSECTION;

        return xDiff < allowedXDiff && yDiff < allowedYDiff;
    }

    /**
     * Moves two colliding(doesn't check if colliding!) objects c1 and c2
     * away from each other so they don't collide anymore
     *
     * @param xIntersect The amount the two objects intersects in x
     * @param yIntersect The amount the two objects intersects in y
     */
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

    /**
     * Checks whether the object collides with another object and acts accordingly.
     *
     * @param world The World which contains the object
     */
    protected void collisionCheck(World world){

        Collection<WorldObject> nearbyObjects;
        int checkDistance = GameModel.COLLIDING_CHECK_DISTANCE;

        nearbyObjects = world.getAllObjectsInArea(zoneCoordinate,
                new Coordinate(coordinate.getX() - checkDistance, coordinate.getY() - checkDistance),
                new Coordinate(coordinate.getX() + checkDistance, coordinate.getY() + checkDistance));

        for(WorldObject nearbyObject : nearbyObjects){
            if (isCollideable(nearbyObject) && nearbyObject != this){
                if(isColliding((CollideableObject) nearbyObject, world.getZoneSize())){
                    setToCollide((CollideableObject) nearbyObject);
                }
            }
        }
    }
}
