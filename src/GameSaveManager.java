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
        filename = filename.replaceAll("[^a-zA-Z0-9.-]", "_");

        Path fullPath = Paths.get(SAVE_DIRECTORY, filename);

        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(Files.newOutputStream(fullPath)))) {
            SaveGameData saveData = new SaveGameData(position, LocalDateTime.now());
            out.writeObject(saveData);
            System.out.println("Game saved successfully to: " + fullPath);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }
    public static SaveGameData loadGame(String filename) {
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
            SaveGameData saveData = (SaveGameData) in.readObject();
            System.out.println("Game loaded successfully from: " + fullPath);
            return saveData;
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

}