package model.background;

import model.MoveableObject;

import java.awt.*;

public abstract class MoveableBackgroundObject extends MoveableObject {

    @Override
    public double getBoundingWidth() {
        return 0;  
    }

    @Override
    public double getBoundingHeight() {
        return 0;  
    }
}
