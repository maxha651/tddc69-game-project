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

    //AI controllers

    //object controllers
    static final double MAX_SPACECRAFT_VELOCITY = 2.0;

    //graphics controllers

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
    }

    public boolean isGameAlive() {
        return isAlive;
    }
}
