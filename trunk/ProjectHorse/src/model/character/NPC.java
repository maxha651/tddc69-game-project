package model.character;


import model.CollideableObject;
import model.background.Projectile;
import model.properties.Boundable;
import model.properties.Collideable;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

/**
 * This class is not being used. NPC is NOT finished.
 */
public class NPC extends AbstractCharacter implements Collideable, Boundable {

	/**
	 * Do not use.
	 */
    public NPC(){
        System.err.println("The NPC is not allowed for used. It is not finished yet.");
        System.exit(0);
        this.setSpacecraft(new Spacecraft());
        this.coordinate = new Coordinate(0,0);
        this.zoneCoordinate = new ZoneCoordinate(0,0);

        mass = (int) (spacecraft.getHull().getHeight() * spacecraft.getHull().getWidth());
    }

    public Projectile fire(){
        return new Projectile(spacecraft.getWeapon1(), new Coordinate(this.getCoordinate()), this.rotationAngle, new ZoneCoordinate(this.zoneCoordinate), this);
    }

    @Override
    public boolean hasCollided() {
        return false;
    }

    @Override
    public int getMass() {
        return mass;
    }

    public void updateAI(){

    }
}
