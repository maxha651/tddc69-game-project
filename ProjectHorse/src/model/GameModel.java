package model;


import model.background.AbstractProjectile;
import model.background.Asteroid;
import model.background.Projectile;
import model.character.Player;
import model.utility.math.Randomizer;
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
    public long tick = 0;

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
    LinkedList<AbstractProjectile> projectiles = new LinkedList<AbstractProjectile>();

    //object controllers
    public static final double DEFAULT_VELOCITY_FLOOR = 0.2;
    public static final double DEFAULT_SPACE_FRICTION = 0.95;
    public static final double ZONE_SIZE = 1000;
    public static final int ZONE_UPDATE_SPAN = 5;
    public static final int ASTEROID_SPAWN_RATE = 2;

    World world = new World(0, ZONE_SIZE);

    //graphics controllers

    //other
    public static final String VERSION = "1.0a";
    public int numberOfWorldObjects = 0;
    public long updateTime = 0;

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

        long start = System.currentTimeMillis();

        numberOfWorldObjects = world.getNumberOfWorldObjects();
        tick++;

        //add collision checks and method for returning all moveable objects
        updatePlayer();
        //updateProjectiles();
        updateEnemies();

        // Temporary
        ZoneCoordinate startZoneToUpdate = new ZoneCoordinate(player.getZoneCoordinate());
        startZoneToUpdate.setX(startZoneToUpdate.getX() - ZONE_UPDATE_SPAN);
        startZoneToUpdate.setY(startZoneToUpdate.getY() - ZONE_UPDATE_SPAN);

        ZoneCoordinate stopZoneToUpdate = new ZoneCoordinate(player.getZoneCoordinate());
        stopZoneToUpdate.setX(stopZoneToUpdate.getX() + ZONE_UPDATE_SPAN);
        stopZoneToUpdate.setY(stopZoneToUpdate.getY() + ZONE_UPDATE_SPAN);

        world.update(startZoneToUpdate, stopZoneToUpdate);

        // add new asteroids
        spawnAsteroids();

        if(tick > 8000 && true){ //change true to false
            //to make the game not fuck up the computer if memory leaks
            System.out.println("Exited game to prevent fucked up memory leaks");
            System.out.println("Check in Game Model in tick() to remove");
            System.exit(0);
        }

        updateTime = System.currentTimeMillis() - start;
    }

    public void spawnAsteroids(){
        for(int i = 0; i < ASTEROID_SPAWN_RATE; i++){
            spawnAsteroid();
        }
    }

    public void spawnAsteroid(){
        Coordinate c = new Coordinate(player.getCoordinate()); //make this some other arbitary coordinate .. now spawns on top of player
        ZoneCoordinate z = new ZoneCoordinate(player.getZoneCoordinate());

        int xDifference = 1 - 2*Randomizer.randomInt(0, 1);
        int yDifference = 1 - 2*Randomizer.randomInt(0, 1);

        z.setX(player.getZoneCoordinate().getX() + xDifference*2);
        z.setY(player.getZoneCoordinate().getY() + yDifference*2);

        world.addWorldObject(new Asteroid(c, z));
        projectiles.add(new Asteroid(c, z));
    }

    public void updatePlayer(){


        if(turnLeftRequest){
            player.rotateLeft(Math.toRadians(player.getSpacecraft().getEngine().getRotationSpeed()));
        } else if (turnRightRequest) {
            player.rotateRight(Math.toRadians(player.getSpacecraft().getEngine().getRotationSpeed()));
        }
        if (accelerationRequest){
            player.accelerate();
        }
        else{
            player.deaccelerate();
        }

        //player.updatePosition(accelerationRequest, ZONE_SIZE);
        if(fireRequest){
            if (fireDelay <= 0){
                Projectile temp = player.fire();


                projectiles.add(temp);

                world.addWorldObject(temp);

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
        for (AbstractProjectile projectile : projectiles){

            Coordinate upperLeftToCheck, lowerRightToCheck;

            projectile.updatePosition(ZONE_SIZE);

            lowerRightToCheck = projectile.getCoordinate();
            upperLeftToCheck = new Coordinate(lowerRightToCheck.getX() - 10.0, lowerRightToCheck.getY() - 10.0);


            WorldObjectContainer worldObjects = world.getAllObjectsInArea(projectile.getZoneCoordinate(), upperLeftToCheck, lowerRightToCheck);

            if(worldObjects.size() > 1){
                projectile.impact();
                //worldObjects.remove(projectile);
                //System.out.println("impact");
            }
        }
    }

    public WorldObjectContainer getAllObjectsInArea(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        return world.getAllObjectsInArea(zoneCoordinate, start, stop);
    }

    public boolean isGameAlive() {
        return isAlive;
    }
}
