package model.interfaces;

import model.utility.shape.Coordinate;

public interface Moveable {
    double getVelocityX();
    double getVelocityY();
    void setPosition(double x, double y);
    void setCoordinate(double x, double y);
    void setCoordinate(Coordinate c);
    Coordinate getCoordinate();

}
