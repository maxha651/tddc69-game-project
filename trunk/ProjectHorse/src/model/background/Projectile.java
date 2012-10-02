package model.background;

import model.interfaces.Boundable;
import model.interfaces.Collideable;
import model.spacecraft.Weapon;
import model.utility.enums.ProjectileType;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.WorldObjectState;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-23
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public class Projectile extends AbstractProjectile implements Collideable, Boundable{

    ProjectileType pt;

    int tick = 0;
    double boundingWidth = 15;
    double boundingHeight = 15;

    public Projectile(Weapon w, Coordinate coordinate, double angle, ZoneCoordinate zoneCoordinate) {
        this.rotationAngle = angle;
        this.pt = w.getProjectileType();
        this.minDamage = w.getMinDamage();
        this.maxDamage = w.getMaxDamage();
        this.impact = false;
        this.velocityX = model.utility.math.StandardMath.xPart(w.getAbsVelocity(), this.rotationAngle);
        this.velocityY = model.utility.math.StandardMath.yPart(w.getAbsVelocity(), this.rotationAngle);
        this.coordinate = coordinate;
        this.zoneCoordinate = zoneCoordinate;
    }

    public Projectile() {
        System.out.println("DO NOT USE THIS CONSTRUCTOR -- WHY ARE YOU USING ME!??? :(( (in Projectile)");
    }

    @Override
    public boolean collidesWith(Collideable c, double zoneSize) {
        ZoneCoordinate tempZoneCoord = new ZoneCoordinate(c.getZoneCoordinate());
        Coordinate tempCoord = new Coordinate(c.getCoordinate());

        if(!zoneCoordinate.equals(tempZoneCoord)){
            double yZoneDiff = (zoneCoordinate.getY() - tempZoneCoord.getY()) * zoneSize;
            double xZoneDiff = (zoneCoordinate.getX() - tempZoneCoord.getX()) * zoneSize;

            tempCoord.setY(tempCoord.getY() + yZoneDiff);
            tempCoord.setX(tempCoord.getX() + xZoneDiff);
        }

        double xDiff = Math.abs(coordinate.getX() - tempCoord.getX());
        double yDiff = Math.abs(coordinate.getY() - tempCoord.getY());

        if( xDiff < boundingWidth/2 &&
                yDiff < boundingHeight/2){
            return true;
        }
        else{
            return false;
        }
    }



    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) this.coordinate.getX(), (int) this.coordinate.getY(), (int) boundingWidth, (int) boundingHeight);
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
}
