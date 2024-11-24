import java.util.ArrayList;
import java.util.List;

abstract class GameSearch {
    // Constants for player types
    public static final boolean DEBUG = false;
    public static final boolean PROGRAM = false;
    public static final boolean HUMAN = true;

    // Abstract methods to be implemented by specific game classes
    public abstract boolean drawnPosition(Position p);
    public abstract boolean wonPosition(Position p, boolean player);
    public abstract float positionEvaluation(Position p, boolean player);
    public abstract void printPosition(Position p);
    public abstract Position[] possibleMoves(Position p, boolean player);
    public abstract Position makeMove(Position p, boolean player, Move move);
    public abstract boolean reachedMaxDepth(Position p, int depth);
    public abstract Move createMove();


    /**
     * Initiates the Alpha-Beta search algorithm
     * @param depth Current depth in the game tree
     * @param p Current game position
     * @param player Current player (true for maximizing, false for minimizing)
     * @return List containing the best evaluation and corresponding move
     */
    protected List<Object> alphaBeta(int depth, Position p, boolean player) {
        return alphaBetaHelper(depth, p, player, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }


    /**
     * Recursive helper method for Alpha-Beta pruning algorithm
     * @param depth Current depth in the game tree
     * @param p Current game position
     * @param player Current player (true for maximizing, false for minimizing)
     * @param alpha Best value for maximizing player
     * @param beta Best value for minimizing player
     * @return List containing the best evaluation and corresponding move
     */
    protected List<Object> alphaBetaHelper(int depth, Position p, boolean player, float alpha, float beta) {
        // Base case: if max depth reached or terminal node, return position evaluation
        if (reachedMaxDepth(p, depth)) {
            List<Object> result = new ArrayList<>();
            result.add(positionEvaluation(p, player));
            result.add(null);
            return result;
        }

        List<Object> best = new ArrayList<>();
        Position[] moves = possibleMoves(p, player);

        // Evaluate each possible move
        for (Position move : moves) {
            List<Object> v2 = alphaBetaHelper(depth + 1, move, !player, -beta, -alpha);
            float value = -((Float) v2.get(0));

            // Update alpha and best move if a better move is found
            if (value > alpha) {
                alpha = value;
                best.clear();
                best.add(move);
                best.addAll(v2.subList(1, v2.size()));

                if (alpha >= beta) {
                    break;
                }
            }
        }

        // Return the best evaluation and corresponding move
        List<Object> result = new ArrayList<>();
        result.add(alpha);
        result.addAll(best);
        return result;
    }
}