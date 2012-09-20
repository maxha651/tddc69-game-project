package model.spacecraft;

import model.MoveableObject;
import model.interfaces.Collideable;
import model.interfaces.Moveable;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:25
 * To change this template use File | Settings | File Templates.
 */
public class Spacecraft extends MoveableObject implements Collideable, Moveable {
    LinkedList<SpacecraftPart> parts;

    public Spacecraft(){

    }
}
