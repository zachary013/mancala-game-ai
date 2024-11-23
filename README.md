# Mancala Game with AI

A Java implementation of the traditional African board game Mancala, featuring AI opponents and various game strategies. This project implements adversarial search algorithms to create an intelligent computer opponent and provide move suggestions to players.

## Authors

- Zakariae Azarkan ([zachary013](https://github.com/zachary013))
- Salah Eljably ([Sam-Jab](https://github.com/Sam-Jab))

## Project Overview

This implementation of Mancala goes beyond the traditional two-player game by incorporating artificial intelligence algorithms for computer opponents and a move suggestion system. The game features multiple difficulty levels and different AI strategies.

## Project Structure

```
MancalaGame/
├── src/
│   ├── GameSaveManager.java    # Handles saving and loading game states
│   ├── GameSearch.java         # Implements game tree search algorithms
│   ├── Mancala.java           # Core game logic and rules
│   ├── MancalaGame.java       # Main game controller
│   ├── MancalaMove.java       # Move validation and execution
│   ├── MancalaPosition.java   # Board state representation
│   ├── Move.java             # Abstract move interface
│   └── Position.java         # Abstract position interface
├── mancala_saves/            # Directory for saved games
└── .gitignore
```

## Features

- **Multiple Game Modes**:
  - Player vs Player
  - Player vs AI
  - Option to request AI move suggestions

- **AI Implementation**:
  - Alpha-Beta pruning algorithm (optimized version)
  - Multiple difficulty levels
  - Various heuristic evaluations
  - Limited number of available hints per game

- **Game Management**:
  - Save and load game states
  - Replay saved games
  - Comprehensive move validation
  - Complete rule implementation

## Game Rules

1. **Board Setup**:
   - 6 small pits per player
   - 1 Mancala (large pit) at each end
   - Initially 4 stones in each small pit

2. **Gameplay**:
   - Players distribute stones counterclockwise
   - Skip opponent's Mancala during distribution
   - Extra turn when last stone lands in own Mancala
   - Capture opponent's stones when last stone lands in empty pit

3. **Victory Conditions**:
   - Game ends when all pits on one side are empty
   - Player with most stones wins

## AI Features

### Heuristic Functions
The game implements multiple heuristic evaluations:
1. Stone Difference: Evaluates based on the difference in stones between players
2. Position Weight: Considers the strategic value of stone positions

### Difficulty Levels
- Easy: Limited search depth, basic heuristic
- Medium: Moderate search depth, combined heuristics
- Hard: Deep search, advanced heuristic combination

## Technical Requirements

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA recommended)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/zachary013/mancala-game-ai.git
```

2. Open in your Java IDE
3. Build and run MancalaGame.java

## Game Controls

- Select pits using number inputs (1-6)
- Special commands:
  - 'S': Save game
  - 'L': Load game
  - 'H': Request hint (limited per game)
  - 'Q': Quit game

## Implementation Details

### Core Components

- **MancalaGame**: Main controller handling game flow and user interaction
- **GameSearch**: Implements the optimized Alpha-Beta pruning algorithm
- **MancalaPosition**: Manages board state and position evaluation
- **GameSaveManager**: Handles serialization of game states

### AI Implementation

The AI system uses an optimized version of the Alpha-Beta pruning algorithm as described in the course material (slide 18). The implementation includes:
- Dynamic search depth based on game phase
- Move ordering optimization
- Multiple heuristic evaluations
- Iterative deepening for time management

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
- Developed as part of an AI systems course project
- Implements adversarial search algorithms from course materials
