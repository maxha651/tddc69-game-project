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

    private Engine engine = null;
    private ShieldGenerator shield = null;
    private Cargo cargo = null;
    private Weapon weapon1 = null;
    private Weapon weapon2 = null;
    private Hull hull = null;

    private int x = 0, y = 0;

    private String name;
    private String description;

    public Spacecraft(){

    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setShield(ShieldGenerator shield) {
        this.shield = shield;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public void setWeapon1(Weapon weapon1) {
        this.weapon1 = weapon1;
    }

    public void setWeapon2(Weapon weapon2) {
        this.weapon2 = weapon2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
