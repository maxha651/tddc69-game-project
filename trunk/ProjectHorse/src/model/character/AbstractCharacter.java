package model.character;

import model.CollideableObject;
import model.GameModel;
import model.background.EngineParticle;
import model.background.Projectile;
import model.background.SpacecraftDeathParticle;
import model.properties.Damageable;
import model.spacecraft.Spacecraft;
import model.utility.math.Randomizer;
import model.utility.math.StandardMath;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObjectState;

/**
 * Used for body to NPC and Player classes. Basic functionality shared by its subclasses.
 */
public abstract class AbstractCharacter extends CollideableObject {
   

    //default constants
    final static double spaceFriction = GameModel.DEFAULT_SPACE_FRICTION;
    final static double velocityFloor = GameModel.DEFAULT_VELOCITY_FLOOR;
    final static int DEFAULT_HEALTH = 100;
    final static int DEFAULT_SCORE_YIELD = 10000;
    final static int DEFAULT_MASS = 400;
    final static int DEFAULT_DEATH_PARTICLE_AMOUNT = 100;

    // object's variables
    protected GameModel gameModel;
    protected Spacecraft spacecraft;
    protected int scoreYield = DEFAULT_SCORE_YIELD;
    protected int remainingFireDelay = 0;
    protected int health = DEFAULT_HEALTH;
    protected boolean accelerationRequest = false;
    protected boolean turnLeftRequest = false;
    protected boolean turnRightRequest = false;
    protected boolean fireRequest = false;
    protected int deathParticleAmount = DEFAULT_DEATH_PARTICLE_AMOUNT;

    /**
     * Deaccelerates the abstract character using space friction taken from GameModel
     */
    public void deaccelerate() {
        double velocityLength = StandardMath.pyth(velocityX, velocityY);

        if(velocityLength > velocityFloor){
            this.velocityX *= spaceFriction;
            this.velocityY *= spaceFriction;
        } else {
            this.velocityX = 0;
            this.velocityY = 0;
        }
    }

    /**
     * Accelerates the AbstractCharacter using variables from the spacecraft.
     */
    public void accelerate() {
        double acceleration = spacecraft.getEngine().getAcceleration();
        double maxVelocity = spacecraft.getEngine().getVelocityMax();

        this.velocityX += StandardMath.xPart(acceleration, rotationAngle);
        this.velocityY += StandardMath.yPart(acceleration, rotationAngle);

        double newVelocity = StandardMath.pyth(velocityX, velocityY);

        if (newVelocity > maxVelocity){
            double maxAndNewVelocityRatio = maxVelocity / newVelocity;
            this.velocityX *= maxAndNewVelocityRatio;
            this.velocityY *= maxAndNewVelocityRatio;
        }
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
        remainingFireDelay--;

        if(accelerationRequest){
            accelerate();
            spawnEngineParticle(world);
        }
        else{
            deaccelerate();
        }

    }

    @Override
    public void destroy(World world) {
        super.destroy(world);
        for(int i = 0; i < deathParticleAmount; i++){
            world.addWorldObject(new SpacecraftDeathParticle(this));
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
        if (remainingFireDelay > 0){
            return;
        }
        remainingFireDelay = spacecraft.getFireDelay();
        Coordinate projectileCoord = new Coordinate(this.getCoordinate());
        projectileCoord.setX(projectileCoord.getX() + Math.cos(rotationAngle) * width/2);
        projectileCoord.setY(projectileCoord.getY() + Math.sin(rotationAngle) * height/2);
        Projectile projectile = new Projectile(spacecraft.getWeapon(), projectileCoord, this.rotationAngle, new ZoneCoordinate(this.zoneCoordinate), this);

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
      public void setToCollide(CollideableObject object) {
        velocityX *= 0.5;
        velocityY *= 0.5;

        if(Damageable.class.isAssignableFrom(object.getClass())){
            ((Damageable) object).doDamage(10);
        }

    }

    // getters and setters
    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public void setSpacecraft(Spacecraft spacecraft) {
        this.spacecraft = spacecraft;
    }

    public void setAccelerationRequest(boolean accelerationRequest) {
        this.accelerationRequest = accelerationRequest;
    }

    public void setTurnLeftRequest(boolean turnLeftRequest) {
        this.turnLeftRequest = turnLeftRequest;
    }

    public void setTurnRightRequest(boolean turnRightRequest) {
        this.turnRightRequest = turnRightRequest;
    }

    public void setFireRequest(boolean fireRequest) {
        this.fireRequest = fireRequest;
    }

    public int getScoreYield() {
        return scoreYield;
    }

    public int getHealth() {
        return health;
    }
}