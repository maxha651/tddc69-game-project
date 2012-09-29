package model.character;

import model.GameModel;
import model.MoveableObject;
import model.spacecraft.Engine;
import model.spacecraft.Spacecraft;
import model.utility.math.StandardMath;

public abstract class AbstractCharacter extends MoveableObject {
    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    protected Spacecraft spacecraft;
    double spaceFriction = GameModel.DEFAULT_SPACE_FRICTION;
    double velocityFloor = GameModel.DEFAULT_VELOCITY_FLOOR;

    private void updateVelocity(boolean accelerate) {
        Engine e = this.spacecraft.getEngine();

        if(accelerate){
            accelerate();
        }

        //space friction
        deaccelerate();
    }

    @Override
    public void updatePosition(double zoneSize) {
        super.updatePosition(zoneSize);
        deaccelerate();
    }

    public void deaccelerate() {
        double velocityLength = StandardMath.pyth(velocityX, velocityY);

        if(velocityLength > 0.1){
            if(velocityLength > this.velocityFloor){
                this.velocityX *= this.spaceFriction;
                this.velocityY *= this.spaceFriction;
            } else {
                this.velocityX = 0;
                this.velocityY = 0;
            }

        }
    }


    public void updatePosition(boolean accelerate, double zoneSize) {
        this.updateVelocity(accelerate);
        super.updatePosition(zoneSize);
    }

    public void accelerate() {
        double acceleration = spacecraft.getEngine().getAcceleration();
        double maxVelocity = spacecraft.getEngine().getVelocityMax();

        if(StandardMath.pyth(velocityX, velocityY) < maxVelocity){
            this.velocityX += StandardMath.xPart(acceleration, rotationAngle);
            this.velocityY += StandardMath.yPart(acceleration, rotationAngle);

            double newVelocity = StandardMath.pyth(velocityX, velocityY);

            if (newVelocity > maxVelocity){
                double maxAndNewVelocityRatio = maxVelocity / newVelocity;
                this.velocityX *= maxAndNewVelocityRatio;
                this.velocityY *= maxAndNewVelocityRatio;
            }
        }
    }


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


}