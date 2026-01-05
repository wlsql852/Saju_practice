package com.example.saju.common;

public enum FiveElement {
    WOOD(0), FIRE(1), EARTH(2), METAL(3), WATER(4);

    private final int idx;

    FiveElement(int idx) { this.idx = idx; }

    public int idx() { return idx; }
}
