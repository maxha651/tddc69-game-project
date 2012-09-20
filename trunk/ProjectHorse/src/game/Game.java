package game;

import graphics.GameFrame;
import graphics.GraphicalViewer;
import graphics.Viewer;
import model.GameModel;

public class Game {

    GameModel gameModel;

    public Game(){
        GameModel gameModel = new GameModel();
        GameFrame gameFrame = new GameFrame(gameModel);


    }

    public static void main(String[] args) {
        Game game = new Game();

    }
}
