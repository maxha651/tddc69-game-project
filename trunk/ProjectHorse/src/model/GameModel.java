package model;


import model.background.Asteroid;
import model.character.Player;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.spawners.AsteroidSpawner;
import model.world.World;
import model.world.WorldObject;
import model.world.WorldObjectContainer;
import model.world.WorldObjectState;

import java.awt.*;
import java.util.Observable;

public class GameModel extends Observable {

    //game controllers
    boolean alive = true;
    int seed;
    public long tick = 0;
    private final int RESET_DELAY = 100;
    private int resetTick;

    //player controllers
    Player player1;
    Player player2;
    
    // Projectile controllers
    private int fireDelayDefault = 10;
    private int fireDelay = 0;

    //object controllers
    public static final double DEFAULT_VELOCITY_FLOOR = 0.2;
    public static final double DEFAULT_SPACE_FRICTION = 0.98;
    public static final double ZONE_SIZE = 500;
    public static final int COLLIDING_CHECK_DISTANCE = 50; // determines how faraway objects to check for collision
    public static final int ZONE_UPDATE_SPAN = 5;    //the spawn of how many zones are loaded away from player
    public static final int asteroidSpawnRate = 2; //how many asteroids to spawn per tick
    public static final Coordinate startCoordinatePlayer1 = new Coordinate(ZONE_SIZE/2, ZONE_SIZE/2);
    public static final Coordinate startCoordinatePlayer2 = new Coordinate(ZONE_SIZE/2, ZONE_SIZE/2);
    public static final ZoneCoordinate startZonePlayer1 = new ZoneCoordinate(0, 0);
    public static final ZoneCoordinate startZonePlayer2 = new ZoneCoordinate(1, 1);
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
            player1 = new Player(this, startCoordinatePlayer1, startZonePlayer1);
    	} else {
    		player1.reset(startCoordinatePlayer1, startZonePlayer1);
    	}
    	
    	if(player2 == null){
            player2 = new Player(this, startCoordinatePlayer2, startZonePlayer2);
    	} else {
    		player2.reset(startCoordinatePlayer2, startZonePlayer2);
    	}

    	player1.setState(WorldObjectState.ALIVE);
    	player2.setState(WorldObjectState.ALIVE);
        world.addWorldObject(player1);
        world.addWorldObject(player2);
        world.addWorldObjectSpawner(new AsteroidSpawner());
        numberOfWorldObjects = 0;
        resetTick = RESET_DELAY;
        tick = 0;
    }
    
    public void initialize(){
    	world = new World(ZONE_SIZE);

    	player1 = new Player(this, startCoordinatePlayer1, startZonePlayer1);
    	player2 = new Player(this, startCoordinatePlayer2, startZonePlayer2);

        player2.setColor(Color.BLUE);

        world.addWorldObject(player1);
    	world.addWorldObject(player2);
    	world.addWorldObjectSpawner(new AsteroidSpawner());
    	numberOfWorldObjects = 0;
        resetTick = RESET_DELAY;
    	tick = 0;
    }

    public void tick(){

        long start = System.currentTimeMillis();

        numberOfWorldObjects = world.getNumberOfWorldObjects();
        tick++;

        ZoneCoordinate p1Coord = player1.getZoneCoordinate();
        ZoneCoordinate p2Coord = player2.getZoneCoordinate();
        ZoneCoordinate startZoneToUpdate = new ZoneCoordinate(0,0);
        ZoneCoordinate stopZoneToUpdate  = new ZoneCoordinate(0,0);

        if(p1Coord.getX() < p2Coord.getX()){
            startZoneToUpdate.setX(p1Coord.getX() - ZONE_UPDATE_SPAN);
            stopZoneToUpdate.setX(p2Coord.getX()  + ZONE_UPDATE_SPAN);
        }
        else{
            startZoneToUpdate.setX(p2Coord.getX() - ZONE_UPDATE_SPAN);
            stopZoneToUpdate.setX(p1Coord.getX()  + ZONE_UPDATE_SPAN);
        }
        if(p1Coord.getY() < p2Coord.getY()){
            startZoneToUpdate.setY(p1Coord.getY() - ZONE_UPDATE_SPAN);
            stopZoneToUpdate.setY(p2Coord.getY()  + ZONE_UPDATE_SPAN);
        }
        else{
            startZoneToUpdate.setY(p2Coord.getY() - ZONE_UPDATE_SPAN);
            stopZoneToUpdate.setY(p1Coord.getY()  + ZONE_UPDATE_SPAN);
        }

        world.update(startZoneToUpdate, stopZoneToUpdate);

        if(!player1.isAlive() || !player2.isAlive()){
            if (resetTick-- == 0){
                reset();
            }
        }

        if(tick > 10000 && true){ //change true to false if you want to remove automatic quitting
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

    public void spawnAsteroid(){
        Coordinate c = new Coordinate(player1.getCoordinate()); //make this some other arbitary coordinate .. now spawns on top of player
        ZoneCoordinate z = new ZoneCoordinate(player1.getZoneCoordinate());

        int xDifference = 1 - 2*Randomizer.randomInt(0, 1);
        int yDifference = 1 - 2*Randomizer.randomInt(0, 1);

        z.setX(player1.getZoneCoordinate().getX() + xDifference*2);
        z.setY(player1.getZoneCoordinate().getY() + yDifference*2);

        Asteroid a = new Asteroid(c, z);
        world.addWorldObject(a);
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

    public void giveScoreToOtherPlayer(Player p){
        if(p == player1){
            player2.addScore(player1.scoreYield());
        } else if (p == player2){
            player1.addScore(player2.scoreYield());
        }
    }

    public Player getPlayer(int i){
        if(i == 1){
            return player1;
        } else {
            return player2;
        }
    }
}
