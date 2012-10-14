package model.character;


import model.CollideableObject;
import model.background.AsteroidParticle;
import model.background.Projectile;
import model.background.RedAsteroidParticle;
import model.background.SpacecraftDeathParticle;
import model.properties.Boundable;
import model.properties.Collideable;
import model.properties.Damageable;
import model.spacecraft.Spacecraft;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObjectState;

/**
 * Abstraction of coordination in 2D space, velocity, face angle and requests of
 * 1 player. 
 */
public class Player extends AbstractCharacter implements Collideable, Boundable, Damageable{

	int score;
    int mass;
    int fireDelay = 0;
    int health = 100;
    public boolean accelerationRequest = false;
    public boolean turnLeftRequest = false;
    public boolean turnRightRequest = false;
    public boolean fireRequest = false;
    public int deathParticleAmount = 100;

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
        this.score = 0;
    }

    @Override
    public void update(World world) {
        super.update(world);

        if(turnLeftRequest){
            rotateLeft(Math.toRadians(getSpacecraft().getEngine().getRotationSpeed()));
        }
        if(turnRightRequest){
            rotateRight(Math.toRadians(getSpacecraft().getEngine().getRotationSpeed()));
        }
        if(fireRequest){
            fire(world);
        }
        fireDelay--;

        if(accelerationRequest){
            accelerate();
        }
        else{
            deaccelerate();
        }
    }

    public int getFireDelay(){
        return spacecraft.getWeapon1().getWeaponType().getFireDelay();
    }

    public void fire(World world){
        if (fireDelay > 0){
            return;
        }
        fireDelay = getFireDelay();
        Coordinate projectileCoord = new Coordinate(this.getCoordinate());
        projectileCoord.setX(projectileCoord.getX() + Math.cos(rotationAngle) * width/2);
        projectileCoord.setY(projectileCoord.getY() + Math.sin(rotationAngle) * height/2);
        Projectile projectile = new Projectile(spacecraft.getWeapon1(), projectileCoord, this.rotationAngle, new ZoneCoordinate(this.zoneCoordinate), this);

        world.addWorldObject(projectile);
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
        this.score = 0;
    }
    
    
    
    //getters and setters
    @Override
    public void setToCollide(CollideableObject c) {
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

    @Override
    public void doDamage(int amount) {
        health -= amount;
        if(health <= 0){
            this.state = WorldObjectState.DEAD;
        }
    }

    @Override
    public void destroy(World world){
        super.destroy(world);
        
        for(int i = 0; i < deathParticleAmount; i++){
            world.addWorldObject(new SpacecraftDeathParticle(this));
        }

    }
}
