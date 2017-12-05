package core;

import java.awt.image.BufferedImage;

public class MinSteg implements StegAlgoInterface {

    public BufferedImage generate(BufferedImage img, byte[] dat) {
        return img;
    }

    public byte[] retrieve(BufferedImage img) {
        return null;
    }

    public boolean isWorkable(BufferedImage img, byte[] dat) {
        return false;
    }
}
