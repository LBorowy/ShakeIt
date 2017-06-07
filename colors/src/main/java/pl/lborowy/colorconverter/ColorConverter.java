package pl.lborowy.colorconverter;

import android.graphics.Color;

/**
 * Created by RENT on 2017-06-07.
 */

public class ColorConverter {

    public static int getColorFromPosition(float x, float y, float z) {

        int r = (int)Math.abs(x)*20;
        int g = (int)Math.abs(y)*20;
        int b = (int)Math.abs(z)*20;

        int color = Color.rgb(r,g,b);

        //// TODO: 2017-06-07 return color
        return color;
    }

    public static int getColorCanal(float x) {
        return (int) Math.abs(x)*20;
    }

    public static int getNegativeColorFromPosition(int color) {
        int red = 255 - Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        //negatyw
        return Color.rgb(red, 255-green, 255-blue);
    }

}
