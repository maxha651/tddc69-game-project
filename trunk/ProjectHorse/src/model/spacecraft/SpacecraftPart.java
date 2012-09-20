package model.spacecraft;

import model.LocalObject;
import model.interfaces.Collideable;
import model.items.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public abstract class SpacecraftPart extends Item implements Collideable {
    int width, height;
}
