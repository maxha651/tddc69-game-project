package game;

import graphics.GameFrame;
import graphics.GraphicalViewer;
import graphics.Viewer;
import model.GameModel;
import javax.swing.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Game {

    GameModel gameModel;

    public Game(){
        GameModel gameModel = new GameModel();
        GameFrame gameFrame = new GameFrame(gameModel);

        final Action doOneStep = AbstractAction(ActionEvent e) {
            gameModel.tick();
        };

        final Timer updateTimer = new Timer(200, doOneStep);
        updateTimer.start();
    }

    public static void main(String[] args) {
        Game game = new Game();

    }
}
