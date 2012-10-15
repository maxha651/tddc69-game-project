package model.background;

import model.CollideableObject;
import model.properties.Collideable;
import model.properties.Damageable;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-29
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class Asteroid extends CollideableObject implements Collideable, Damageable{

    short health = 100;
    boolean hasCollided = false;
    double tempVelocityX;
    double tempVelocityY;
    int mass;
    int damageYield;
    static public int deathParticleAmount = 0;
    static public int redParticleAmount = 0;

    public Asteroid(Coordinate c, ZoneCoordinate z){
        this.width = this.boundingHeight = Randomizer.randomInt(20, 80);
        this.height = this.boundingWidth = this.boundingHeight + Randomizer.randomInt(0, 15);
        this.mass = (int) Math.sqrt(boundingHeight*boundingWidth);
        this.rotationSpeed = (Randomizer.randomInt(0,300) - Randomizer.randomInt(0,300))/5000.0;
        this.velocityX = Randomizer.randomDouble(0, 4) - Randomizer.randomDouble(0, 4);
        this.velocityY = Randomizer.randomDouble(0, 4) - Randomizer.randomDouble(0, 4);
        this.tempVelocityX = velocityX;
        this.tempVelocityY = velocityY;
        this.setRotationAngle(Randomizer.randomDouble(0,10));
        this.damageYield = mass/3;
        this.coordinate = c;
        this.zoneCoordinate = z;

    }

    @Override
    public void update(World world) {
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
    public void updatePosition(double size){
        collidedCheck();
        super.updatePosition(size);
    }

    @Override
    public double getWidth(){
        return boundingWidth;
    }

    @Override
    public double getHeight(){
        return boundingHeight;
    }

    @Override
    public void setToCollide(CollideableObject c) {
        hasCollided = true;

        if(Damageable.class.isAssignableFrom(c.getClass())){
            ((Damageable) c).doDamage(damageYield);
            if(c.getClass() == Projectile.class){
            	c.setState(WorldObjectState.DEAD);
            	return;
            }
        }

        //double massRatio =(double) mass / (double) c.getMass() ; // divides by zero

        tempVelocityX = c.getVelocityX();// * massRatio * 0.7;
        tempVelocityY = c.getVelocityY();// * massRatio * 0.7;
    }

    @Override
    public double getBoundingWidth() {
        return boundingWidth;
    }

    @Override
    public double getBoundingHeight() {
        return boundingHeight;
    }

    @Override
    public boolean hasCollided() {
        return hasCollided;
    }

    @Override
    public int getMass() {
        return mass;
    }

    @Override
    public void doDamage(int amount) {
        health -= amount;
        if(health <= 0){
            this.state = WorldObjectState.DEAD;
        }
    }

    @Override
    public int scoreYield() {
        return 10;
    }

    @Override
    public void destroy(World world){
        super.destroy(world);
        calculateDeathParticleAmount();
        for(int i = 0; i < deathParticleAmount; i++){
            world.addWorldObject(new AsteroidParticle(this));
        }

        /*
        for(int i = 0; i < redParticleAmount; i++){
            world.addWorldObject(new RedAsteroidParticle(this));
        }
        */
    }
    
    public void calculateDeathParticleAmount(){
    	deathParticleAmount = (int) ((width*height/130));
    	redParticleAmount = (int) ((width*height/1000));
    }
}
