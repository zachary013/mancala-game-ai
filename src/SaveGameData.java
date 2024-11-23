import java.io.Serializable;
import java.time.LocalDateTime;

enum Gamemode {
    H_vs_H, // For Human vs Human  game
    H_vs_AI,   // For a Human vs AI game
}

class SaveGameData implements Serializable {
    private static final long serialVersionUID = 1L;

    // The actual game state
    private final MancalaPosition position;

    // The game mode
    private  Gamemode gamemode = Gamemode.H_vs_AI;

    // Constructor that takes both the game position and save time
    public SaveGameData(MancalaPosition position, LocalDateTime saveDate) {
        this.position = position;
    }

    // Getter for the game position
    public MancalaPosition getPosition() {
        return position;
    }

}