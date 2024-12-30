package de.sbg.unity.iconomy.Utils;


public class icMath {
    
    public int randomInRange(int min, int max) {
        return (int) (java.lang.Math.random() * (max - min)) + min;
    }
}
