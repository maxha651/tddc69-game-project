package model.background;

import model.MoveableObject;
import model.properties.Collideable;
import model.properties.Damageable;
import model.spacecraft.parts.Weapon;
import model.spacecraft.parts.types.projectile.ProjectileType;
import model.spacecraft.parts.types.weapon.WeaponType;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObjectState;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-23
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public class Projectile extends MoveableObject implements Collideable{

    ProjectileType pt;

    int tick = 0;
    boolean hasCollided = false;

    public Projectile(Weapon weapon, Coordinate coordinate, double angle, ZoneCoordinate zoneCoordinate) {
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
    public void setToCollide(Collideable c) {
        if(Damageable.class.isAssignableFrom(c.getClass())){
            ((Damageable) c).doDamage(pt.getDamage());
        }

        hasCollided = true;
        this.state = WorldObjectState.DEAD;
    }

    @Override
    public void updatePosition(double size){
        super.updatePosition(size);
        if(tick < 125){
            this.tick++;
        } else {
            this.setState(WorldObjectState.REMOVE);
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
