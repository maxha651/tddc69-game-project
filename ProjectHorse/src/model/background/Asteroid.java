package model.background;

import model.interfaces.Boundable;
import model.interfaces.Collideable;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-29
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class Asteroid extends AbstractProjectile implements Collideable{

    public Asteroid(Coordinate c, ZoneCoordinate z){
        this.boundingHeight = Randomizer.randomInt(20, 80);
        this.boundingWidth = this.boundingHeight + Randomizer.randomInt(0, 15) - Randomizer.randomInt(0, 15);
        this.rotationSpeed = (Randomizer.randomInt(0,300) - Randomizer.randomInt(0,300))/5000.0;
        this.velocityX = Randomizer.randomDouble(0, 4) - Randomizer.randomDouble(0, 4);
        this.velocityY = Randomizer.randomDouble(0, 4) - Randomizer.randomDouble(0, 4);
        this.setRotationAngle(Randomizer.randomDouble(0,10));

        this.coordinate = c;
        this.zoneCoordinate = z;

    }

    @Override
    public void updatePosition(double size){
        super.updatePosition(size);
        this.setRotationAngle(this.getRotationSpeed() + this.getRotationAngle());
    }

    @Override
    public double getWidth(){
        return boundingWidth;
    }

    @Override
    public double getHeight(){
        return boundingHeight;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) this.coordinate.getX(), (int) this.coordinate.getY(), (int) boundingWidth, (int) boundingHeight);
    }

    @Override
    public boolean collidesWith(Collideable c, double zoneSize) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
