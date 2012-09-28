package model.character;


import model.background.Projectile;
import model.interfaces.Collideable;
import model.interfaces.Moveable;
import model.spacecraft.Engine;
import model.spacecraft.Spacecraft;
import model.spacecraft.Weapon;
import model.utility.math.StandardMath;
import model.utility.shape.Coordinate;

import java.awt.*;

public class Player extends AbstractCharacter{

    public Player(){
        this.setSpacecraft(new Spacecraft());
        this.coordinate = new Coordinate(0,0);
    }

    public Projectile fire(){
        return new Projectile(spacecraft.getWeapon1(), new Coordinate(this.getCoordinate()), this.rotationAngle);
    }
}
