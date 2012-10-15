package model.spacecraft.parts;

import model.spacecraft.parts.types.hull.DefaultHull;
import model.spacecraft.parts.types.hull.HullType;

import java.awt.*;

/**
 * Spacecraft hull. 
 * Not really used at the moment (but can be used).
 */
public class Hull extends SpacecraftPart {

    private HullType hullType;

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
