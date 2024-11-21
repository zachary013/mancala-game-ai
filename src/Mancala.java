import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Mancala extends GameSearch {
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

        // Consider stones in player's pits as potential points
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

        // Weight store stones more heavily than pit stones
        return storesDiff + 0.5f * (playerPitsSum - opponentPitsSum);
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
        return depth >= 6 || ((MancalaPosition)p).isGameOver();
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

        if (!humanPlayFirst) {
            System.out.println("\nAI is making its move...");
            List<Object> v = alphaBeta(0, startingPosition, PROGRAM);
            if (v.size() > 1) {
                currentPosition = (MancalaPosition) v.get(1);
            }
        }

        while (true) {
            printPosition(currentPosition);

            if (currentPosition.isGameOver()) {
                System.out.println("\n" + currentPosition.getGameResult(true));
                break;
            }

            // Human's turn
            if (currentPosition.isPlayerTurn()) {
                System.out.println("\nYour turn");
                System.out.println("Available moves: " + currentPosition.getAvailableMoves(true));
                Move move = createMove();
                currentPosition = (MancalaPosition) makeMove(currentPosition, HUMAN, move);
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
                    break;
                }
            }
        }
    }

    public void playGameHumanVsHuman(Position startingPosition) {
        MancalaPosition currentPosition = (MancalaPosition) startingPosition;

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
}