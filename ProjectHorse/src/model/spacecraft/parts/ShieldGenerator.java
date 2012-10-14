package model.spacecraft.parts;

import model.properties.Damageable;
import model.spacecraft.parts.types.shield.NoShield;
import model.spacecraft.parts.types.shield.ShieldType;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */
public class ShieldGenerator extends SpacecraftPart {
    ShieldType shieldType;
    int currentShieldStrength;

    public ShieldGenerator() {
        shieldType = new NoShield();
    }

    public ShieldGenerator(ShieldType shieldType) {
        this.shieldType = shieldType;
    }

    public void tick(){
        if(currentShieldStrength < shieldType.getShieldStrength()){
            currentShieldStrength += shieldType.getShieldRechargeRate();
        }
    }

    public int getPiercingDamage(){
        if (currentShieldStrength < 0){
            int piercingDamage = currentShieldStrength;
            currentShieldStrength = 0;
            return piercingDamage;
        }
        else {
            return 0;
        }
    }

}
