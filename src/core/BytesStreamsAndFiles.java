package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import main.Main;

public class BytesStreamsAndFiles {

    /**
     * Read the given binary file, and return its contents as a byte array.
     */
    public static byte[] read(File file) throws IOException {
        byte[] result = new byte[(int) file.length()];

        InputStream input = new BufferedInputStream(new FileInputStream(file));

        for (int i = 0; i < file.length(); i++) {
            result[i] = (byte) input.read();
        }

        Main.log("Closing input stream.");
        input.close();

        return result;
    }

    /**
     * Write a byte array to the given file.
     * Writing binary data is significantly simpler than reading it.
     */
    public static void write(byte[] input, File file) throws IOException {
        Main.log("Writing binary file...");

        OutputStream output = new BufferedOutputStream(new FileOutputStream(file));
        output.write(input);
        output.close();

    }
}