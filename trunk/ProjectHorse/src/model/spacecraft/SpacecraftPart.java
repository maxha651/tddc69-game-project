package model.spacecraft;

import model.interfaces.Collideable;
import model.items.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public abstract class SpacecraftPart extends Item {
    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    double height, width;
}
