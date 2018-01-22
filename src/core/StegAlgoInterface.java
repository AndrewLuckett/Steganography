package core;

import java.awt.image.BufferedImage;

public interface StegAlgoInterface {

    public BufferedImage generate(BufferedImage img, byte[] dat);

    public byte[] retrieve(BufferedImage img);

    public default boolean isDataWithinSupportedSize(long datlength) {
        return datlength < Math.pow(2, 32) - 1;
    };

    public boolean isWorkable(BufferedImage img, int datlength);
}
