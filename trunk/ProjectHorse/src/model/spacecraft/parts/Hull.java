package model.spacecraft.parts;

import model.spacecraft.parts.types.hull.DefaultHull;
import model.spacecraft.parts.types.hull.HullType;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-21
 * Time: 00:27
 * To change this template use File | Settings | File Templates.
 */
public class Hull extends SpacecraftPart {

    HullType hullType;

    public Hull(){
        hullType = new DefaultHull();
        this.height = hullType.getHeight();
        this.width  = hullType.getWidth();
    }

    public Hull(HullType hullType) {
        this.hullType = hullType;
        this.height = hullType.getHeight();
        this.width  = hullType.getWidth();
    }

    public Rectangle getRectangle(){
        return hullType.getRectangle();
    }
}
