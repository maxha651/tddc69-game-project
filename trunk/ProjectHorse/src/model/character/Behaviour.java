package model.character;

import model.world.World;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-11-02
 * Time: 21:22
 * To change this template use File | Settings | File Templates.
 */
public interface Behaviour {

    public void update(World world, NPC target);
}
