package model.character;

import model.background.Projectile;
import model.properties.Boundable;
import model.properties.Collideable;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

/**
 * Abstraction of coordination in 2D space, velocity, face angle and requests of
 * 1 player. 
 */
public class Player extends AbstractCharacter implements Collideable, Boundable{

    int mass;
    public boolean accelerationRequest = false;
    public boolean turnLeftRequest = false;
    public boolean turnRightRequest = false;
    public boolean fireRequest = false;

    /**
     * Standard constructor that initializes 1 player.
     */
    public Player(){
        this.setSpacecraft(new Spacecraft());
        this.coordinate = new Coordinate(0,0);
        this.zoneCoordinate = new ZoneCoordinate(0,0);
        this.width = spacecraft.getBounds().getWidth();
        this.height = spacecraft.getBounds().getHeight();
        this.mass = 400;
    }

    /**
     * Creates a projectile and gives it velocity and position according to player face angle and player weapon.
     */
    public Projectile fire(){
        Coordinate projectileCoord = new Coordinate(this.getCoordinate());
        projectileCoord.setX(projectileCoord.getX() + Math.cos(rotationAngle) * width/2);
        projectileCoord.setY(projectileCoord.getY() + Math.sin(rotationAngle) * height/2);
        return new Projectile(spacecraft.getWeapon1(), projectileCoord, this.rotationAngle, new ZoneCoordinate(this.zoneCoordinate), this);
    }
    
    /**
     * Resets this player to default player. 
     */
    public void reset(){
    	this.setSpacecraft(new Spacecraft());
        this.coordinate = new Coordinate(0,0);
        this.zoneCoordinate = new ZoneCoordinate(0,0);
        this.width = spacecraft.getBounds().getWidth();
        this.height = spacecraft.getBounds().getHeight();
        this.mass = 400;
    }
    //getters and setters
    @Override
    public void setToCollide(Collideable c) {
        return;
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
        return width;
    }

    @Override
    public double getBoundingHeight() {
        return height;
    }
}
