package model.world;

import model.AbstractGameObject;
import model.properties.Boundable;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

/**
 * Any object to be put in the world is an WorldObject
 *
 */

public abstract class WorldObject extends AbstractGameObject implements Boundable {

    protected Coordinate coordinate;
    protected ZoneCoordinate zoneCoordinate;
    protected double rotationAngle;
    protected double width = 15, height = 1;

    protected WorldObjectState state = WorldObjectState.ALIVE;
    protected double boundingHeight;
    protected double boundingWidth;

    public WorldObjectState getState() {
        return state;
    }

    /**
     * Alters ZoneCoordinate and Coordinate as the object changes zone.
     *
     * @param zoneSize The size of the zone which contains the object
     */
    public void updateZone(double zoneSize){
        while (coordinate.getX() > zoneSize){
            zoneCoordinate.setX(zoneCoordinate.getX() +1);
            coordinate.setX(coordinate.getX() % zoneSize);
        }
        while (coordinate.getX() < 0.0){
            zoneCoordinate.setX(zoneCoordinate.getX() -1);
            coordinate.setX(zoneSize + coordinate.getX());
        }
        while (coordinate.getY() > zoneSize){
            zoneCoordinate.setY(zoneCoordinate.getY() +1);
            coordinate.setY(coordinate.getY() % zoneSize);
        }
        while (coordinate.getY() < 0.0){
            zoneCoordinate.setY(zoneCoordinate.getY() -1);
            coordinate.setY(zoneSize + coordinate.getY());
        }
    }

    /**
     * Checks if the object is outside the zone
     *
     */
    public boolean isOutOfBounds(double zoneSize){
        return coordinate.getX() < 0 || coordinate.getX() > zoneSize ||
                coordinate.getY() < 0 || coordinate.getY() > zoneSize;
    }

    /**
     * The vertical difference between the coordinate of this object and another object's
     *
     */
    public int getXDifference(WorldObject worldObject, double zoneSize){
        ZoneCoordinate thisZoneCoord = new ZoneCoordinate(zoneCoordinate);
        Coordinate thisCoord = new Coordinate(coordinate);

        ZoneCoordinate thatZoneCoord = new ZoneCoordinate(worldObject.getZoneCoordinate());
        Coordinate thatCoord = new Coordinate(worldObject.getCoordinate());

        if(!thatZoneCoord.equals(thisZoneCoord)){
            double xZoneDiff = (double) (thatZoneCoord.getX() - thisZoneCoord.getX());
            thatCoord.setX(thatCoord.getX() + xZoneDiff*zoneSize);
        }

        return (int) (thatCoord.getX() - thisCoord.getX());
    }

    /**
     * The horizontal difference between the coordinate of this object and another object's
     *
     */
    public int getYDifference(WorldObject worldObject, double zoneSize){
        ZoneCoordinate thisZoneCoord = new ZoneCoordinate(zoneCoordinate);
        Coordinate thisCoord = new Coordinate(coordinate);

        ZoneCoordinate thatZoneCoord = new ZoneCoordinate(worldObject.getZoneCoordinate());
        Coordinate thatCoord = new Coordinate(worldObject.getCoordinate());

        if(!thatZoneCoord.equals(thisZoneCoord)){
            double yZoneDiff = (double) (thatZoneCoord.getY() - thisZoneCoord.getY());
            thatCoord.setY(thatCoord.getY() + yZoneDiff*zoneSize);
        }

        return (int) (thatCoord.getY() - thisCoord.getY());
    }

    /**
     * Calculates the angle to another object
     *
     * @return angle in radians
     */
    public double getAngleTo(WorldObject worldObject, double zoneSize){
        double xDiff = getXDifference(worldObject, zoneSize);
        double yDiff = getYDifference(worldObject, zoneSize);
        double angle = Math.atan(yDiff/xDiff);

        if(xDiff < 0){
            angle += Math.PI;
        }
        return angle;
    }

    public void setState(WorldObjectState state) {
        this.state = state;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public ZoneCoordinate getZoneCoordinate(){
        return zoneCoordinate;
    }

    public boolean isAlive() {
        return this.state == WorldObjectState.ALIVE;
    }

    public void update(World world){
        if (!isAlive()){
            this.destroy(world);
        }
    }

    public void destroy(World world){
        world.removeWorldObject(this);
    }

    @Override
    public double getBoundingWidth() {
        return boundingWidth;
    }

    @Override
    public double getBoundingHeight() {
        return boundingHeight;
    }
}
