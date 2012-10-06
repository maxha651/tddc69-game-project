package model.interfaces;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-21
 * Time: 01:27
 * To change this template use File | Settings | File Templates.
 */
public interface Boundable {

    Rectangle getBounds();
    public double getBoundingWidth();
    public double getBoundingHeight();
}
