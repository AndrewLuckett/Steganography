package main;

import java.awt.Dimension;

import javax.swing.JFrame;

import ui.HomeContent;
import window.Content;
import window.WindowFrame;

public class Main {

    public static Content HOMECONTENT = new HomeContent();

    public static int loglvl = 5;

    public static void main(String[] args) {

        WindowFrame inst = new WindowFrame(HOMECONTENT, new Dimension(440, 230), "Image Steganography - Andrew Luckett 2017");
        inst.setVisible(true);

        inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void log(Object dat) {
        System.out.println(dat);
    }

    public static void log(Object dat, int lvl) {
        if (lvl >= loglvl) {
            log(dat);
        }
    }

}
