package model.background;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-23
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public class Projectile extends MoveableBackgroundObject {
    int minDamage, maxDamage;
    double speed;

    public Projectile(int minDamage, int maxDamage, double speed) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.speed = speed;
    }
}
