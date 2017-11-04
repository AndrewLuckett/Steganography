package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import main.Main;

public class BytesStreamsAndFiles {

    /**
     * Read the given binary file, and return its contents as a byte array.
     */
    public static byte[] read(File file) throws IOException {
        Main.log(file.length());

        byte[] result = Files.readAllBytes(file.toPath());

        Main.log("Read");

        return result;
    }

    /**
     * Write a byte array to the given file.
     * Writing binary data is significantly simpler than reading it.
     */
    public static void write(byte[] input, File file) throws IOException {
        Main.log("Writing binary file...");

        Files.write(file.toPath(), input);
        Main.log("Written");

    }
}