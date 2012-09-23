package model.spacecraft;

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

    public Weapon(int minDamage, int maxDamage, double speed, int energyCost) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.speed = speed;
        this.energyCost = energyCost;
    }


}