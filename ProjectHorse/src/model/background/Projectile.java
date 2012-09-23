package model.background;

import model.utility.shape.Coordinate;

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
    Coordinate coordinate;

    public Projectile(int minDamage, int maxDamage, double speed, Coordinate coordinate) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.speed = speed;
        this.coordinate = coordinate;
    }

    public void update(){

    }

}
