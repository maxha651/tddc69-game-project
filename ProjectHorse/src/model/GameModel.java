package model;


import model.background.Projectile;
import model.character.Player;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObjectContainer;

import java.util.LinkedList;
import java.util.Observable;

public class GameModel extends Observable {

    //game controllers
    boolean isAlive = true;
    int seed;
    long tick = 0;

    //player controllers
    Player player;
    public boolean accelerationRequest = false;
    public boolean turnLeftRequest = false;
    public boolean turnRightRequest = false;
    public boolean fireRequest = false;

    //AI controllers

    // Projectile controllers
    private int fireDelayDefault = 10;
    private int fireDelay = 0;
    LinkedList<Projectile> projectiles = new LinkedList<Projectile>();

    //object controllers
    public static final double DEFAULT_VELOCITY_FLOOR = 0.2;
    public static final double DEFAULT_SPACE_FRICTION = 0.99;
    public static final double ZONE_SIZE = 1000;

    World world = new World(0, ZONE_SIZE);

    //graphics controllers

    //other
    public static final String VERSION = "1.0a";

    public GameModel(){
        this(1);
    }

    public GameModel(int seed){
        this.seed = seed;
        player = new Player();
        world.addWorldObject(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public void tick(){
        tick++;

        //add collision checks and method for returning all moveable objects

        world.update();
        updatePlayer();
        updateProjectiles();
        updateEnemies();
    }

    public void updatePlayer(){


        if(turnLeftRequest){
            player.rotateLeft(Math.toRadians(player.getSpacecraft().getEngine().getRotationSpeed()));
        } else if (turnRightRequest) {
            player.rotateRight(Math.toRadians(player.getSpacecraft().getEngine().getRotationSpeed()));
        }
        player.updatePosition(accelerationRequest, ZONE_SIZE);
        if(fireRequest){
            if (fireDelay <= 0){
                Projectile temp = player.fire();
                System.out.println("Proj: " + projectiles);
                System.out.println("Temp: " + temp);

                projectiles.add(temp);

                world.addWorldObject(temp); // Error when firing weapon and changing zone y at the same time

                fireDelay = fireDelayDefault;
            }
            else{
                fireDelay--;
            }
        }
    }

    public double getZoneSize(){
        return ZONE_SIZE;
    }

    public void updateEnemies(){

    }

    public void updateProjectiles(){
        for (Projectile projectile : projectiles){
            Coordinate upperLeftToCheck, lowerRightToCheck;

            lowerRightToCheck = projectile.getCoordinate();
            upperLeftToCheck = new Coordinate(lowerRightToCheck.getX() - 10.0, lowerRightToCheck.getY() - 10.0);

            WorldObjectContainer worldObjects = world.getAllObjectsInArea(upperLeftToCheck, lowerRightToCheck);

            if(worldObjects.size() > 1){
                projectile.impact();
                //worldObjects.remove(projectile);
                System.out.println("impact");
            }
        }
    }

    public WorldObjectContainer getAllObjectsInArea(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        return world.getAllObjectsInArea(start, stop);
    }

    public boolean isGameAlive() {
        return isAlive;
    }
}
