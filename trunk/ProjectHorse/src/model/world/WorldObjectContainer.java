package model.world;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The container used when handling WorldObjects
 *
 * Is Thread-safe
 */
public class WorldObjectContainer extends ConcurrentLinkedQueue<WorldObject> {
    public WorldObjectContainer() {
    }
}