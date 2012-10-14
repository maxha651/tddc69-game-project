package model.utility.shape;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-21
 * Time: 00:45
 * To change this template use File | Settings | File Templates.
 */
public class Coordinate {
    double x, y;

    public Coordinate(){
        this.x = 0.0;
        this.y = 0.0;
    }

    public Coordinate(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Coordinate(Point p){
        this.x = p.x;
        this.y = p.y;
    }

    public Coordinate(Coordinate c){
        this.x = c.x;
        this.y = c.y;
    }

    public void add(Coordinate coordinate){
        x += coordinate.getX();
        y += coordinate.getY();
    }

    public void subtract(Coordinate coordinate){
        x -= coordinate.getX();
        y -= coordinate.getY();
    }

    public void add(double term){
        x += term;
        y += term;
    }

    public void multiply(double factor){
        x *= factor;
        y *= factor;
    }

    public Point getPoint(){
        return new Point((int) x, (int) y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;

        Coordinate that = (Coordinate) o;

        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = x != +0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
