package core;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class Steg implements StegAlgoInterface {
    /*
     * One dat byte fits in a single pixel
     * infobytes describes how many bytes at the start of the image hold the data for the number of dat bytes
     * infobytes should remain the same throughout any usage of the program
     * if info bytes is 4 then there are 32 bits of data for the how many pixels are used for dat
     * 32 bits allows for max int = 2147483647 which would be the max number of data bytes (2.14GB)
     * it would take an image with at least 2147483651 pixels to contain this
     */

    public static final int infobytes = 4;

    public BufferedImage generate(BufferedImage img, byte[] dat) {
        // BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        BufferedImage out = img;

        byte[] res = ByteBuffer.allocate(infobytes * 2).putInt(dat.length).array(); // I'm not sure how many to allocate

        for (int i = 0; i < infobytes; i++) {
            out.setRGB(i, 0, adddattorgb(res[i], img.getRGB(i, 0)));
        }

        for (int i = infobytes; i < dat.length + infobytes; i++) {
            int y = Math.floorDiv(i, img.getWidth());
            int x = Math.floorMod(i, img.getWidth());

            out.setRGB(x, y, adddattorgb(dat[i - infobytes], img.getRGB(x, y)));
        }

        return out;
    }

    private int adddattorgb(byte dat, int rgb) {
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

    public byte[] retrieve(BufferedImage img) {

        byte[] res = new byte[infobytes];

        for (int i = 0; i < infobytes; i++) {
            res[i] = getfromrgb(img.getRGB(i, 0));
        }
        int bytecount = (ByteBuffer.wrap(res)).getInt();

        byte[] dat = new byte[bytecount];

        for (int i = infobytes; i < bytecount + infobytes; i++) {
            int y = Math.floorDiv(i, img.getWidth());
            int x = Math.floorMod(i, img.getWidth());

            dat[i - infobytes] = getfromrgb(img.getRGB(x, y));
        }

        return dat;
    }

    private byte getfromrgb(int rgb) {
        String rgbdat = Integer.toBinaryString(rgb);

        if (rgbdat.length() < 24) {
            String pad = "";
            for (int i = 0; i < 24 - rgbdat.length(); i++) {
                pad += "0";
            }

            rgbdat = pad + rgbdat;
        }
        if (rgbdat.length() > 24) {
            rgbdat = rgbdat.substring(rgbdat.length() - 24);
        }

        rgbdat = rgbdat.substring(5, 8) + rgbdat.substring(14, 16) + rgbdat.substring(21);

        return (byte) Integer.parseInt(rgbdat, 2);
    }

    public boolean isWorkable(BufferedImage img, int datlength) {
        return datlength < img.getHeight() * img.getWidth() - 4;
    }
}
