package com.example.saju.common;

public enum FiveElementGround {

    子(FiveElement.WATER),
    丑(FiveElement.EARTH),
    寅(FiveElement.WOOD),
    卯(FiveElement.WOOD),
    辰(FiveElement.EARTH),
    巳(FiveElement.FIRE),
    午(FiveElement.FIRE),
    未(FiveElement.EARTH),
    申(FiveElement.METAL),
    酉(FiveElement.METAL),
    戌(FiveElement.EARTH),
    亥(FiveElement.WATER);


    private final FiveElement element;

    FiveElementGround(FiveElement element) { this.element = element; }

    public static int getElementidxByGround(String ground) {
        for (FiveElementGround element : FiveElementGround.values()) {
            if (element.name().equals(ground)) {
                return element.element.idx();
            }
        }
        return -1;
    }
}
