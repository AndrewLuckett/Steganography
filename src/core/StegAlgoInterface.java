package core;

import java.awt.image.BufferedImage;

public interface StegAlgoInterface {
    public BufferedImage generate(BufferedImage img, byte[] dat);

    public byte[] retrieve(BufferedImage img);

    public boolean isWorkable(BufferedImage img, byte[] dat);
}
