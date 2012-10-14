package model.properties;


public interface Collideable extends Moveable, Boundable{
    public boolean hasCollided();
    public int getMass();
}
