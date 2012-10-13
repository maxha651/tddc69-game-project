package game;

import graphics.GraphicalViewer;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-20
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class GameFrame extends JFrame implements KeyListener{

    JMenuBar menuBar;
    JMenu fileMenu, settingsMenu, viewMenu;
    static final String FRAME_TITLE = "Space Explorer";
    GameModel gameModel;
    GraphicalViewer viewer1;
    GraphicalViewer viewer2;

    public GameFrame(GameModel gameModel) {

        super(FRAME_TITLE);

        this.gameModel = gameModel;
        this.addKeyListener(this);

        viewer1 = new GraphicalViewer(gameModel);
        viewer2 = new GraphicalViewer(gameModel);

        this.setLayout(new FlowLayout());

        this.add(viewer1);
        this.add(viewer2);


        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }



    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Player p = gameModel.getPlayer();
        switch(keyCode){
            case KeyEvent.VK_W:
                gameModel.accelerationRequest = true;
                break;
            case KeyEvent.VK_A:
                gameModel.turnLeftRequest = true;
                break;
            case KeyEvent.VK_D:
                gameModel.turnRightRequest = true;
                break;
            case KeyEvent.VK_F:
                viewer1.setLockOnPlayer(!viewer1.isLockOnPlayer());
                break;
            case KeyEvent.VK_SPACE:
                gameModel.fireRequest = true;
                break;
            case KeyEvent.VK_I :
                viewer1.setPaintExtraInformation(!viewer1.isPaintExtraInformation());
                break;
            case KeyEvent.VK_C :
                viewer1.setDrawCross(!viewer1.isDrawCross());
                break;
            case KeyEvent.VK_K :
                viewer1.setPaintKeyBindings(!viewer1.isPaintKeyBindings());
                break;
            case KeyEvent.VK_B :
                viewer1.setPaintWorldObjectBounds(!viewer1.isPaintWorldObjectBounds());
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode){
            case KeyEvent.VK_W:
                gameModel.accelerationRequest = false;
                break;
            case KeyEvent.VK_A:
                gameModel.turnLeftRequest = false;
                break;
            case KeyEvent.VK_D:
                gameModel.turnRightRequest = false;
                break;
            case KeyEvent.VK_SPACE:
                gameModel.fireRequest = false;
                break;
        }

    }
}
