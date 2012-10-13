package model.background;

import model.GameModel;
import model.properties.Collideable;
import model.properties.Damageable;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObjectState;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-29
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class Asteroid extends AbstractProjectile implements Collideable, Damageable{

    int health = 1;
    boolean hasCollided = false;
    double tempVelocityX;
    double tempVelocityY;
    int mass;
    public static World w;
    public static int deathParticleAmount = 45;
    public static int redParticleAmount = 5;

    public Asteroid(World w, Coordinate c, ZoneCoordinate z){
        this.w = w;
        this.boundingHeight = Randomizer.randomInt(20, 80);
        this.boundingWidth = this.boundingHeight + Randomizer.randomInt(0, 15) - Randomizer.randomInt(0, 15);
        this.mass = (int) Math.sqrt(boundingHeight*boundingWidth);
        this.rotationSpeed = (Randomizer.randomInt(0,300) - Randomizer.randomInt(0,300))/5000.0;
        this.velocityX = Randomizer.randomDouble(0, 4) - Randomizer.randomDouble(0, 4);
        this.velocityY = Randomizer.randomDouble(0, 4) - Randomizer.randomDouble(0, 4);
        this.tempVelocityX = velocityX;
        this.tempVelocityY = velocityY;
        this.setRotationAngle(Randomizer.randomDouble(0,10));

        this.coordinate = c;
        this.zoneCoordinate = z;

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
        this.setRotationAngle(this.getRotationSpeed() + this.getRotationAngle());
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
    public void setToCollide(Collideable c) {
        hasCollided = true;

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
            this.kill();
        }
    }

    @Override
    public void kill(){
        super.kill();

        for(int i = 0; i < deathParticleAmount; i++){
            this.w.addWorldObject(new AsteroidParticle(this));
        }

        for(int i = 0; i < redParticleAmount; i++){
            this.w.addWorldObject(new RedAsteroidParticle(this));
        }
    }
}