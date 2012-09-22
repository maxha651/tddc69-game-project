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
        final GameModel gameModel = new GameModel();
        final GameFrame gameFrame = new GameFrame(gameModel);

        final Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                gameModel.tick();
            }
        };

        final Timer updateTimer = new Timer(200, doOneStep);
        updateTimer.start();
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
