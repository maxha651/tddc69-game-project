package model.utility.shape;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-26
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class ZoneCoordinate implements Comparable{

    int x;
    int y;

    public ZoneCoordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public ZoneCoordinate (ZoneCoordinate zoneCoordinate){
        this.x = zoneCoordinate.getX();
        this.y = zoneCoordinate.getY();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZoneCoordinate)) return false;

        ZoneCoordinate that = (ZoneCoordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (!(o instanceof ZoneCoordinate)) throw new ClassCastException(o.getClass().toString());

        ZoneCoordinate that = (ZoneCoordinate) o;

        if (x != that.x) {
            return x - that.x;
        }
        else{
            return y - that.y;
        }
    }
}
