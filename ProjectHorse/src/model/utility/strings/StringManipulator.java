package model.utility.strings;

/**
 * Created with IntelliJ IDEA.
 * User: Brain
 * Date: 2012-09-23
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class StringManipulator {

    public static String toString(double d, int decimals){
        int power = (int) Math.pow(10, decimals);
        return Double.toString(((double)((int)(d*power)))/power);
    }

}
