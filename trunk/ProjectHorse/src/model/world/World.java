package model.world;

import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;

import java.awt.*;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-26
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class World {
    Coordinate zonesRelativeOrigo = new Coordinate(0.0, 0.0);

    int numberOfLevels;
    double size;
    HashMap<ZoneCoordinate, Zone> zones;

    public int getNumberOfWorldObjects() {
        return numberOfWorldObjects;
    }

    int numberOfWorldObjects = 0;

    public World(int numberOfLevels, double size){
        this.numberOfLevels = numberOfLevels;
        this.size = size;
        this.zones = new HashMap<ZoneCoordinate, Zone>();
        // creates 9 zones around origo to start with
        createZones();
    }

    private Zone getZone(ZoneCoordinate coordinate){
        Zone zone = zones.get(coordinate);

        if (zone == null){
            zones.put(coordinate, new Zone(zonesRelativeOrigo, size, numberOfLevels));
        }
        return zones.get(coordinate);
    }



    public void addWorldObject(WorldObject worldObject){
        ZoneCoordinate zoneCoord = worldObject.getZoneCoordinate();
        Zone zone = getZone(zoneCoord);
        zone.add(worldObject);
        numberOfWorldObjects++;
    }

    public WorldObjectContainer getAllObjectsInArea(Coordinate start, Coordinate stop){
        //TEmporary
        WorldObjectContainer worldObjects = new WorldObjectContainer();

        for(Zone zone : zones.values()){
            worldObjects.addAll(zone.getWorldObjects());
        }

        return worldObjects;

        /*Rectangle tempRectangle = new Rectangle(rectangle); // FIXA HÄR

        if(rectangle.getMinX() < 0){
            tempRectangle.setSize(0, (int) Math.ceil(size - rectangle.getMinX()));
            tempRectangle.setRect(rectangle);
        }
        if(rectangle.getMinY() < 0){
            tempRectangle.setSize(0, (int) Math.ceil(size - rectangle.getMinX()));
        }*/
    }

    public void update(){ // Temporary, should only update used zones
        WorldObjectContainer worldObjects = new WorldObjectContainer();

        for(Zone zone : zones.values()){
            worldObjects.addAll(zone.removeObjectsToReinsert());
        }

        for(WorldObject object : worldObjects){
            addWorldObject(object);
        }
    }

    private void createZones(){
        zones.put(new ZoneCoordinate(-1, -1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(0,-1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(1,-1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(-1,0), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(0,0), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(1, 0), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(-1,1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(0,1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
        zones.put(new ZoneCoordinate(1,1), new Zone(zonesRelativeOrigo, size, numberOfLevels));
    }

    private ZoneCoordinate toZoneCoordinate(Coordinate coordinate){

        System.out.println("before X: " + coordinate.getX() + " Y: " + coordinate.getY());

        int tempX;
        int tempY;

        tempX = (int) Math.floor(coordinate.getX() / size);
        tempY = (int) Math.floor(coordinate.getY() / size);

        System.out.println("after X: " + tempX + " Y: " + tempY);

        return new ZoneCoordinate(tempX, tempY);
    }
}
