package model.properties;


public interface Collideable extends Moveable, Boundable{
    public void setToCollide(Collideable c);
    public boolean hasCollided();
    public int getMass();
}
