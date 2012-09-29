package game;

import graphics.GraphicalViewer;
import model.GameModel;
import model.character.Player;

import javax.swing.*;
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
public class GameFrame extends JFrame implements KeyListener, Observer {

    JMenuBar menuBar;
    JMenu fileMenu, settingsMenu, viewMenu;
    static final String FRAME_TITLE = "Space Explorer";
    GameModel gameModel;
    GraphicalViewer viewer;

    public GameFrame(GameModel gameModel) {

        super(FRAME_TITLE);

        this.gameModel = gameModel;
        this.addKeyListener(this);

        fileMenu = new JMenu("File");
        settingsMenu = new JMenu("Settings");
        viewMenu = new JMenu("View");

        fileMenu.add(new JMenuItem("New"));
        settingsMenu.add(new JMenuItem("New"));
        viewMenu.add(new JMenuItem("New"));
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(viewMenu);
        this.add(menuBar);

        viewer = new GraphicalViewer(gameModel);
        this.add(viewer);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void update(Observable observable, Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
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
                viewer.setLockOnPlayer(!viewer.isLockOnPlayer());
                break;
            case KeyEvent.VK_SPACE:
                gameModel.fireRequest = true;
                break;
            case KeyEvent.VK_I :
                viewer.setPaintExtraInformation(!viewer.isPaintExtraInformation());
                break;
            case KeyEvent.VK_C :
                viewer.setDrawCross(!viewer.isDrawCross());
                break;
            case KeyEvent.VK_K :
                viewer.setPaintKeyBindings(!viewer.isPaintKeyBindings());
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