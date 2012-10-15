package model.character;


import model.CollideableObject;
import model.GameModel;
import model.background.*;
import model.properties.Boundable;
import model.properties.Collideable;
import model.properties.Damageable;
import model.spacecraft.Spacecraft;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObjectState;

import java.awt.*;

/**
 * Abstraction of coordination in 2D space, velocity, face angle and requests of
 * 1 player. 
 */
public class Player extends AbstractCharacter implements Collideable, Boundable, Damageable{

    //declarations and initialization
    final static int DEFAULT_HEALTH = 100;
    final static int DEFAULT_SCORE_YIELD = 10000;
    int scoreYield = DEFAULT_SCORE_YIELD;
    GameModel gameModel;
	int score = 0;
    int fireDelay = 0;
    int health = DEFAULT_HEALTH;
    public boolean accelerationRequest = false;
    public boolean turnLeftRequest = false;
    public boolean turnRightRequest = false;
    public boolean fireRequest = false;
    public int deathParticleAmount = 100;
    Color color = Color.RED; //not graphical color -- no graphical implementation -- used if needed

    /**
     * Standard constructor that initializes 1 player.
     */
    public Player(GameModel gameModel, Coordinate c, ZoneCoordinate zc){
        this.gameModel = gameModel;
        this.setSpacecraft(new Spacecraft(this));
        this.coordinate = new Coordinate(c);
        this.zoneCoordinate = new ZoneCoordinate(zc);
        this.width = spacecraft.getBounds().getWidth();
        this.height = spacecraft.getBounds().getHeight();
        this.mass = 400;
        this.score = 0;
    }

    /**
     * Updates information about the player.
     * @param world
     */
    @Override
    public void update(World world) {
        super.update(world);
        collisionCheck(world);

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
            spawnEngineParticle(world);
        }
        else{
            deaccelerate();
        }

    }

    /**
     * Spawns 1 engine particle by the engine of the spacecraft.
     * @param world
     */
    public void spawnEngineParticle(World world){
        EngineParticle ep = new EngineParticle(this);
        world.addWorldObject(ep);
    }

    /**
     * Fires a projectile of type taken from the weapon the player uses. Other projectile information is also taken from the weapon.
     */
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
    public void reset(Coordinate c, ZoneCoordinate zc){
    	this.setState(WorldObjectState.ALIVE);
    	this.setSpacecraft(new Spacecraft());
        this.coordinate = new Coordinate(c);
        this.zoneCoordinate = new ZoneCoordinate(zc);
        this.width = spacecraft.getBounds().getWidth();
        this.height = spacecraft.getBounds().getHeight();
        this.mass = (int) Math.sqrt(width*height);
        this.health = DEFAULT_HEALTH;
        this.rotationAngle = Math.toRadians(Randomizer.randomInt(0, 360));
        this.velocityX = 0;
        this.velocityY = 0;
        accelerationRequest = false;
        turnLeftRequest = false;
        turnRightRequest = false;
        fireRequest = false;
    }

    @Override
    public void doDamage(int amount) {
        health -= amount;
        if(health <= 0){
            this.state = WorldObjectState.DEAD;
        }
    }

    /**
     * Destroys the player and then lets the world remove him when the world is "ready".
     * @param world
     */
    @Override
    public void destroy(World world){
        super.destroy(world);
        gameModel.giveScoreToOtherPlayer(this);
        for(int i = 0; i < deathParticleAmount; i++){
            world.addWorldObject(new SpacecraftDeathParticle(this));
        }
    }
    
    //getters and setters
    @Override
    public void setToCollide(CollideableObject object) {
        velocityX *= 0.5;
        velocityY *= 0.5;

        if(Damageable.class.isAssignableFrom(object.getClass())){
            ((Damageable) object).doDamage(10);
        }

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
    public int scoreYield() {
        return scoreYield;
    }

    public void addScore(int i) {
        this.score += i;
    }

    public int getScore(){
        return score;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getFireDelay(){
        return spacecraft.getWeapon1().getWeaponType().getFireDelay();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
