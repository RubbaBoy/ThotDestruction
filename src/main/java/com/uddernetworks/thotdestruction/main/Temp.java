package com.uddernetworks.thotdestruction.main;

import java.awt.*;

public class Temp {

    public static void main(String[] args) {
        System.out.println(isWithin(4, 7, 2));
    }


    private static boolean isWithin(int num1, int num2, int range) {
        System.out.println("num1 = [" + num1 + "], num2 = [" + num2 + "], range = [" + range + "]");

        int diff = Math.abs(num1 - num2);

        return diff <= range;

    }

}
