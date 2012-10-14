package model.background;

import model.MoveableObject;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */
public class MoveableBackgroundObject extends MoveableObject {

    @Override
    public double getBoundingWidth() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getBoundingHeight() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
