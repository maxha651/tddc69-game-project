package game;

import model.GameModel;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Main class of game. When constructed a normal 2 player game is initialized and started.
 */
public class Game {

    GameModel gameModel;
    final static int paintRateGoal = 30; 
    final static int updateRateGoal = 100;
    boolean pause = false;
    
	/**
     * Main constructor. Used to start and load a new game (2 players).
     */
    public Game(){
        final GameModel gameModel = new GameModel();
        final GameFrame gameFrame = new GameFrame(gameModel, this);
        int updateTime = 1000/updateRateGoal; 
        int paintTime = 1000/paintRateGoal;
        
        //game loop
        final Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

            	if(!pause){
            		//updates and paints the game
            		gameModel.tick();
            		gameFrame.repaint();
            	}
            }
        };
        
        final Action paintOneStep = new AbstractAction() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        };

        //starts the game loop
        final Timer updateTimer = new Timer(updateTime, doOneStep);
        updateTimer.start();
        
        final Timer paintTimer = new Timer(paintTime, paintOneStep);
        paintTimer.start();
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
    
    //getters and setters
    public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
}
