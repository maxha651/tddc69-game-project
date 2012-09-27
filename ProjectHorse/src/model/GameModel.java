package model;


import model.character.Player;

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

    //AI controllers

    //object controllers
    public static final double DEFAULT_VELOCITY_FLOOR = 0.2;
    public static final double DEFAULT_SPACE_FRICTION = 0.99;

    //graphics controllers

    //other
    public static final String VERSION = "1.0a";

    public GameModel(){
        this(1);
    }

    public GameModel(int seed){
        this.seed = seed;
        player = new Player();
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
        player.updatePosition(accelerationRequest);
    }

    public void updateEnemies(){

    }

    public void updateProjectiles(){

    }

    public boolean isGameAlive() {
        return isAlive;
    }
}
