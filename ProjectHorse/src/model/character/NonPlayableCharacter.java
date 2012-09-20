package model.character;


import model.interfaces.Collideable;
import model.interfaces.Moveable;

import java.awt.*;

public class NonPlayableCharacter extends AbstractCharacter implements Moveable, Collideable {
    @Override
    public boolean collidesWith(Rectangle r) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
