package model.spacecraft.parts.types.cargo;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 2012-10-13
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCargo implements CargoType {

    int maxCargo = 10;

    @Override
    public int getMaxCargo() {
        return maxCargo;
    }
}
