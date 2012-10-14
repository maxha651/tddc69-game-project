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
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-23
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public class Projectile extends CollideableObject implements Collideable{

    ProjectileType pt;
    AbstractCharacter owner;
    
    int tick = 0;
    boolean hasCollided = false;

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

    public Projectile() {
        System.out.println("DO NOT USE THIS CONSTRUCTOR -- WHY ARE YOU USING ME!??? :(( (in Projectile)");
    }

    @Override
    public void setToCollide(CollideableObject c) {
    	if(c == owner){
    		return;
    	}
    	
    	if(Damageable.class.isAssignableFrom(c.getClass())){
            ((Damageable) c).doDamage(pt.getDamage());
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
}
