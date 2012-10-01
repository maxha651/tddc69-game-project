package model.utility.math;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-21
 * Time: 00:48
 * To change this template use File | Settings | File Templates.
 */
public class Randomizer {
    public static int randomInt(int min, int max){
        Random rm = new Random();
        if(min <= max){
            return min  + rm.nextInt(max - min + 1);
        } else {
            return max;
        }
    }

    public static double randomDouble(double min, double max){
        Random rm = new Random();
        if(min <= max){
            return rm.nextDouble()*max + min;
        } else {
            return max;
        }
    }
}
