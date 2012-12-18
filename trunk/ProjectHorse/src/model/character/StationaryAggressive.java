package model.character;

import model.CollideableObject;
import model.background.Asteroid;
import model.utility.shape.Coordinate;
import model.world.World;
import model.world.WorldObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Needs overall fix. Make more standard behaviour from which all others derive.
 * Especially fix inSights function to calculate target area.
 */
public class StationaryAggressive implements Behaviour {

    final static int RADIUS_OF_AI_SEARCH = 500;

    public StationaryAggressive() {
    }

    @Override
    public void update(World world, NPC npc) {
        Collection<WorldObject> nearbyObjects;
        Coordinate targetCoord = npc.getCoordinate();

        npc.accelerationRequest = false;

        nearbyObjects = world.getAllObjectsInArea(npc.getZoneCoordinate(),
                new Coordinate(targetCoord.getX() -RADIUS_OF_AI_SEARCH, targetCoord.getY() -RADIUS_OF_AI_SEARCH),
                new Coordinate(targetCoord.getX() +RADIUS_OF_AI_SEARCH, targetCoord.getY() +RADIUS_OF_AI_SEARCH));

        for(WorldObject worldObject : nearbyObjects){
            if(worldObject == npc || worldObject.getClass() != Asteroid.class){
                nearbyObjects.remove(worldObject);
            }
        }

        if(nearbyObjects.isEmpty()){
            npc.turnLeftRequest = npc.turnRightRequest = npc.fireRequest = false;
            return;
        }

        WorldObject nearestObject = getNearestObject(npc, nearbyObjects, world.getZoneSize());

        rotateTowardsTarget(npc, nearestObject, world.getZoneSize());
        npc.fireRequest = isInSights(npc, nearestObject, world.getZoneSize());
    }

    private WorldObject getNearestObject(NPC npc, Collection<WorldObject> objects, double zoneSize){
        if(objects.isEmpty()){
            return null;
        }

        // Change to a Map
        WorldObject worldObjectArray[] = new WorldObject[objects.size()];
        objects.toArray(worldObjectArray);
        double distanceFromTarget[] = new double[objects.size()];

        for (int idx = 0; idx < worldObjectArray.length; idx++) {
            WorldObject worldObject = worldObjectArray[idx];
            distanceFromTarget[idx] = npc.getDistanceTo(worldObject, zoneSize);
        }

        int nearestObjectIndex = 0;
        for (int idx = 1; idx < distanceFromTarget.length; idx++) {
            if(distanceFromTarget[idx] < distanceFromTarget[nearestObjectIndex]){
                nearestObjectIndex = idx;
            }
        }

        return worldObjectArray[nearestObjectIndex];
    }

    private void rotateTowardsTarget(NPC npc, WorldObject target, double zoneSize){
        double npcRotation = npc.getRotationAngle();
        double angleToTarget = npc.getAngleTo(target, zoneSize);

        if(npcRotation == angleToTarget){
            npc.turnLeftRequest = npc.turnRightRequest = false;
            return;
        }

        if(npcRotation >= Math.PI){
            if(angleToTarget > npcRotation || angleToTarget < npcRotation - Math.PI){
                npc.turnLeftRequest = false;
                npc.turnRightRequest = true;
            } else{
                npc.turnLeftRequest = true;
                npc.turnRightRequest = false;
            }
        } else{
            if(angleToTarget < npcRotation || angleToTarget > npcRotation + Math.PI){
                npc.turnLeftRequest = true;
                npc.turnRightRequest = false;
            } else {
                npc.turnLeftRequest = false;
                npc.turnRightRequest = true;
            }
        }
    }

    private boolean isInSights(NPC npc, WorldObject target, double zoneSize){
        double angle = npc.getAngleTo(target, zoneSize);

        double angleDiff = angle - (npc.getRotationAngle());

        return Math.abs(angleDiff) < 0.5;
    }
}
