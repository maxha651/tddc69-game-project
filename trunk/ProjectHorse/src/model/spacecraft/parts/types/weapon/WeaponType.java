package model.spacecraft.parts.types.weapon;

import model.GameModel;
import model.character.AbstractCharacter;
import model.character.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public interface WeaponType {
    public double getAbsVelocity();
    public int getFireDelay();
}
