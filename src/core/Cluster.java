package core;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class Cluster {
    /*
     * Represents a 2*2 image
     * _________
     * | a | b |
     * |___|___|
     * | c | d |
     * |___|___|
     */

    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;

    public Cluster(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Cluster(BufferedImage img, int depth) {
        Point loc = new Point((depth % img.getWidth()) * 2, (depth / img.getHeight()) * 2);
        a = img.getRGB(loc.x, loc.y);
        b = img.getRGB(loc.x + 1, loc.y);
        c = img.getRGB(loc.x, loc.y + 1);
        d = img.getRGB(loc.x + 1, loc.y + 1);
    }

    public BufferedImage addToImage(BufferedImage img, int depth) {
        Point loc = new Point((depth % img.getWidth()) * 2, (depth / img.getHeight()) * 2);
        img.setRGB(loc.x, loc.y, a);
        img.setRGB(loc.x + 1, loc.y, b);
        img.setRGB(loc.x, loc.y + 1, c);
        img.setRGB(loc.x + 1, loc.y + 1, d);
        return img;
    }

    public int deltaOfCluster(Cluster cluster) {
        return Math.abs(a - cluster.a) + Math.abs(b - cluster.b) + Math.abs(c - cluster.c) + Math.abs(d - cluster.d);
    }

    // This whole thing is ugly and dumb
    // TODO: Think of something better
}
