package game;

import model.GameModel;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Main class of game. When constructed a normal 2 player game is initialized and started.
 * Updates and painting is made in the same loop for performance issues when using 2 threads/timers.
 * And thats why the paint loop is commented away. If hardware acceleration is used the painting
 * SHOULD be made in the paint loop because of different CPU/GPU being used.
 */
public class Game {
    final static int UPDATE_RATE_GOAL = 80;
    boolean pause = false;
    
	/**
     * Main constructor. Used to start and load a new game (2 players).
     */
    public Game(){
        final GameModel gameModel = new GameModel();
        final GameFrame gameFrame = new GameFrame(gameModel, this);
        int updateTime = 1000/ UPDATE_RATE_GOAL;
        
        //game loop
        final Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

            	if(!pause){
            		gameModel.tick();
            		gameFrame.repaint();
            	}
            }
        };
        
        //paint loop
        /*
        final Action paintOneStep = new AbstractAction() {
        	public void actionPerformed(ActionEvent e) {

        		gameFrame.repaint();
        	}
        };*/

        //starts the game loop
        final Timer updateTimer = new Timer(updateTime, doOneStep);
        updateTimer.start();
        
        /*final Timer paintTimer = new Timer(paintTime, paintOneStep);        
        paintTimer.start();*/
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
