package model.spacecraft.parts;

import model.properties.Damageable;
import model.spacecraft.parts.types.shield.NoShield;
import model.spacecraft.parts.types.shield.ShieldType;

/**
 * Not used at the moment (but can be used).
 */
public class ShieldGenerator extends SpacecraftPart {
    private ShieldType shieldType;
    private int currentShieldStrength;

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
