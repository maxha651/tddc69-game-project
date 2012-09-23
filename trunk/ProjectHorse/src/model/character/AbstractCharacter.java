package model.character;

import model.world.WorldObject;

public abstract class AbstractCharacter extends WorldObject {
    public abstract void updatePosition(boolean accelerate);

}