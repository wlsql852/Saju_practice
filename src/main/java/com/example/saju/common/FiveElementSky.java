package com.example.saju.common;

public enum FiveElementSky {
    甲(FiveElement.WOOD),
    乙(FiveElement.WOOD),
    丙(FiveElement.FIRE),
    丁(FiveElement.FIRE),
    戊(FiveElement.EARTH),
    己(FiveElement.EARTH),
    庚(FiveElement.METAL),
    辛(FiveElement.METAL),
    壬(FiveElement.WATER),
    癸(FiveElement.WATER);

    private final FiveElement element;

    FiveElementSky(FiveElement element) { this.element = element; }

    public static int getElementidxBySky(String sky) {
        for (FiveElementSky element : FiveElementSky.values()) {
            if (element.name().equals(sky)) {
                return element.element.idx();
            }
        }
        return -1;
    }
}
