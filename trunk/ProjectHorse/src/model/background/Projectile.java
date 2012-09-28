package model.background;

import model.interfaces.Boundable;
import model.interfaces.Collideable;
import model.spacecraft.Weapon;
import model.utility.enums.ProjectileType;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-23
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public class Projectile extends MoveableBackgroundObject implements Collideable, Boundable{
    int minDamage, maxDamage;
    double boundingWidth = 10, boundingHeight = 10;
    ProjectileType pt;
    boolean impact;

    public Projectile(Weapon w, Coordinate coordinate, double angle, ZoneCoordinate zoneCoordinate) {
        this.pt = w.getProjectileType();
        this.minDamage = w.getMinDamage();
        this.maxDamage = w.getMaxDamage();
        this.impact = false;
        this.velocityX = model.utility.math.StandardMath.xPart(w.getAbsVelocity(), this.rotationAngle);
        this.velocityY = model.utility.math.StandardMath.yPart(w.getAbsVelocity(), this.rotationAngle);
        this.coordinate = coordinate;
        this.zoneCoordinate = zoneCoordinate;
    }

    @Override
    public boolean collidesWith(Rectangle r) {

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void impact(){
        impact = true;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) this.coordinate.getX(), (int) this.coordinate.getY(), (int) boundingWidth, (int) boundingHeight);
    }
}
