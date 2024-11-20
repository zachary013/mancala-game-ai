import java.io.Serializable;

class MancalaPosition extends Position implements Serializable {
    private int[] board;
    private boolean playerTurn;

    public MancalaPosition() {
        board = new int[14];
        for (int i = 0; i < 14; i++) {
            if (i != 6 && i != 13) {
                board[i] = 4;
            }
        }
        playerTurn = true;
    }

    public MancalaPosition(MancalaPosition other) {
        this.board = other.board.clone();
        this.playerTurn = other.playerTurn;
    }

    public int[] getBoard() {
        return board;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean isValidMove(int pit, boolean isPlayer1) {
        int startIndex = isPlayer1 ? 0 : 7;
        int endIndex = isPlayer1 ? 5 : 12;
        return pit >= startIndex && pit <= endIndex && board[pit] > 0;
    }

    public String getAvailableMoves(boolean isPlayer1) {
        StringBuilder moves = new StringBuilder("(");
        int startIndex = isPlayer1 ? 0 : 7;
        int endIndex = isPlayer1 ? 5 : 12;
        boolean first = true;

        for (int i = startIndex; i <= endIndex; i++) {
            if (board[i] > 0) {
                if (!first) {
                    moves.append(", ");
                }
                moves.append(isPlayer1 ? i : i - 7);
                first = false;
            }
        }
        moves.append(")");
        return moves.toString();
    }

    public void makeMove(MancalaMove move) {
        int startPit = playerTurn ? move.getPitIndex() : move.getPitIndex() + 7;

        if (!isValidMove(startPit, playerTurn)) {
            return;
        }

        int stones = board[startPit];
        board[startPit] = 0;
        int currentPit = startPit;

        while (stones > 0) {
            currentPit = (currentPit + 1) % 14;
            if ((playerTurn && currentPit == 13) || (!playerTurn && currentPit == 6)) {
                continue;
            }
            board[currentPit]++;
            stones--;
        }

        if (currentPit != 6 && currentPit != 13) {
            if (board[currentPit] == 1) {
                int oppositePit = 12 - currentPit;
                if ((playerTurn && currentPit <= 5) || (!playerTurn && currentPit >= 7)) {
                    if (board[oppositePit] > 0) {
                        int playerStore = playerTurn ? 6 : 13;
                        board[playerStore] += board[oppositePit] + 1;
                        board[oppositePit] = 0;
                        board[currentPit] = 0;
                    }
                }
            }
        }

        if (!((playerTurn && currentPit == 6) || (!playerTurn && currentPit == 13))) {
            playerTurn = !playerTurn;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n     12  11  10   9   8   7\n");
        sb.append("   +---+---+---+---+---+---+\n");
        sb.append("13 |");
        for (int i = 12; i >= 7; i--) {
            sb.append(String.format("%2d |", board[i]));
        }
        sb.append("\n");
        sb.append("   |---+---+---+---+---+---|\n");
        sb.append(" P2|");
        sb.append(String.format("%2d ", board[13]));
        sb.append("|");
        sb.append("   ".repeat(4));
        sb.append(String.format("|%2d ", board[6]));
        sb.append("| P1\n");
        sb.append("   |---+---+---+---+---+---|\n");
        sb.append("   |");
        for (int i = 0; i <= 5; i++) {
            sb.append(String.format("%2d |", board[i]));
        }
        sb.append("\n");
        sb.append("   +---+---+---+---+---+---+\n");
        sb.append("      0   1   2   3   4   5\n");
        return sb.toString();
    }

    public boolean isGameOver() {
        boolean player1Empty = true;
        boolean player2Empty = true;

        // Check Player 1's pits
        for (int i = 0; i <= 5; i++) {
            if (board[i] > 0) {
                player1Empty = false;
                break;
            }
        }

        // Check Player 2's pits
        for (int i = 7; i <= 12; i++) {
            if (board[i] > 0) {
                player2Empty = false;
                break;
            }
        }

        // If either side is empty, collect remaining stones
        if (player1Empty || player2Empty) {
            collectRemainingStones();
            return true;
        }

        return false;
    }

    private void collectRemainingStones() {
        for (int i = 0; i <= 5; i++) {
            board[6] += board[i];
            board[i] = 0;
        }

        for (int i = 7; i <= 12; i++) {
            board[13] += board[i];
            board[i] = 0;
        }
    }

    public String getGameResult(boolean isAIGame) {
        if (!isGameOver()) return "Game is still in progress";

        if (board[6] > board[13]) {
            return isAIGame
                    ? String.format("Game Over! Human wins with %d stones vs AI's %d stones!", board[6], board[13])
                    : String.format("Game Over! Player 1 wins with %d stones vs Player 2's %d stones!", board[6], board[13]);
        } else if (board[13] > board[6]) {
            return isAIGame
                    ? String.format("Game Over! AI wins with %d stones vs Human's %d stones!", board[13], board[6])
                    : String.format("Game Over! Player 2 wins with %d stones vs Player 1's %d stones!", board[13], board[6]);
        } else {
            return String.format("Game Over! It's a draw! Both players have %d stones.", board[6]);
        }
    }
}
