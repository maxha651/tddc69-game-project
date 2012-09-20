package model;


public class GameModel {
    final int ZONE_WIDTH_PIXELS = Integer.MAX_VALUE/2;
    final int ZONE_HEIGHT_PIXELS = Integer.MAX_VALUE/2;

    Player player;
    int seed;

    public GameModel(int seed){
        this.seed = seed;
        player = new Player();

    }
}
