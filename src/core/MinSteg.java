package core;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Random;

public class MinSteg implements StegAlgoInterface {
    /*
     * Custom Algorithm
     *
     * The first four pixels the length of the data held within
     * and the four below that will hold the seed for the random that positions the data pixel clusters
     *
     * Data length and seed pixels will be in the format RGB = 211 to produce 4 bits per pixel
     *
     *
     * Data Pixel cluster:
     * ________
     * | . | . | <-- Row A
     * |___|___|
     * | . | . | <-- Row B
     * |___|___|
     * RGBRGB in 212212 for 10 bits
     *
     * a[0],a[1],b[0],b[1] for 4 bits,
     * to define the operation on the remaining 8 bits (1 byte) per row to retrieve the data
     *
     * if a[0] = b[0]
     * then the operation will be and
     * otherwise
     * 00 10 = or
     * 00 11 = nor
     * 01 10 = xor
     * 01 11 = not xor
     * 10 00 = a
     * 10 01 = not a
     * 11 00 = b
     * 11 01 = not b
     *
     * One cluster holds 1 byte
     *
     * ________________________________________________________
     * | D | D | D | D | C | . | C | . | C | . | C | . | C | . |
     * |___|___|___|___|___|___|___|___|___|___|___|___|___|___|
     * | S | S | S | S | . | . | . | . | . | . | . | . | . | . |
     * |___|___|___|___|___|___|___|___|___|___|___|___|___|___|
     * | C | . | C | . | C | . | C | . | C | . | C | . | C | . |
     * |___|___|___|___|___|___|___|___|___|___|___|___|___|___|
     * | . | . | . | . | . | . | . | . | . | . | . | . | . | . |
     * |___|___|___|___|___|___|___|___|___|___|___|___|___|___|
     * | C | . | C | . | C | . | C | . | C | . | C | . | C | . |
     * |___|___|___|___|___|___|___|___|___|___|___|___|___|___|
     * | . | . | . | . | . | . | . | . | . | . | . | . | . | . |
     * |___|___|___|___|___|___|___|___|___|___|___|___|___|___|
     * | . | . | . | . | . | . | . | . | . | . | . | . | . | . |
     * |___|___|___|___|___|___|___|___|___|___|___|___|___|___|
     *
     * D = Data location pixel
     * S = Seed pixel
     * C = Valid pixel for top left corner of data cluster
     *
     */

    public static final int infobytes = 4;
    private Steg steg = new Steg();

    public BufferedImage generate(BufferedImage img, byte[] dat) {
        BufferedImage out = img;

        byte[] res = ByteBuffer.allocate(infobytes * 2).putInt(dat.length).array();

        for (int i = 0; i < infobytes; i++) {
            out.setRGB(i, 0, steg.adddattorgb(res[i], img.getRGB(0, 0)));
        }

        int seed = new Random().nextInt();
        res = ByteBuffer.allocate(infobytes * 2).putInt(seed).array(); // allocating the same for safety sake
        for (int i = 0; i < 4; i++) {
            out.setRGB(i, 1, steg.adddattorgb(res[i], img.getRGB(i, 1)));
        }

        Random rand = new Random(seed);
        int validclusters = (img.getHeight() / 2) * (img.getWidth() / 2) - 2;

        for (int i = 0; i < dat.length; i++) {
            int depth = (int) (rand.nextDouble() * validclusters) + 2; // TODO : Check against duplicates
            out = addDatToCluster(dat[i], new Cluster(img, depth)).addToImage(out, depth);
        }

        return out;
    }

    public Cluster addDatToCluster(byte dat, Cluster cluster) {
        return cluster;
    }

    public byte[] retrieve(BufferedImage img) {
        return null;
    }

    public boolean isWorkable(BufferedImage img, int datlength) {
        if (datlength < (img.getHeight() / 2) * (img.getWidth() / 2) - 2) {
            return false; // TODO : change to true once the algo is implemented
        }
        return false;
    }
}
