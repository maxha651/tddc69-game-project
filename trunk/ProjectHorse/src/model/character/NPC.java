package model.character;


import model.background.Projectile;
import model.properties.Boundable;
import model.properties.Collideable;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

public class NPC extends AbstractCharacter implements Collideable, Boundable {

    int mass;

    public NPC(){
        this.setSpacecraft(new Spacecraft());
        this.coordinate = new Coordinate(0,0);
        this.zoneCoordinate = new ZoneCoordinate(0,0);

        mass = (int) (spacecraft.getHull().getHeight() * spacecraft.getHull().getWidth());
    }

    public Projectile fire(){
        return new Projectile(spacecraft.getWeapon1(), new Coordinate(this.getCoordinate()), this.rotationAngle, new ZoneCoordinate(this.zoneCoordinate));
    }

    @Override
    public void setToCollide(Collideable c) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasCollided() {
        return false;
    }

    @Override
    public int getMass() {
        return mass;
    }

    @Override
    public double getBoundingWidth() {
        return boundingWidth;
    }

    @Override
    public double getBoundingHeight() {
        return boundingHeight;
    }

    public void updateAI(){

    }
}
