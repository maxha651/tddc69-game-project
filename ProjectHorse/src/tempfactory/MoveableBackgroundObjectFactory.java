package tempfactory;

import model.WorldObject;
import model.background.MoveableBackgroundObject;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-21
 * Time: 01:53
 * To change this template use File | Settings | File Templates.
 */
public class MoveableBackgroundObjectFactory implements WorldObjectFactory {
    @Override
    public WorldObject create() {
        return new MoveableBackgroundObject();
    }
}
