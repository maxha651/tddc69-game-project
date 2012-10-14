package model.world;

import model.AbstractGameObject;
import model.GameModel;
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
        if(this.state == WorldObjectState.ALIVE){
            return true;
        }
        return false;
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
