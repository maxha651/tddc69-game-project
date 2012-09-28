package model.world;

import model.AbstractGameObject;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-21
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class WorldObject extends AbstractGameObject {

    protected Coordinate coordinate;
    protected ZoneCoordinate zoneCoordinate;

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
}
