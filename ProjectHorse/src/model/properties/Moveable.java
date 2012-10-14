package model.properties;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

public interface Moveable {
    public double getVelocityX();
    public double getVelocityY();
    public Coordinate getCoordinate();
    public ZoneCoordinate getZoneCoordinate();
}
