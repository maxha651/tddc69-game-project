package model.spacecraft.parts.types.projectile;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectileType{
    public int getDamage();
    public Color getColor();
    public int getBoundingWidth();
    public int getBoundingHeight();
}
