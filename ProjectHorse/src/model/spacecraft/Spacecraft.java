package model.spacecraft;

import model.LocalObject;
import model.character.Player;
import model.properties.Boundable;
import model.items.Item;
import model.spacecraft.parts.*;

import java.awt.*;

/**
  * A spacecraft is the model of a spacecraft that can then be used in stores, by players or by NPCs
 */
public class Spacecraft extends LocalObject implements Boundable {

    private Engine engine = null;
    private ShieldGenerator shield = null;
    private Cargo cargo = null;
    private Weapon weapon1 = null; // weaponLeft?
    private Weapon weapon2 = null; // weaponRight?
    private Hull hull = null;

    private String name = "Default Spacecraft";
    private String description = "Default description";

    public Spacecraft(){
        //add parts to the spacecraft
        this.hull = new Hull();
        this.engine = new Engine();
        this.weapon1 = new Weapon();
        this.shield = new ShieldGenerator();
    }

    public Spacecraft(Player p){
        this();
        this.weapon1 = new Weapon(p);
    }

    public Rectangle getBounds(){
        //the bounding rectangle of the spacecraft only checks the hull size
        return new Rectangle((int)this.hull.getWidth(), (int)this.hull.getHeight());

    }

    @Override
    public double getBoundingWidth() {
        return hull.getWidth();
    }

    @Override
    public double getBoundingHeight() {
        return hull.getHeight();
    }

    public double getWidth(){
        return this.hull.getWidth();
    }

    public double getHeight(){
        return this.hull.getHeight();
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

    public Engine getEngine() {
        return engine;
    }

    public ShieldGenerator getShield() {
        return shield;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Weapon getWeapon1() {
        return weapon1;
    }

    public Weapon getWeapon2() {
        return weapon2;
    }

    public Hull getHull() {
        return hull;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
