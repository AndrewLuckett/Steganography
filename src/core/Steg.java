package core;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class Steg {
    /*
     * One dat byte fits in a single pixel
     * infobytes describes how many bytes at the start of the image hold the data for the number of dat bytes
     * infobytes should remain the same throughout any usage of the program
     * if info bytes is 3 then there are 24 bits of data for the how many pixels are used for dat
     * 24 bits allows for max int = 8388607, the max number of data bytes (8.3GiB)
     * it would take an image with at least 8388610 pixels to contain this (2113*3970)
     */

    public static final int infobytes = 3;

    public static BufferedImage generate(BufferedImage img, byte[] dat) {
        // BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        BufferedImage out = img;

        byte[] res = ByteBuffer.allocate(infobytes * 2).putInt(dat.length).array(); // I'm not sure how many to allocate

        for (int i = 0; i < infobytes; i++) {
            out.setRGB(i, 0, adddattorgb(res[i], img.getRGB(0, 0)));
        }

        for (int i = infobytes; i <= dat.length; i++) {
            int y = Math.floorDiv(i, img.getWidth());
            int x = Math.floorMod(i, img.getWidth());

            out.setRGB(x, y, adddattorgb(dat[i - infobytes], img.getRGB(x, y)));
        }

        return out;
    }

    private static int adddattorgb(byte dat, int rgb) {
        String rgbdat = Integer.toBinaryString(rgb);
        String data = String.format("%8s", Integer.toBinaryString(dat & 0xFF)).replace(' ', '0');

        String out = "";

        if (rgbdat.length() < 24) {
            String pad = "";
            for (int i = 0; i < 24 - rgbdat.length(); i++) {
                pad += "0";
            }

            rgbdat = pad + rgbdat;
        }
        if (rgbdat.length() > 24) {
            out += rgbdat.substring(0, rgbdat.length() - 24);
            rgbdat = rgbdat.substring(rgbdat.length() - 24);
        }

        out += rgbdat.substring(0, 5) + data.substring(0, 3) +
            rgbdat.substring(8, 14) + data.substring(3, 5) +
            rgbdat.substring(16, 21) + data.substring(5);

        return (int) Long.parseLong(out, 2);
    }

    public static byte[] retrieve(BufferedImage img) {
        return null;
    }
}
