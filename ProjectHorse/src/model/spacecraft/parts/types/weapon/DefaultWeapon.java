package model.spacecraft.parts.types.weapon;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 12:54
 * To change this template use File | Settings | File Templates.
 */
public class DefaultWeapon implements WeaponType {

    double absVelocity = 17;
    int fireDelay = 12;

    @Override
    public double getAbsVelocity() {
        return absVelocity;
    }

    @Override
    public int getFireDelay() {
        return fireDelay;
    }
}
