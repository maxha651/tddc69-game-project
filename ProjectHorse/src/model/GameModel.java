package model;


import model.background.AbstractProjectile;
import model.background.Asteroid;
import model.background.EngineParticle;
import model.background.Projectile;
import model.character.Player;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.AsteroidSpawner;
import model.world.World;
import model.world.WorldObject;
import model.world.WorldObjectContainer;

import java.util.LinkedList;
import java.util.Observable;

public class GameModel extends Observable {

    //game controllers
    boolean alive = true;
    int seed;
    public long tick = 0;

    //player controllers
    Player player1;
    Player player2;
    
    // Projectile controllers
    private int fireDelayDefault = 10;
    private int fireDelay = 0;

    //object controllers
    public static final double DEFAULT_VELOCITY_FLOOR = 0.2;
    public static final double DEFAULT_SPACE_FRICTION = 0.98;
    public static final double ZONE_SIZE = 1000;
    public static final int COLLIDING_CHECK_DISTANCE = 50; // determines how faraway objects to check for collision
    public static final int ZONE_UPDATE_SPAN = 5;    //the spawn of how many zones are loaded away from player
    public static final int asteroidSpawnRate = 2; //how many asteroids to spawn per tick

    World world;

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
        this.initialize();
    }

    public int numberOfZones(){
        return world.getNumOfZones();
    }

    public Player getPlayer() {
        return player1;
    }

    public void setPlayer(Player player) {
        this.player1 = player;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
    
    /**
     * Restarts the game model.
     */
    public void reset(){
    	world = new World(ZONE_SIZE);
    	if(player1 == null){
            player1 = new Player();
    	} else {
    		player1.reset();
    	}
    	
    	if(player2 == null){
            player2 = new Player();
    	} else {
    		player2.reset();
    	}
        world.addWorldObject(player1);
        world.addWorldObject(player2);
        world.addWorldObjectSpawner(new AsteroidSpawner());
        numberOfWorldObjects = 0;
        tick = 0;
    }
    
    public void initialize(){
    	world = new World(ZONE_SIZE);

    	player1 = new Player();
    	player2 = new Player();

    	world.addWorldObject(player1);
    	world.addWorldObject(player2);
    	world.addWorldObjectSpawner(new AsteroidSpawner());
    	numberOfWorldObjects = 0;
    	tick = 0;
    }

    public void tick(){

        long start = System.currentTimeMillis();

        numberOfWorldObjects = world.getNumberOfWorldObjects();
        tick++;

        //add collision checks and method for returning all moveable objects
        updatePlayers();
        //updateProjectiles();
        updateEnemies();

        // Temporary
        ZoneCoordinate startZoneToUpdate = new ZoneCoordinate(player1.getZoneCoordinate());
        startZoneToUpdate.setX(startZoneToUpdate.getX() -ZONE_UPDATE_SPAN);
        startZoneToUpdate.setY(startZoneToUpdate.getY() -ZONE_UPDATE_SPAN);

        ZoneCoordinate stopZoneToUpdate = new ZoneCoordinate(player1.getZoneCoordinate());
        stopZoneToUpdate.setX(stopZoneToUpdate.getX() +ZONE_UPDATE_SPAN);
        stopZoneToUpdate.setY(stopZoneToUpdate.getY() +ZONE_UPDATE_SPAN);

        world.update(startZoneToUpdate, stopZoneToUpdate);
        spawnEngineParticles();

        if(tick > 8000 && true){ //change true to false if you want to remove automatic quitting
            //to make the game not fuck up the computer if memory leaks
            System.out.println("Exited game to prevent fucked up memory leaks");
            System.out.println("Check in Game Model in tick() to remove");
            System.exit(0);
        }

        updateTime = System.currentTimeMillis() - start;
    }

    public void spawnAsteroids(){
        for(int i = 0; i < asteroidSpawnRate; i++){
            spawnAsteroid();
        }
    }

    public void spawnEngineParticles(){
        if(player1.accelerationRequest == true){
            spawnEngineParticle();
        }

    }

    public void spawnEngineParticle(){
        EngineParticle ep = new EngineParticle(player1);
        ep.updateZone(ZONE_SIZE);
        world.addWorldObject(ep);
    }

    public void spawnAsteroid(){
        Coordinate c = new Coordinate(player1.getCoordinate()); //make this some other arbitary coordinate .. now spawns on top of player
        ZoneCoordinate z = new ZoneCoordinate(player1.getZoneCoordinate());

        int xDifference = 1 - 2*Randomizer.randomInt(0, 1);
        int yDifference = 1 - 2*Randomizer.randomInt(0, 1);

        z.setX(player1.getZoneCoordinate().getX() + xDifference*2);
        z.setY(player1.getZoneCoordinate().getY() + yDifference*2);

        Asteroid a = new Asteroid(c, z);
        a.updateZone(ZONE_SIZE);
        world.addWorldObject(a);
    }

    public void updatePlayer(int i){
        Player p = getPlayer(i);

        if(p.turnLeftRequest){
            p.rotateLeft(Math.toRadians(p.getSpacecraft().getEngine().getRotationSpeed()));
        } else if (p.turnRightRequest) {
            p.rotateRight(Math.toRadians(p.getSpacecraft().getEngine().getRotationSpeed()));
        }
        if (p.accelerationRequest){
            p.accelerate();
        }
        else{
            p.deaccelerate();
        }

        //player.updatePosition(accelerationRequest, ZONE_SIZE);
        if(p.fireRequest){
            if (fireDelay <= 0){
                Projectile temp = p.fire();

                // might have changed zones
                temp.updateZone(ZONE_SIZE);

                world.addWorldObject(temp);

                fireDelay = fireDelayDefault;
            }
            else{
                fireDelay--;
            }
        }
    }

    public void updatePlayers(){
        updatePlayer(1);
        updatePlayer(2);
    }

    public double getZoneSize(){
        return ZONE_SIZE;
    }

    public void updateEnemies(){

    }

    public WorldObjectContainer getAllObjectsInArea(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
        return world.getAllObjectsInArea(zoneCoordinate, start, stop);
    }

    public boolean isGameAlive() {
        return alive;
    }

    public void addWorldObject(WorldObject wo){
        this.world.addWorldObject(wo);
    }

    public Player getPlayer(int i){
        if(i == 1){
            return player1;
        } else {
            return player2;
        }
    }
}
