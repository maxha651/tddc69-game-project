package model.spacecraft;

import model.background.Projectile;
import model.utility.enums.ProjectileType;
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
    double absVelocity;
    double energyCost;
    ProjectileType pt;


    public Weapon(int minDamage, int maxDamage, double speed, double energyCost) {
        this.pt = ProjectileType.LASER_BLUE;

        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.absVelocity = speed;
        this.energyCost = energyCost;
    }


    public ProjectileType getProjectileType() {
        return pt;
    }

    public void setProjectileType(ProjectileType pt) {
        this.pt = pt;
    }

    public double getEnergyCost() {
        return energyCost;
    }

    public void setEnergyCost(int energyCost) {
        this.energyCost = energyCost;
    }

    public double getAbsVelocity() {
        return absVelocity;
    }

    public void setAbsVelocity(double absVelocity) {
        this.absVelocity = absVelocity;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

}
