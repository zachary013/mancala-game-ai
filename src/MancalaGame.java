import java.util.Scanner;

public class MancalaGame {
    private static Mancala game;
    private static MancalaPosition currentPosition;
    public static int gamemode;

    public static void main(String[] args) {
        game = new Mancala();
        currentPosition = new MancalaPosition();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to Mancala!");
            System.out.print("Would you like to see the rules? (y/n): ");
            if (scanner.nextLine().toLowerCase().startsWith("y")) {
                game.displayGameRules();
            }

            int gameMode = getGameMode(scanner);

            switch (gameMode) {
                case 1:
                    playAIGame(scanner);
                    break;
                case 2:
                    playHumanVsHumanGame();
                    break;
                case 3:
                    loadSavedGame(scanner);
                    break;
                case 4:
                    listSavedGames();
                    break;
                case 5:
                    System.out.println("Exiting the game. Goodbye!");
                    scanner.close();
                    return;
            }

            System.out.print("Do you want to return to the main menu? (y/n): ");
            if (!scanner.nextLine().toLowerCase().startsWith("y")) {
                break;
            }
        }

        scanner.close();
    }

    public static int getGameMode(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nChoose game mode:");
                System.out.println("1. Play against AI");
                System.out.println("2. Play against another human");
                System.out.println("3. Load saved game");
                System.out.println("4. List saved games");
                System.out.println("5. Exit");
                System.out.print("Enter your choice (1-5): ");

                int mode = Integer.parseInt(scanner.nextLine());
                if (mode >= 1 && mode <= 5) {
                    if(mode == 2 || mode == 1) {
                        gamemode = mode;
                    }
                    return mode;
                }
                System.out.println("Invalid choice. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void playAIGame(Scanner scanner) {
        int difficulty = getDifficulty(scanner);
        game.setDifficulty(difficulty);

        System.out.print("Would you like to go first? (y/n): ");
        boolean humanFirst = scanner.nextLine().toLowerCase().startsWith("y");

        game.playGameAgainstAI(currentPosition, humanFirst);
    }

    //Setting game difficulty
    private static int getDifficulty(Scanner scanner) {
        while (true) {
            try {
                System.out.println("\nChoose AI difficulty:");
                System.out.println("1. Easy");
                System.out.println("2. Medium");
                System.out.println("3. Hard");
                System.out.print("Enter difficulty (1-3): ");

                int difficulty = Integer.parseInt(scanner.nextLine());
                if (difficulty >= 1 && difficulty <= 3) {
                    return difficulty;
                }
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void playHumanVsHumanGame() {
        game.playGameHumanVsHuman(currentPosition);
    }

    private static void loadSavedGame(Scanner scanner) {
        System.out.print("Enter the filename of the saved game: ");
        String filename = scanner.nextLine();
        SaveGameData Game = GameSaveManager.loadGame(filename);
        MancalaPosition savedGame = Game.getPosition();
        if (savedGame != null) {
            System.out.print("Continue this saved game? (y/n): ");
            if (scanner.nextLine().toLowerCase().startsWith("y")) {
                currentPosition = savedGame;
                if(Game.getGamemode() == Gamemode.H_vs_H){
                    game.playGameHumanVsHuman(currentPosition);
                }
                else game.playGameAgainstAI(currentPosition, true);
            }
        }
    }

    private static void listSavedGames() {
        String[] savedGames = GameSaveManager.listSaveFiles();
        if (savedGames.length == 0) {
            System.out.println("No saved games found.");
        } else {
            System.out.println("Saved games:");
            for (String game : savedGames) {
                System.out.println("- " + game);
            }
        }
    }

}