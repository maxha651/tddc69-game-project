package model.interfaces;


import java.awt.*;

public interface Collideable extends Moveable{
    public boolean collidesWith(Collideable c, double zoneSize);
}
