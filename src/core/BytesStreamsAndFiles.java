package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import main.Main;

public class BytesStreamsAndFiles {

    /** Read the given binary file, and return its contents as a byte array. */
    public static byte[] read(File file) {
        Main.log("File size: " + file.length());
        byte[] result = new byte[(int) file.length()];
        try {
            InputStream input = null;
            try {
                int totalBytesRead = 0;
                input = new BufferedInputStream(new FileInputStream(file));
                while (totalBytesRead < result.length) {
                    int bytesRemaining = result.length - totalBytesRead;
                    // input.read() returns -1, 0, or more :
                    int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
                    if (bytesRead > 0) {
                        totalBytesRead = totalBytesRead + bytesRead;
                    }
                }
                /*
                 * the above style is a bit tricky: it places bytes into the 'result' array;
                 * 'result' is an output parameter;
                 * the while loop usually has a single iteration only.
                 */
                Main.log("Num bytes read: " + totalBytesRead);
            } finally {
                Main.log("Closing input stream.");
                input.close();
            }
        } catch (FileNotFoundException ex) {
            Main.log("File not found.");
        } catch (IOException ex) {
            Main.log(ex);
        }
        return result;
    }

    /**
     * Write a byte array to the given file.
     * Writing binary data is significantly simpler than reading it.
     */
    public static void write(byte[] input, File file) {
        Main.log("Writing binary file...");
        try {
            OutputStream output = null;
            try {
                output = new BufferedOutputStream(new FileOutputStream(file));
                output.write(input);
            } finally {
                output.close();
            }
        } catch (FileNotFoundException ex) {
            Main.log("File not found.");
        } catch (IOException ex) {
            Main.log(ex);
        }
    }
}