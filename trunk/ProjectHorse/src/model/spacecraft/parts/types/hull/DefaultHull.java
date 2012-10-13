package model.spacecraft.parts.types.hull;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class DefaultHull implements HullType {

    int width = 40;
    int height = 40;

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(width, height);
    }
}
