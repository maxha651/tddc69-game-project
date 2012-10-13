package model.spacecraft.parts.types.projectile;

import model.utility.math.Randomizer;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class ProjectileBlue implements ProjectileType {
    int boundingWidth = 10;
    int boundingHeight = 10;
    int minDamage = 10;
    int maxDamage = 20;
    Color projectileColor = Color.BLUE;

    @Override
    public int getDamage() {
        return Randomizer.randomInt(minDamage, maxDamage);
    }

    @Override
    public Color getColor() {
        return projectileColor;
    }

    @Override
    public int getBoundingWidth() {
        return boundingWidth;
    }

    @Override
    public int getBoundingHeight() {
        return boundingHeight;
    }
}
