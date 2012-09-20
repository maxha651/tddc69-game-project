package tempfactory;

import model.WorldObject;
import model.background.PinnedBackgroundObject;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-09-21
 * Time: 01:55
 * To change this template use File | Settings | File Templates.
 */
public class PinnedBackgroundObjectFactory implements WorldObjectFactory {
    @Override
    public WorldObject create() {
        return PinnedBackgroundObject;
    }
}
