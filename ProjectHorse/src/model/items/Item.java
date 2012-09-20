package model.items;

import model.LocalObject;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-20
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
public class Item extends LocalObject {
    public void setValue(int value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    int value;
    String name;
    String description;


}
