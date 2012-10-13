package model.properties;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

public interface Moveable {
    public double getVelocityX();
    public double getVelocityY();
    public Coordinate getCoordinate();
    public ZoneCoordinate getZoneCoordinate();
    public void setCoordinate(Coordinate c);
    public void updatePosition(double zoneSize);
    public void updateZone(double zoneSize);

}
