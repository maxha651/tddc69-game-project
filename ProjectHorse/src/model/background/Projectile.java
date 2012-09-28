package model.background;

import model.interfaces.Collideable;
import model.spacecraft.Weapon;
import model.utility.enums.ProjectileType;
import model.utility.shape.Coordinate;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-23
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public class Projectile extends MoveableBackgroundObject implements Collideable{
    int minDamage, maxDamage;
    ProjectileType pt;
    boolean impact;

    public Projectile(Weapon w, Coordinate coordinate, double angle) {
        this.pt = w.getProjectileType();
        this.minDamage = w.getMinDamage();
        this.maxDamage = w.getMaxDamage();
        this.impact = false;
        this.velocityX = model.utility.math.StandardMath.xPart(w.getAbsVelocity(), this.rotationAngle);
        this.velocityY = model.utility.math.StandardMath.yPart(w.getAbsVelocity(), this.rotationAngle);
        this.coordinate = coordinate;
    }

    @Override
    public boolean collidesWith(Rectangle r) {

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void impact(){
        impact = true;
    }
}
