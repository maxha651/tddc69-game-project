package model.background;

import model.CollideableObject;
import model.properties.Collideable;
import model.properties.Damageable;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.*;

/**
 * Model for an asteroid with velocity, damage yield, health, mass and collision
 */
public class Asteroid extends CollideableObject implements Collideable, Damageable{

	//declarations
    private short health;
    private boolean hasCollided = false;
    private double tempVelocityX;
    private double tempVelocityY;
    private int damageYield;
    private int deathParticleAmount;
    
    //default constants
    private final static int MAX_VELOCITY = 4;
    private final static int ROTATION_SPEED_MAX_SPREAD = 150;
    private final static double ROTATION_SPEED_FACTOR = 0.0002;
    private final static int MAX_SIZE = 95;
    private final static int MIN_SIZE = 20;
    private final static int SIZE_SPREAD = 15;                  //spread between width and height
    private final static int ROTATION_ANGLE_MAX_SPREAD = 10;    //max rotation angle when the asteroid is spawned
    private final static int DAMAGE_YIELD_MASS_DIVISIOR = 20;   //used to calculate the damage yield
    private final static int HEALTH_MASS_DIVISIOR = 60;
    private final static int DEATH_PARTICLE_DIVISIOR = 130;     //used for calculating amount of death particles
    
    /**
     * Standard constructor that takes the zone and coordinate it should spawn at.
     */
    public Asteroid(Coordinate c, ZoneCoordinate z){
        this.width = this.boundingWidth = Randomizer.randomInt(MIN_SIZE, MAX_SIZE);
        this.height = this.boundingHeight = this.width - Randomizer.randomInt(0, SIZE_SPREAD);
        this.mass = (int) Math.sqrt(boundingHeight*boundingWidth);
        this.rotationSpeed = (Randomizer.randomInt(0,ROTATION_SPEED_MAX_SPREAD) - Randomizer.randomInt(0,ROTATION_SPEED_MAX_SPREAD))*ROTATION_SPEED_FACTOR;
        this.velocityX = Randomizer.randomDouble(0, MAX_VELOCITY) - Randomizer.randomDouble(0, MAX_VELOCITY);
        this.velocityY = Randomizer.randomDouble(0, MAX_VELOCITY) - Randomizer.randomDouble(0, MAX_VELOCITY);
        this.tempVelocityX = velocityX;
        this.tempVelocityY = velocityY;
        this.setRotationAngle(Randomizer.randomDouble(0,ROTATION_ANGLE_MAX_SPREAD));
        this.damageYield = mass/DAMAGE_YIELD_MASS_DIVISIOR;
        this.health = (short) ((Math.pow(mass, 2)) / HEALTH_MASS_DIVISIOR); //pow => high mass => high health
        this.coordinate = c;
        this.zoneCoordinate = z;

    }

    @Override
    public void update(World world) {
        collidedCheck();
        super.update(world);  
        collisionCheck(world);
    }

    private void collidedCheck(){
        if(hasCollided){
            velocityX = tempVelocityX;
            velocityY = tempVelocityY;
            hasCollided = false;
        }
    }

    @Override
    public double getWidth(){
        return boundingWidth;
    }

    @Override
    public double getHeight(){
        return boundingHeight;
    }

    /**
     * Sets this object to has collided. Contains collision velocity change and damage yield.
     * If a projectile is the colliding object then remove the projectile.S 
     */
    @Override
    public void setToCollide(CollideableObject object) {
    	hasCollided = true;

        if(Damageable.class.isAssignableFrom(object.getClass())){
            ((Damageable) object).doDamage(damageYield);
            
        }

        if(object.getClass() != Projectile.class){
        	tempVelocityX = object.getVelocityX();
        	tempVelocityY = object.getVelocityY();
        } else {
        	object.setState(WorldObjectState.DEAD);
        }
    }

    @Override
    public boolean hasCollided() {
        return hasCollided;
    }

    @Override
    public int getMass() {
        return mass;
    }

    /**
     * Does damage to this object. If the health is less or equal than 0 then set its state to dead.
     */
    @Override
    public void doDamage(int amount) {
        health -= amount;
        if(health <= 0){
            this.state = WorldObjectState.DEAD;
        }
    }

    /**
     * Returns the score this object yields when killed.
     */
    @Override
    public int scoreYield() {
        return 10;
    }

    /**
     * 
     */
    @Override
    public void destroy(World world){
        super.destroy(world);
        calculateDeathParticleAmount();
        for(int i = 0; i < deathParticleAmount; i++){
            world.addWorldObject(new AsteroidParticle(this));
        }
    }
    
    /**
     * Calculates how many asteroid particles should be spawned upon death.
     */
    protected void calculateDeathParticleAmount(){
    	deathParticleAmount = (int) ((width*height/DEATH_PARTICLE_DIVISIOR));
    }
}
