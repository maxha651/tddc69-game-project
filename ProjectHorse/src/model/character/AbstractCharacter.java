package model.character;

import model.CollideableObject;
import model.GameModel;
import model.MoveableObject;
import model.spacecraft.parts.Engine;
import model.spacecraft.Spacecraft;
import model.utility.math.StandardMath;

/**
 * Used for body to NPC and Player classes. Basic functionality shared by its subclasses.
 */
public abstract class AbstractCharacter extends CollideableObject {
   

    //default constants
    protected Spacecraft spacecraft;
    double spaceFriction = GameModel.DEFAULT_SPACE_FRICTION;
    double velocityFloor = GameModel.DEFAULT_VELOCITY_FLOOR;

    /**
     * Deaccelerates the abstract character using space friction taken from GameModel
     */
    public void deaccelerate() {
        double velocityLength = StandardMath.pyth(velocityX, velocityY);

        if(velocityLength > this.velocityFloor){
            this.velocityX *= this.spaceFriction;
            this.velocityY *= this.spaceFriction;
        } else {
            this.velocityX = 0;
            this.velocityY = 0;
        }
    }

    /**
     * Accelerates the AbstractCharacter using variables from the spacecraft.
     */
    public void accelerate() {
        double acceleration = spacecraft.getEngine().getAcceleration();
        double maxVelocity = spacecraft.getEngine().getVelocityMax();

        this.velocityX += StandardMath.xPart(acceleration, rotationAngle);
        this.velocityY += StandardMath.yPart(acceleration, rotationAngle);

        double newVelocity = StandardMath.pyth(velocityX, velocityY);

        if (newVelocity > maxVelocity){
            double maxAndNewVelocityRatio = maxVelocity / newVelocity;
            this.velocityX *= maxAndNewVelocityRatio;
            this.velocityY *= maxAndNewVelocityRatio;
        }
    }

    //getters and setters
    public void setSpacecraft(Spacecraft spacecraft) {
        this.spacecraft = spacecraft;
    }

    public double getSpaceFriction() {
        return spaceFriction;
    }

    public void setSpaceFriction(double spaceFriction) {
        this.spaceFriction = spaceFriction;
    }

    public double getVelocityFloor() {
        return velocityFloor;
    }

    public void setVelocityFloor(double velocityFloor) {
        this.velocityFloor = velocityFloor;
    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }
}