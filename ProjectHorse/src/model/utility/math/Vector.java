package model.utility.math;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-23
 * Time: 00:41
 * To change this template use File | Settings | File Templates.
 */
public class Vector {

    //length of vector
    private double length;

    //angle from 12 o' clock (0 is from origo of clock to twelve)
    private double angle;

    public Vector(double length, double angle){
        this.length = length;
        this.angle = angle;
    }

    public Vector(){
        this.length = 1;
        this.angle = 0;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
