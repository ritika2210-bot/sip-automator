package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File read/write helper for persisting data between sessions.
 * Saves to and reads from the /data directory.
 */
public class FileHandler {

    private static final String DATA_DIR = "data/";

    /** Writes a full text string to a file (overwrites existing content). */
    public static void writeToFile(String filename, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_DIR + filename))) {
            writer.write(data);
        } catch (IOException e) {
            System.err.println("[FileHandler] Error writing to " + filename + ": " + e.getMessage());
        }
    }

    /** Reads all lines from a file and returns them as a List. */
    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(DATA_DIR + filename);
        if (!file.exists()) return lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) lines.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("[FileHandler] Error reading " + filename + ": " + e.getMessage());
        }
        return lines;
    }

    /** Appends a single line to a file (creates if not exists). */
    public static void appendToFile(String filename, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_DIR + filename, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("[FileHandler] Error appending to " + filename + ": " + e.getMessage());
        }
    }

    /** Checks whether a file exists in the data directory. */
    public static boolean fileExists(String filename) {
        return new File(DATA_DIR + filename).exists();
    }
}
