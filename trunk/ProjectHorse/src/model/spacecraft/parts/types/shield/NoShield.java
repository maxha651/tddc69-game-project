package model.spacecraft.parts.types.shield;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 14:16
 * To change this template use File | Settings | File Templates.
 */
public class NoShield implements ShieldType {
    @Override
    public int getShieldStrength() {
        return 0;
    }

    @Override
    public int getShieldRechargeRate() {
        return 0;
    }
}
