package model.utility.shape;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-26
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class ZoneCoordinate {

    int x;
    int y;

    public ZoneCoordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
