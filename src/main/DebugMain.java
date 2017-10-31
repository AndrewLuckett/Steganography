package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.BytesStreamsAndFiles;
import core.Steg;

public class DebugMain {

    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File("C:/Users/Andrew/Pictures/stegin.png"));
            byte[] dat = BytesStreamsAndFiles.read(new File("C:/Users/Andrew/Pictures/Folder.jpg"));

            BufferedImage out = Steg.generate(img, dat);

            ImageIO.write(out, "png", new File("C:/Users/Andrew/stegtestimg.png"));

            System.out.println("donea");

            byte[] rec = Steg.retrieve(out);

            BytesStreamsAndFiles.write(rec, new File("C:/Users/Andrew/stegtestrecovered"));

            System.out.println("doneb");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
