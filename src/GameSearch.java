import java.util.ArrayList;
import java.util.List;

abstract class GameSearch {
    public static final boolean DEBUG = false;
    public static final boolean PROGRAM = false;
    public static final boolean HUMAN = true;

    public abstract boolean drawnPosition(Position p);
    public abstract boolean wonPosition(Position p, boolean player);
    public abstract float positionEvaluation(Position p, boolean player);
    public abstract void printPosition(Position p);
    public abstract Position[] possibleMoves(Position p, boolean player);
    public abstract Position makeMove(Position p, boolean player, Move move);
    public abstract boolean reachedMaxDepth(Position p, int depth);
    public abstract Move createMove();

    protected List<Object> alphaBeta(int depth, Position p, boolean player) {
        return alphaBetaHelper(depth, p, player, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    protected List<Object> alphaBetaHelper(int depth, Position p, boolean player, float alpha, float beta) {
        if (reachedMaxDepth(p, depth)) {
            List<Object> result = new ArrayList<>();
            result.add(positionEvaluation(p, player));
            result.add(null);
            return result;
        }

        List<Object> best = new ArrayList<>();
        Position[] moves = possibleMoves(p, player);

        for (Position move : moves) {
            List<Object> v2 = alphaBetaHelper(depth + 1, move, !player, -beta, -alpha);
            float value = -((Float) v2.get(0));

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

        List<Object> result = new ArrayList<>();
        result.add(alpha);
        result.addAll(best);
        return result;
    }
}