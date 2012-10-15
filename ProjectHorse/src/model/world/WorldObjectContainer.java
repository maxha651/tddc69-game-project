package model.world;


import model.utility.shape.Coordinate;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class WorldObjectContainer extends ConcurrentLinkedQueue<WorldObject> {
    public WorldObjectContainer() {
    }
}