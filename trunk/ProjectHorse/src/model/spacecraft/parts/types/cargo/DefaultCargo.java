package model.spacecraft.parts.types.cargo;

public class DefaultCargo implements CargoType {

    int maxCargo = 10;

    @Override
    public int getMaxCargo() {
        return maxCargo;
    }
}
