package model.background;

import model.MoveableObject;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-29
 * Time: 21:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProjectile extends MoveableObject {
    int minDamage, maxDamage;
    // double boundingWidth = 10, boundingHeight = 10; in Collideable instead
    boolean impact;
    public void impact(){
        impact = true;
    }
}
