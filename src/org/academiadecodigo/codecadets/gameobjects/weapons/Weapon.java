package org.academiadecodigo.codecadets.gameobjects.weapons;

import org.academiadecodigo.codecadets.Position;
import org.academiadecodigo.codecadets.enums.WeaponTypes;
import org.academiadecodigo.codecadets.gameobjects.GameObject;
import org.academiadecodigo.codecadets.gameobjects.Target;

abstract public class Weapon extends GameObject {

    //Proprieties:----------------
    private Position aim;
    private int ammo;
    private WeaponTypes type;
    private int damage;
    private int clips;

    //methods:---------------------

    public Weapon(WeaponTypes type) {
        this.type = type;
        this.ammo = type.getClipBullets();
        this.clips = type.getClips();
        this.damage = type.getDamage();
    }

    public void shoot(Target target) {

        target.hit(damage);
    }

    public void reload() {
        if (clips == 0) {
            return;
        }

        ammo = type.getClipBullets();
        clips--;
    }


    public void setAim(Position aiming) {
        this.aim.setX(aiming.getX());
        this.aim.setY(aiming.getY());
    }

    public Position getAim() {
        return aim;
    }

    public int getAmmo() {
        return ammo;
    }

    public WeaponTypes getType() {
        return type;
    }

}
