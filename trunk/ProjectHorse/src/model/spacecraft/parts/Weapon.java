package model.spacecraft.parts;

import model.character.Player;
import model.spacecraft.parts.types.projectile.ProjectileRed;
import model.spacecraft.parts.types.weapon.DefaultWeapon;
import model.spacecraft.parts.types.projectile.ProjectileBlue;
import model.spacecraft.parts.types.projectile.ProjectileType;
import model.spacecraft.parts.types.weapon.WeaponType;

import java.awt.*;

/**
 * The weapon is used to decide a players or NPCs fire rate, projectile output and damage.
 */
public class Weapon extends SpacecraftPart {
    ProjectileType pt;
    WeaponType wt;
    int fireDelay = 10;
    int fireDelayTop = 10;

    public Weapon(Player p){
        this.wt = new DefaultWeapon();
        this.pt = new ProjectileRed();

        /*if(p.getColor().equals(Color.blue)){
            this.pt = new ProjectileBlue();
        } else {
            this.pt = new ProjectileRed();
        }*/

    }

    public Weapon(){
        this.wt = new DefaultWeapon();
        this.pt = new ProjectileBlue();


    }

    public Weapon(WeaponType weaponType, ProjectileType projectileType) {
        this.wt = weaponType;
        this.pt = projectileType;
    }

    public WeaponType getWeaponType() {
        return wt;
    }

    public void setWeaponType(WeaponType wt){
        this.wt = wt;
    }

    public ProjectileType getProjectileType() {
        return pt;
    }

    public void setProjectileType(ProjectileType pt) {
        this.pt = pt;
    }

    public int getFireDelay(){
        return fireDelay;
    }

}
