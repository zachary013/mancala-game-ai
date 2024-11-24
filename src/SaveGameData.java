import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

enum Gamemode {
    H_vs_H, // For Human vs Human  game
    H_vs_AI,   // For a Human vs AI game
}

class SaveGameData implements Serializable {

    private static final long serialVersionUID = 1L;

    // The actual game state
    private final MancalaPosition position;


    private  Date saveDate ;
    // The game mode
    public  Gamemode gamemode ;

    // Constructor that takes both the game position and save time
    public SaveGameData(MancalaPosition position, LocalDateTime saveDate , Gamemode gamemode) {
        this.position = position;
        this.gamemode = gamemode;

    }

    // Getter for the game position
    public MancalaPosition getPosition() {
        return position;
    }
    public Gamemode getGamemode() {
        return gamemode;
    }

}