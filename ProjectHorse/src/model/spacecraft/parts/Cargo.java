package model.spacecraft.parts;

import model.items.Item;
import model.spacecraft.parts.types.cargo.CargoType;
import model.spacecraft.parts.types.cargo.DefaultCargo;

import java.util.LinkedList;

public class Cargo extends SpacecraftPart {
    CargoType cargoType;
    private LinkedList<Item> items;

    public Cargo() {
        this.cargoType = new DefaultCargo();
    }

    public Cargo(CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public Item get(int i) {
        return items.get(i);
    }

    public void add(int i, Item item) throws Exception {
        if(this.size() >= cargoType.getMaxCargo()){
            throw new Exception("Cargo Full"); // Make custom exception
        }
        items.add(i, item);
    }

    public Item remove(int i) {
        return items.remove(i);
    }

    public int size() {
        return items.size();
    }
}
