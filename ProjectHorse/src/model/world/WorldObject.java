package model.world;

import model.AbstractGameObject;
import model.properties.Boundable;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-21
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class WorldObject extends AbstractGameObject implements Boundable {

    //uses width and height when painting actual object
    //uses getBounds when painting bounds

    protected Coordinate coordinate;
    protected ZoneCoordinate zoneCoordinate;
    protected double rotationAngle;
    protected double width = 15, height = 1;
    protected int z = 0;

    protected WorldObjectState state = WorldObjectState.ALIVE;
    protected double boundingHeight;
    protected double boundingWidth;

    public WorldObjectState getState() {
        return state;
    }

    public void setState(WorldObjectState state) {
        this.state = state;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setZoneCoordinate(ZoneCoordinate zoneCoordinate) {
        this.zoneCoordinate = zoneCoordinate;
    }

    public boolean isAlive() {
        if(this.state == WorldObjectState.ALIVE){
            return true;
        }
        return false;
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
