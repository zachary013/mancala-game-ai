import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Mancala extends GameSearch {
    private int aiDifficulty; // 1 = easy, 2 = medium, 3 = hard

    public void setDifficulty(int level) {
        this.aiDifficulty = level;
    }

    private int player1HelpCount = 5;
    private int player2HelpCount = 5;

    @Override
    public boolean drawnPosition(Position p) {
        MancalaPosition pos = (MancalaPosition) p;
        return pos.isGameOver() && pos.getBoard()[6] == pos.getBoard()[13];
    }

    @Override
    public boolean wonPosition(Position p, boolean player) {
        MancalaPosition pos = (MancalaPosition) p;
        if (!pos.isGameOver()) return false;
        int playerStore = player ? 6 : 13;
        int opponentStore = player ? 13 : 6;
        return pos.getBoard()[playerStore] > pos.getBoard()[opponentStore];
    }

    @Override
    public float positionEvaluation(Position p, boolean player) {
        MancalaPosition pos = (MancalaPosition) p;
        int playerStore = player ? 6 : 13;
        int opponentStore = player ? 13 : 6;
        float storesDiff = pos.getBoard()[playerStore] - pos.getBoard()[opponentStore];

        // Adjust evaluation strategy based on difficulty
        switch (aiDifficulty) {
            case 1: // Easy - only considers stores difference
                return storesDiff;

            case 2: // Medium - considers stores and some pit weights
                int playerPitsSum = 0;
                int opponentPitsSum = 0;
                for (int i = 0; i < 6; i++) {
                    if (player) {
                        playerPitsSum += pos.getBoard()[i];
                        opponentPitsSum += pos.getBoard()[i + 7];
                    } else {
                        playerPitsSum += pos.getBoard()[i + 7];
                        opponentPitsSum += pos.getBoard()[i];
                    }
                }
                return storesDiff + 0.5f * (playerPitsSum - opponentPitsSum);

            case 3: // Hard - considers stores, pits, and strategic positions
                float evaluation = storesDiff * 2.0f; // Give more weight to stores

                // Add weight for stones in pits
                for (int i = 0; i < 6; i++) {
                    int playerSide = player ? i : i + 7;
                    int opponentSide = player ? i + 7 : i;

                    // Give more weight to pits closer to the store
                    float weight = (6 - i) / 6.0f;
                    evaluation += pos.getBoard()[playerSide] * weight;
                    evaluation -= pos.getBoard()[opponentSide] * weight;
                }

                // Extra points for having stones in pits that can land in store
                int storeDistance = player ? 6 : 13;
                for (int i = 0; i < 6; i++) {
                    int pit = player ? i : i + 7;
                    if (pos.getBoard()[pit] == (storeDistance - pit)) {
                        evaluation += 1.5f;
                    }
                }

                return evaluation;

            default:
                return storesDiff;
        }
    }

    @Override
    public void printPosition(Position p) {
        System.out.println(p.toString());
    }

    @Override
    public Position[] possibleMoves(Position p, boolean player) {
        MancalaPosition pos = (MancalaPosition) p;
        List<Position> moves = new ArrayList<>();
        int start = player ? 0 : 7;
        int end = player ? 5 : 12;

        for (int i = start; i <= end; i++) {
            if (pos.getBoard()[i] > 0) {
                MancalaPosition newPos = new MancalaPosition(pos);
                newPos.makeMove(new MancalaMove(i - start));
                moves.add(newPos);
            }
        }

        return moves.toArray(new Position[0]);
    }

    @Override
    public Position makeMove(Position p, boolean player, Move move) {
        MancalaPosition pos = new MancalaPosition((MancalaPosition) p);
        MancalaMove mMove = (MancalaMove) move;

        int actualPitIndex = player ? mMove.getPitIndex() : mMove.getPitIndex() + 7;

        if (!pos.isValidMove(actualPitIndex, player)) {
            System.out.println("Invalid move! Please try again.");
            return p;
        }

        pos.makeMove(mMove);
        return pos;
    }


    @Override
    public boolean reachedMaxDepth(Position p, int depth) {
        // Adjust search depth based on difficulty
        int maxDepth;
        switch (aiDifficulty) {
            case 1: // Easy
                maxDepth = 2;
                break;
            case 2: // Medium
                maxDepth = 4;
                break;
            case 3: // Hard
                maxDepth = 6;
                break;
            default:
                maxDepth = 4; // Default to medium
                break;
        }
        return depth >= maxDepth || ((MancalaPosition)p).isGameOver();
    }

    @Override
    public Move createMove() {
        return createMove(true);
    }

    public Move createMove(boolean isPlayer1) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter pit number " + (isPlayer1 ? "(1-6): " : "(7-12): "));
                int pit = scanner.nextInt();
                if (isPlayer1 && pit >= 1 && pit <= 6) {
                    return new MancalaMove(pit - 1); // Subtract 1 to convert to 0-based index
                } else if (!isPlayer1 && pit >= 7 && pit <= 12) {
                    return new MancalaMove(pit - 7); // Subtract 7 to convert to 0-based index
                } else {
                    System.out.println("Invalid pit. Choose between " + (isPlayer1 ? "1 and 6" : "7 and 12") + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
        }
    }

    public void displayGameRules() {
        System.out.println("\n=== MANCALA GAME RULES ===");
        System.out.println("1. The game board has 12 small pits (6 on each side) and 2 stores (one at each end)");
        System.out.println("2. Each small pit starts with 4 stones");
        System.out.println("3. On your turn:");
        System.out.println("   - Choose a pit from your side");
        System.out.println("   - Player 1: choose from pits 1-6");
        System.out.println("   - Player 2: choose from pits 7-12");
        System.out.println("   - Stones are distributed counter-clockwise, one in each pit");
        System.out.println("   - Skip your opponent's store but not your own");
        System.out.println("4. Special rules:");
        System.out.println("   - If last stone lands in your store, you get another turn");
        System.out.println("   - If last stone lands in an empty pit on your side,");
        System.out.println("     capture that stone and all stones in the opposite pit");
        System.out.println("5. Game ends when all pits on one side are empty");
        System.out.println("6. Player with the most stones in their store wins\n");
    }
public void playGameAgainstAI(Position startingPosition, boolean humanPlayFirst) {
    MancalaPosition currentPosition = (MancalaPosition) startingPosition;
    Scanner scanner = new Scanner(System.in);

    String difficultyLevel = "";
    switch (aiDifficulty) {
        case 1: difficultyLevel = "Easy"; break;
        case 2: difficultyLevel = "Medium"; break;
        case 3: difficultyLevel = "Hard"; break;
    }
    System.out.println("\nPlaying against AI (" + difficultyLevel + " difficulty)");

    // Initial AI move if human does not play first
    if (!humanPlayFirst) {
        System.out.println("\nAI is making its move...");
        List<Object> v = alphaBeta(0, startingPosition, PROGRAM);
        if (v.size() > 1) {
            currentPosition = (MancalaPosition) v.get(1);
        }
    }

    // Main game loop
    while (true) {
        printPosition(currentPosition);

        // Check for game over condition
        if (currentPosition.isGameOver()) {
            System.out.println("\n" + currentPosition.getGameResult(true));
            break;
        }

        // Human's turn
        if (currentPosition.isPlayerTurn()) {
            System.out.println("\nYour turn");
            System.out.println("Available moves: " + currentPosition.getAvailableMoves(true));
            System.out.println("Press 'S' to save the game or enter a pit number");

            String input = scanner.nextLine().trim().toUpperCase();

            // Handle save game functionality
            if (input.equals("S")) {
                System.out.print("Enter a name for your save file: ");
                String filename = scanner.nextLine();
                GameSaveManager.saveGame(currentPosition, filename);
                //System.out.println("Game saved successfully.");
                break; // Allow for another turn after saving
            }

            // Process human move
            try {
                int pitIndex = Integer.parseInt(input);
                Move move = createMove(); // Assuming createMove() uses pitIndex
                currentPosition = (MancalaPosition) makeMove(currentPosition, HUMAN, move);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a pit number or 'S' to save.");
                continue; // Restart loop for valid input
            }
        }
        // AI's turn
        else {
            System.out.println("\nAI is thinking...");
            List<Object> v = alphaBeta(0, currentPosition, PROGRAM);
            if (v.size() > 1) {
                currentPosition = (MancalaPosition) v.get(1);
                System.out.println("AI made its move.");
            } else {
                System.out.println(currentPosition.getGameResult(true));
                break; // End game if no valid moves are available
            }
        }
    }
}


    public void playGameHumanVsHuman(Position startingPosition) {
        MancalaPosition currentPosition = (MancalaPosition) startingPosition;
        Scanner scanner = new Scanner(System.in);

        // Display initial help information
        System.out.println("\nEach player has 5 help opportunities. Type 'H' to get AI suggestions.");
        System.out.println("Player 1 help remaining: " + player1HelpCount);
        System.out.println("Player 2 help remaining: " + player2HelpCount);

        while (true) {
            // Print current board state
            printPosition(currentPosition);

            // Check if game is over
            if (currentPosition.isGameOver()) {
                System.out.println("\n" + currentPosition.getGameResult(false));
                break;
            }

            // Determine current player
            boolean isPlayer1 = currentPosition.isPlayerTurn();
            String currentPlayer = isPlayer1 ? "Player 1" : "Player 2";
            System.out.println("\n" + currentPlayer + "'s turn");

            // Show available moves for current player
            System.out.println("Available moves: " + currentPosition.getAvailableMoves(isPlayer1));
            System.out.println("Help remaining: " + (isPlayer1 ? player1HelpCount : player2HelpCount));
            System.out.println("Press 'H' for help, 'S' to save, or enter a pit number");

            String input = scanner.nextLine().trim().toUpperCase();

            // Handle save game functionality
            if (input.equals("S")) {
                System.out.print("Enter a name for your save file: ");
                String filename = scanner.nextLine();
                GameSaveManager.saveGame(currentPosition, filename);
                break; // Allow for another turn after saving
            }

            // Handle help request
            if (input.equals("H")) {
                int helpCount = isPlayer1 ? player1HelpCount : player2HelpCount;
                if (helpCount > 0) {
                    // Use AI to suggest a move
                    List<Object> suggestion = alphaBeta(0, currentPosition, isPlayer1);
                    if (suggestion.size() > 1) {
                        MancalaPosition suggestedPosition = (MancalaPosition) suggestion.get(1);
                        // Find which move led to this position
                        int suggestedMove = findSuggestedMove(currentPosition, suggestedPosition, isPlayer1);
                        System.out.println("AI suggests moving from pit " + (suggestedMove + (isPlayer1 ? 1 : 7)));

                        // Decrease help count
                        if (isPlayer1) {
                            player1HelpCount--;
                        } else {
                            player2HelpCount--;
                        }
                    }
                } else {
                    System.out.println("No help opportunities remaining!");
                }
                continue;
            }

            // Get move from current player
            Move move;
            while (true) {
                move = createMove(isPlayer1);

                // Adjust move index for Player 2's side of the board
                int actualPitIndex = isPlayer1 ?
                        ((MancalaMove)move).getPitIndex() :
                        ((MancalaMove)move).getPitIndex() + 7;

                // Validate move
                if (currentPosition.isValidMove(actualPitIndex, isPlayer1)) {
                    break;
                }

                System.out.println("Invalid move. Please choose a non-empty pit from your side.");
            }

            // Make the move
            currentPosition = (MancalaPosition) makeMove(currentPosition, HUMAN, move);
        }
    }

    private int findSuggestedMove(MancalaPosition current, MancalaPosition suggested, boolean isPlayer1) {
        // Try each possible move and compare with suggested position
        int start = isPlayer1 ? 0 : 7;
        int end = isPlayer1 ? 5 : 12;

        for (int i = start; i <= end; i++) {
            if (current.getBoard()[i] > 0) {
                MancalaPosition testPos = new MancalaPosition(current);
                testPos.makeMove(new MancalaMove(i - start));
                if (comparePositions(testPos, suggested)) {
                    return i - start;
                }
            }
        }
        return -1;
    }

    private boolean comparePositions(MancalaPosition pos1, MancalaPosition pos2) {
        int[] board1 = pos1.getBoard();
        int[] board2 = pos2.getBoard();
        for (int i = 0; i < board1.length; i++) {
            if (board1[i] != board2[i]) {
                return false;
            }
        }
        return true;
    }
}