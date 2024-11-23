# Mancala Game with AI

A Java implementation of the traditional African board game Mancala, featuring AI opponents with multiple difficulty levels and various game strategies. This project implements the Alpha-Beta pruning algorithm to create an intelligent computer opponent and provide move suggestions to players.

## Authors

- Zakariae Azarkan ([zachary013](https://github.com/zachary013))
- Salaheddine Eljably ([Sam-Jab](https://github.com/Sam-Jab))

## Project Overview

This implementation of Mancala enhances the traditional two-player game with artificial intelligence capabilities. The game features multiple difficulty levels, AI-powered move suggestions, and the ability to save and load game states.

## Game Preview

<div align="center">

### Traditional Mancala Board Layout
![Mancala Game Board Preview](/screenshots/mancala1.jpg)

### Game Implementation Interface
![Terminal View](/screenshots/mancala2.png)

</div>

## Project Structure

```
MancalaGame/
├── src/
│   ├── GameSaveManager.java    # Handles saving and loading game states
│   ├── GameSearch.java         # Implements Alpha-Beta pruning algorithm
│   ├── Mancala.java           # Core game logic and AI strategies
│   ├── MancalaGame.java       # Main game controller and UI
│   ├── MancalaMove.java       # Move representation and validation
│   ├── MancalaPosition.java   # Board state and evaluation
│   ├── Move.java             # Abstract move interface
│   └── Position.java         # Abstract position interface
├── mancala_saves/            # Directory for saved games
└── .gitignore
```

## Features

### Multiple Game Modes
- Player vs AI (with difficulty selection)
- Player vs Player (with AI move suggestions)
- Save/Load game functionality

### AI Implementation
- **Algorithm**: Alpha-Beta pruning with position evaluation
- **Difficulty Levels**:
  - Easy (Depth: 2)
    - Basic evaluation focusing on stone count differences
    - Simple decision-making suitable for beginners
  - Medium (Depth: 4)
    - Enhanced evaluation considering both stones and pit positions
    - Balanced strategy for intermediate players
  - Hard (Depth: 6)
    - Advanced evaluation with weighted positions
    - Strategic consideration of store-landing moves
    - Sophisticated capture move analysis

### Help System
- 5 AI move suggestions available per player
- Available in both PvP and PvAI modes
- Suggests optimal moves based on current board state

## Game Rules

1. **Board Setup**:
   - 6 small pits per player
   - 1 Mancala (store) at each end
   - 4 stones in each small pit initially

2. **Gameplay**:
   - Choose pits numbered 1-6 (Player 1) or 7-12 (Player 2)
   - Stones are distributed counterclockwise
   - Skip opponent's store during distribution
   - Extra turn when last stone lands in own store
   - Capture opponent's stones when last stone lands in empty pit on own side

3. **Special Features**:
   - 'S': Save current game state
   - 'H': Request AI move suggestion (limited to 5 per player)

## Technical Details

### AI Strategy Implementation
```java
// Difficulty-based evaluation weights
case 1: // Easy
    return storesDiff;
case 2: // Medium
    return storesDiff + 0.5f * (playerPitsSum - opponentPitsSum);
case 3: // Hard
    // Advanced evaluation with position weights
    float evaluation = storesDiff * 2.0f;
    // Consider pit positions and potential store-landing moves
    // Weight stones based on proximity to store
    // Extra points for capture opportunities
```

### Search Depth by Difficulty
- Easy: 2 levels deep
- Medium: 4 levels deep
- Hard: 6 levels deep

## Installation

1. Clone the repository:
```bash
git clone https://github.com/zachary013/mancala-game-ai.git
```

2. Open in your Java IDE
3. Build and run MancalaGame.java

## Requirements

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA recommended)

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Based on the classic Mancala board game
- Implements adversarial search algorithms with Alpha-Beta pruning
- Developed as part of an AI systems course project
