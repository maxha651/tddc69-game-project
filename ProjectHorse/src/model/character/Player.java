package model.character;


import model.CollideableObject;
import model.GameModel;
import model.background.*;
import model.properties.Boundable;
import model.properties.Collideable;
import model.properties.Damageable;
import model.spacecraft.Spacecraft;
import model.utility.math.Randomizer;
import model.utility.shape.Coordinate;
import model.utility.shape.ZoneCoordinate;
import model.world.World;
import model.world.WorldObjectState;

import java.awt.*;

/**
 * Abstraction of coordination in 2D space, velocity, face angle and requests of
 * 1 player. 
 */
public class Player extends AbstractCharacter implements Boundable, Damageable{

    //declarations and initialization


    Color color = Color.RED; //not graphical color -- no graphical implementation -- used if needed
    protected int score = 0;

    /**
     * Standard constructor that initializes 1 player.
     */
    public Player(GameModel gameModel, Coordinate c, ZoneCoordinate zc){
        this.gameModel = gameModel;
        this.setSpacecraft(new Spacecraft(this));
        this.coordinate = new Coordinate(c);
        this.zoneCoordinate = new ZoneCoordinate(zc);
        this.width = spacecraft.getBounds().getWidth();
        this.height = spacecraft.getBounds().getHeight();
        this.mass = DEFAULT_MASS;
    }



    @Override
    public void doDamage(int amount) {
        health -= amount;
        if(health <= 0){
            this.state = WorldObjectState.DEAD;
        }
    }

    /**
     * Destroys the player and then lets the world remove hen when the world is "ready".
     * @param world
     */
    @Override
    public void destroy(World world){
        super.destroy(world);
        gameModel.giveScoreToOtherPlayer(this);
    }

    public void addScore(Damageable damageable){
        this.score += damageable.getScoreYield();
    }

    public int getScore() {
        return score;
    }

    public Color getColor() {
        return color;
    }
}
