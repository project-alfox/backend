package com.ace.alfox.game.models;

import com.ace.alfox.lib.Vector2;

import java.util.Random;

public class Enemy {
    public byte[] id;

    public String name = "Ferocious Squirrel";
    public int hp = 100;
    public Vector2 location = new Vector2(0,0);

    public Enemy() {
        Random r = new Random();
        id = new byte[16];
        r.nextBytes(id);
    }
}
