package model.utility.math;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-23
 * Time: 02:40
 * To change this template use File | Settings | File Templates.
 */
public class StandardMath {
    public static double pyth(double a, double b){
        return Math.sqrt(Math.pow(a, 2) + Math.pow(a, 2));
    }

    public static double pyth(int a, int b){
        return Math.sqrt(Math.pow(a, 2) + Math.pow(a, 2));
    }

    public static double xPart(double x, double angle){
        return Math.cos(angle) * x;
    }

    public static double yPart(double x, double angle){
        return Math.sin(angle) * x;
    }
}
