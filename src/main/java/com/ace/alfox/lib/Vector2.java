package com.ace.alfox.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Vector;

public class Vector2 extends Vector<Integer> {
    public Vector2() {
        super();
    }
    public Vector2(int[] array) {
        this(array[0], array[1]);
    }
    public Vector2(int x, int y) {
        super(2, 0);
        add(x);
        add(y);
    }

    public Vector2 mathAdd (Vector2 vector) {
        return new Vector2(get(0) + vector.get(0), get(1) + vector.get(1));
    }
}
