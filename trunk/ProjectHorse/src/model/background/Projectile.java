package model.background;

import model.CollideableObject;
import model.MoveableObject;
import model.character.AbstractCharacter;
import model.character.Player;
import model.properties.Collideable;
import model.properties.Damageable;
import model.spacecraft.parts.Weapon;
import model.spacecraft.parts.types.projectile.ProjectileType;
import model.spacecraft.parts.types.weapon.WeaponType;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObjectState;

/**
 * The projectile class is used for all projectiles in the world, such as laser beams,
 * missiles, gun shots etc. 
 * 
 * Is removed on collision <=> "health = 0"
 */
public class Projectile extends CollideableObject implements Collideable{

	//default declarations
    private ProjectileType pt;
    public AbstractCharacter owner;
    private int tick = 0;
    private boolean hasCollided = false;
    
    static private int deathParticleAmount = 5;

    /** 
     * Default constructor for a projectile.
     * @param weapon
     * @param coordinate
     * @param angle
     * @param zoneCoordinate
     * @param player
     */
    public Projectile(Weapon weapon, Coordinate coordinate, double angle, ZoneCoordinate zoneCoordinate, AbstractCharacter player) {
        owner = player;
    	WeaponType wt = weapon.getWeaponType();
        this.velocityX = Math.cos(angle) * wt.getAbsVelocity();
        this.velocityY = Math.sin(angle) * wt.getAbsVelocity();
        this.pt = weapon.getProjectileType();
        state = WorldObjectState.ALIVE;
        this.rotationAngle = angle;
        this.coordinate = coordinate;
        this.zoneCoordinate = zoneCoordinate;
        this.boundingHeight = pt.getBoundingHeight();
        this.boundingWidth = pt.getBoundingWidth();
    }

    /**
     * Do NOT use this constructor.
     */
    private Projectile() {
        System.out.println("DO NOT USE THIS CONSTRUCTOR -- WHY ARE YOU USING ME!??? :(( (in Projectile)");
        System.exit(1);
    }

    @Override
    public void setToCollide(CollideableObject object) {
    	if(object == owner){
    		return;
    	}
    	
    	if(Damageable.class.isAssignableFrom(object.getClass())){
            ((Damageable) object).doDamage(pt.getDamage());
            if(object.getClass() != Player.class){
            	if(owner.getClass() == Player.class){
            		((Player) owner).addScore(((Damageable) object).scoreYield());
            	}
            }
        }

        hasCollided = true;
        this.state = WorldObjectState.DEAD;
    }

    @Override
    public void update(World world){
        super.update(world);

        collisionCheck(world);
        
        if(tick < 125){
            this.tick++;
        } else {
            this.setState(WorldObjectState.DEAD);
        }
    }

    @Override
    public boolean hasCollided() {
        return hasCollided;
    }

    @Override
    public int getMass() {
        return 0; // massless
    }
    
    @Override
    public void destroy(World world){
    	super.destroy(world);

    	//spawn death particles
        for(int i = 0; i < deathParticleAmount; i++){
          world.addWorldObject(new RedProjectileDeathParticle(this));
        }
    }
}
