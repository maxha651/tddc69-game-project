package model.world;

import model.utility.shape.Coordinate;

import java.util.LinkedList;

public class WorldObjectContainer extends LinkedList<WorldObject>{

    public WorldObjectContainer() {
    }

    public boolean contains(Coordinate coordinate){
        for(WorldObject object : this){
            if(coordinate.equals(object.getCoordinate())){
                return true;
            }
        }
        return false;
    }

    public WorldObjectContainer get(Coordinate coordinate){
        WorldObjectContainer res = new WorldObjectContainer();

        for(WorldObject object : this){
            if(coordinate.equals(object.getCoordinate())){
                res.add(object);
            }
        }
        return res;
    }
}