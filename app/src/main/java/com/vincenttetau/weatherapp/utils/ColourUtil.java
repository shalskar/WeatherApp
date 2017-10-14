package com.vincenttetau.weatherapp.utils;

public class ColourUtil {

    /** Code snippet obtained from:
     * https://stackoverflow.com/questions/6070163/color-mixing-in-android
     * **/
    public static int mixColours(int firstColour, int secondColour, float amount) {
        final byte ALPHA_CHANNEL = 24;
        final byte RED_CHANNEL = 16;
        final byte GREEN_CHANNEL = 8;
        final byte BLUE_CHANNEL = 0;

        final float inverseAmount = 1.0f - amount;

        int a = ((int) (((float) (firstColour >> ALPHA_CHANNEL & 0xff) * amount) +
                ((float) (secondColour >> ALPHA_CHANNEL & 0xff) * inverseAmount))) & 0xff;
        int r = ((int) (((float) (firstColour >> RED_CHANNEL & 0xff) * amount) +
                ((float) (secondColour >> RED_CHANNEL & 0xff) * inverseAmount))) & 0xff;
        int g = ((int) (((float) (firstColour >> GREEN_CHANNEL & 0xff) * amount) +
                ((float) (secondColour >> GREEN_CHANNEL & 0xff) * inverseAmount))) & 0xff;
        int b = ((int) (((float) (firstColour & 0xff) * amount) +
                ((float) (secondColour & 0xff) * inverseAmount))) & 0xff;

        return a << ALPHA_CHANNEL | r << RED_CHANNEL | g << GREEN_CHANNEL | b << BLUE_CHANNEL;
    }

}
