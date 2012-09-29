package model.background;

import model.interfaces.Boundable;
import model.interfaces.Collideable;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-29
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class Asteroid extends Projectile {

    public Asteroid(){
        this.boundingHeight = Randomizer.randomInt(20, 60);
        this.boundingWidth = this.boundingHeight + Randomizer.randomInt(0, 20) - Randomizer.randomInt(0, 20);
        this.rotationSpeed = Randomizer.randomInt(0,20) - Randomizer.randomInt(0,20);

    }
}
