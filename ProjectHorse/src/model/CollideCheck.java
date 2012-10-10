package model;

import model.interfaces.Collideable;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-09
 * Time: 08:33
 * To change this template use File | Settings | File Templates.
 */
public class CollideCheck {
    public static boolean isColliding(Collideable c1, Collideable c2, double zoneSize) {
        ZoneCoordinate c1ZoneCoord = new ZoneCoordinate(c1.getZoneCoordinate());
        Coordinate c1Coord = new Coordinate(c1.getCoordinate());

        ZoneCoordinate c2ZoneCoord = new ZoneCoordinate(c2.getZoneCoordinate());
        Coordinate c2Coord = new Coordinate(c2.getCoordinate());

        if(!c2ZoneCoord.equals(c1ZoneCoord)){
            double yZoneDiff = (c2ZoneCoord.getY() - c1ZoneCoord.getY()) * zoneSize;
            double xZoneDiff = (c2ZoneCoord.getX() - c1ZoneCoord.getX()) * zoneSize;

            c1Coord.setY(c1Coord.getY() + yZoneDiff);
            c1Coord.setX(c1Coord.getX() + xZoneDiff);
        }

        double xDiff = Math.abs(c2Coord.getX() - c1Coord.getX());
        double yDiff = Math.abs(c2Coord.getY() - c1Coord.getY());
        double allowedXDiff = (c1.getBoundingWidth() + c2.getBoundingWidth())/2;
        double allowedYDiff = (c1.getBoundingHeight() + c2.getBoundingHeight())/2;

        if( xDiff < allowedXDiff && yDiff < allowedYDiff){
            unCollide(c1, c2, allowedXDiff - xDiff, allowedYDiff - yDiff);
            return true;
        }

        return false;
    }

    private static void unCollide(Collideable c1, Collideable c2, double xIntersect, double yIntersect){
        Coordinate c1Coord = c1.getCoordinate();
        Coordinate c2Coord = c2.getCoordinate();

        if(xIntersect < yIntersect){
            c1Coord.setX(c1Coord.getX() - xIntersect/2);
            c2Coord.setX(c2Coord.getX() + xIntersect/2);
        }
        else{
            c1Coord.setY(c1Coord.getY() - yIntersect/2);
            c2Coord.setY(c2Coord.getY() + yIntersect/2);
        }
    }
}
