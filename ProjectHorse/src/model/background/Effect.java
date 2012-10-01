package model.background;

import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-10-01
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public class Effect extends MoveableBackgroundObject {

    public Effect(Coordinate c, ZoneCoordinate z){
        this.width = Randomizer.randomInt(20, 60);
        this.height = width + Randomizer.randomInt(0, 17) - Randomizer.randomInt(0, 17);
        this.rotationSpeed = (Randomizer.randomInt(0,300) - Randomizer.randomInt(0,300))/3000.0;
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

}
