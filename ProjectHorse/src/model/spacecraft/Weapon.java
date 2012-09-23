package model.spacecraft;

import model.background.Projectile;
import model.utility.shape.Coordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:54
 * To change this template use File | Settings | File Templates.
 */
public class Weapon extends SpacecraftPart {
    int minDamage, maxDamage;
    double speed;
    int energyCost;
    Coordinate coordinate;

    public Weapon(int minDamage, int maxDamage, double speed, int energyCost, Coordinate coordinate) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.speed = speed;
        this.energyCost = energyCost;
        this.coordinate = coordinate;
    }

    public void fire(){
        new Projectile(minDamage, maxDamage, speed, coordinate);
    }

}
