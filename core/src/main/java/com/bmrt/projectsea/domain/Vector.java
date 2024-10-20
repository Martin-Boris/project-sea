package com.bmrt.projectsea.domain;

import java.util.Objects;

public class Vector {

    public static final Vector ZERO = new Vector(0, 0);

    private final float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector mult(float factor) {
        return new Vector(x * factor, y * factor);
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Float.compare(x, vector.x) == 0 && Float.compare(y, vector.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
