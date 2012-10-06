package model.interfaces;


import java.awt.*;

public interface Collideable extends Moveable, Boundable{
    public void collidesWith(Collideable c, double zoneSize);
    public void setToCollide(Collideable c);
    public boolean hasCollided();
}
