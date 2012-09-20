package model;


import model.character.Player;

import java.util.Observable;

public class GameModel extends Observable {
    final int ZONE_WIDTH_PIXELS = Integer.MAX_VALUE/2;
    final int ZONE_HEIGHT_PIXELS = Integer.MAX_VALUE/2;

    Player player;
    int seed;

    public GameModel(){
        this.seed = 1;
    }

    public GameModel(int seed){
        this();
        this.seed = seed;
        player = new Player();

    }
}
