package model.world;

import model.AbstractGameObject;
import model.utility.shape.Coordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-21
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class WorldObject extends AbstractGameObject {

    protected Coordinate coordinate;

    public Coordinate getCoordinate() {
        return coordinate;
    }

}
