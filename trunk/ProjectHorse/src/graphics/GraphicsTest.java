package graphics;

import game.GameFrame;
import model.GameModel;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-20
 * Time: 22:10
 * To change this template use File | Settings | File Templates.
 */
public class GraphicsTest {

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame(new GameModel(1));
    }
}
