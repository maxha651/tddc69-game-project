package graphics;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-20
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class GameFrame extends JFrame implements Observer {

    JMenuBar menuBar;
    JMenu fileMenu, settingsMenu, viewMenu;

    public GameFrame(String s) {
        super(s);

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

        this.pack();
        this.setVisible(true);
    }


    @Override
    public void update(Observable observable, Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
