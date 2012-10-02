package model.interfaces;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

public interface Moveable {
    double getVelocityX();
    double getVelocityY();
    void setCoordinate(Coordinate c);
    Coordinate getCoordinate();
    ZoneCoordinate getZoneCoordinate();

}
