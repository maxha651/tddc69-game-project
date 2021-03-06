package model;


import model.background.Asteroid;
import model.character.NPC;
import model.character.Player;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.spawners.AsteroidSpawner;
import model.world.World;
import model.world.WorldObject;
import model.world.WorldObjectState;

import java.awt.*;
import java.util.Collection;
import java.util.Observable;

public class GameModel extends Observable {

    //game controllers
    boolean alive = true;
    int seed;
    public long tick = 0;
    private static final int RESET_DELAY = 200;
    private int resetTick;

    //player controllers
    Player player1;
    Player player2;

    //object controllers
    public static final double DEFAULT_VELOCITY_FLOOR = 0.2;
    public static final double DEFAULT_SPACE_FRICTION = 0.98;
    public static final double ZONE_SIZE = 500;
    public static final int COLLIDING_CHECK_DISTANCE = 50; // determines how faraway objects to check for collision
    public static final int ZONE_UPDATE_SPAN = 5;    //the spawn of how many zones are loaded away from player
    public static final int ASTEROID_SPAWN_RATE = 2; //how many asteroids to spawn per tick
    public static final Coordinate START_COORDINATE_PLAYER_1 = new Coordinate(ZONE_SIZE/2, ZONE_SIZE/2);
    public static final Coordinate START_COORDINATE_PLAYER_2 = new Coordinate(ZONE_SIZE/2, ZONE_SIZE/2);
    public static final ZoneCoordinate START_ZONE_PLAYER_1 = new ZoneCoordinate(0, 0);
    public static final ZoneCoordinate START_ZONE_PLAYER_2 = new ZoneCoordinate(1, 1);
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

    	if(player1 != null && player2 != null){
    		player1.reset(START_COORDINATE_PLAYER_1, START_ZONE_PLAYER_1);
    		player2.reset(START_COORDINATE_PLAYER_2, START_ZONE_PLAYER_2);
    	} else {
    		System.err.println("null pointer exception at player1 and player2");
    		System.exit(0);
    	}

        world.addWorldObject(player1);
        world.addWorldObject(player2);
        world.addWorldObjectSpawner(new AsteroidSpawner());
        numberOfWorldObjects = 0;
        resetTick = RESET_DELAY;
        tick = 0;
    }
    
    public void initialize(){
    	world = new World(ZONE_SIZE);

    	player1 = new Player(this, START_COORDINATE_PLAYER_1, START_ZONE_PLAYER_1);
    	player2 = new Player(this, START_COORDINATE_PLAYER_2, START_ZONE_PLAYER_2);

        //player2.setColor(Color.BLUE);

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
            resetTick--;
            if (resetTick == 0){
                reset();
            }
        }

        updateTime = System.currentTimeMillis() - start;
    }

    public void spawnAsteroids(){
        for(int i = 0; i < ASTEROID_SPAWN_RATE; i++){
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

    public Collection<WorldObject> getAllObjectsInArea(ZoneCoordinate zoneCoordinate, Coordinate start, Coordinate stop){
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
            player2.addScore(player1);
        } else if (p == player2){
            player1.addScore(player2);
        }
    }

    public Player getPlayer(int i){
        if(i == 1){
            return player1;
        } else {
            return player2;
        }
    }
    
    public Player getOtherPlayer(Player p){
    	if(p == player1){
    		return player2;
    	} else {
    		return player1;
    	}
    }
}
