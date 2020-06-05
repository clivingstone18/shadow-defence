package main.java;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Airplane extends Tower {
    private int speed;
    private Vector2 dirVec;
    private double timeElapsedDrop;
    private double dropTime;
    private double FPS = 60;
    private Explosive explosive;
    private int numDetonated;
    private int numDropped;
    private List<Explosive> explosives;


    public boolean isFinished() {
        return (this.outOfBounds() && numDropped == numDetonated);
    }

    @Override
    public Tower copy() {
        Airplane copy = new Airplane(this.getCenter(), this.getImage());
        return copy;
    }

    public Airplane(Point position, Image imageSrc) {
        super(position, imageSrc);
        setCost(500);
        setEffectRadius(200);
        setDamage(500);
        this.explosive = new Explosive(super.getCenter(),"res/images/explosive.png", 200);
        this.explosives = new ArrayList<>();
        this.speed = 5;
        this.timeElapsedDrop = 0;
        this.numDropped = 0;
        this.numDetonated = 0;
        this.dropTime = Math.random() * 3;
    }

    public void setSpawnPoint() {
        //Travelling horizontally
        if (this.dirVec.y == 0) {
            super.centerRectAt(new Point(-10, super.getCenter().y));
        }
        else {
            super.centerRectAt(new Point(super.getCenter().x, 20));
        }
    }

    public void setDirVec(Vector2 dirVec) {
        this.dirVec = dirVec;
    }

    @Override
    public void detectAndShoot() {
        // Updates existing Projectiles;
        for (int i=0; i<this.explosives.size(); i++) {
            Explosive dormantExplosive = (Explosive) this.explosives.get(i);
            dormantExplosive.increaseTime();
        }
        if (super.outOfBounds()) {return;}

        this.timeElapsedDrop += ShadowDefend.getTimescale()/FPS;

        //Adds new explosive to the list
        if (this.timeElapsedDrop >= this.dropTime) {
            Explosive newExplosive = new Explosive(super.getCenter(),"res/images/explosive.png", 200);
            newExplosive.centerRectAt(super.getCenter());
            this.explosives.add(newExplosive);
            this.timeElapsedDrop = 0;
            // Generates a new drop time
            this.dropTime = Math.random() * 3;
            this.numDropped++;
        }

    }

    public void HitTargets() {
        List<Explosive> detExp = new ArrayList<>();
            for (int i=0; i<this.explosives.size(); i++) {
                Explosive explosive = this.explosives.get(i);
                if (explosive != null && explosive.getDetTime()>=2) {
                    numDetonated++;
                    detExp.add(explosive);
                    //Checks if enemy is in the area
                    for (Slicer target: ShadowDefend.activeEnemies) {
                        if (explosive.getCenter().distanceTo(target.getCenter()) <= getEffectRadius()) {
                            target.reduceHealth(500);
                    }
                }
            }
        }
      explosives.removeAll(detExp);
    }

    @Override
    public void updateAllProjectiles() {
        for (Explosive explosive: explosives) {
            explosive.update();
        }
    }

    public void update() {
        if (!outOfBounds()) {
            super.move(dirVec.mul(ShadowDefend.getTimescale() * speed));
        }
        super.update();
    }

}

