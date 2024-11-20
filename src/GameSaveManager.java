import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameSaveManager {
    private static final String SAVE_DIRECTORY = "mancala_saves";
    private static final String FILE_EXTENSION = ".ser";

    static {
        try {
            Files.createDirectories(Paths.get(SAVE_DIRECTORY));
        } catch (IOException e) {
            System.err.println("Failed to create saves directory: " + e.getMessage());
        }
    }

    public static void saveGame(MancalaPosition position, String filename) {
        if (!filename.endsWith(FILE_EXTENSION)) {
            filename += FILE_EXTENSION;
        }

        Path fullPath = Paths.get(SAVE_DIRECTORY, filename);

        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(Files.newOutputStream(fullPath)))) {
            out.writeObject(position);
            System.out.println("Game saved successfully to: " + fullPath);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public static void quickSave(MancalaPosition position) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        saveGame(position, "quicksave_" + timestamp);
    }

    public static MancalaPosition loadGame(String filename) {
        if (!filename.endsWith(FILE_EXTENSION)) {
            filename += FILE_EXTENSION;
        }

        Path fullPath = Paths.get(SAVE_DIRECTORY, filename);

        if (!Files.exists(fullPath)) {
            System.err.println("Save file not found: " + fullPath);
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(Files.newInputStream(fullPath)))) {
            MancalaPosition position = (MancalaPosition) in.readObject();
            System.out.println("Game loaded successfully from: " + fullPath);
            return position;
        } catch (IOException e) {
            System.err.println("Error loading game: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Save file format is incompatible");
        }
        return null;
    }

    public static String[] listSaveFiles() {
        try {
            return Files.list(Paths.get(SAVE_DIRECTORY))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(FILE_EXTENSION))
                    .toArray(String[]::new);
        } catch (IOException e) {
            System.err.println("Error listing save files: " + e.getMessage());
            return new String[0];
        }
    }

    public static boolean deleteSaveFile(String filename) {
        if (!filename.endsWith(FILE_EXTENSION)) {
            filename += FILE_EXTENSION;
        }

        try {
            Path savePath = Paths.get(SAVE_DIRECTORY, filename);
            boolean deleted = Files.deleteIfExists(savePath);
            if (deleted) {
                System.out.println("Save file deleted: " + filename);
            } else {
                System.out.println("Save file not found: " + filename);
            }
            return deleted;
        } catch (IOException e) {
            System.err.println("Error deleting save file: " + e.getMessage());
            return false;
        }
    }
}