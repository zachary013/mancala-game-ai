# Mancala Game

A Java implementation of the classic board game Mancala. This game allows players to compete against each other in the traditional African stone-moving strategy game.

## Project Structure

```
MancalaGame/
├── src/
│   ├── GameSaveManager.java
│   ├── GameSearch.java
│   ├── Mancala.java
│   ├── MancalaGame.java
│   ├── MancalaMove.java
│   ├── MancalaPosition.java
│   ├── Move.java
│   └── Position.java
├── mancala_saves/
└── .gitignore
```

## Features

- Full implementation of Mancala game rules
- Game state saving and loading functionality
- Move validation and execution
- Position tracking system
- Game state search capabilities

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA recommended)

## Installation

1. Clone the repository:
```bash
git clone [your-repository-url]
```

2. Open the project in your preferred Java IDE

3. Build the project to resolve dependencies

## How to Play

1. Run the `MancalaGame` class to start the game
2. The game board consists of:
   - 12 small pits (6 for each player)
   - 2 Mancala stores (one at each end)
3. Game Rules:
   - Players take turns moving stones from their pits
   - Each turn, a player selects a pit on their side and distributes stones counterclockwise
   - Special rules apply for landing in your Mancala or empty pits
   - Game ends when all pits on one side are empty

## Key Components

- `MancalaGame`: Main game logic and flow controller
- `Mancala`: Core game rules and board state management
- `MancalaPosition`: Represents board positions and state
- `MancalaMove`: Handles move validation and execution
- `GameSaveManager`: Manages saving and loading game states
- `GameSearch`: Implements game state searching algorithms

## Game Save System

- Games can be saved and loaded using the `GameSaveManager`
- Save files are stored in the `mancala_saves` directory
- Each save includes the complete game state and player information

## Project Architecture

The game follows an object-oriented design with clear separation of concerns:
- Game logic is separated from UI components
- Move and Position classes provide abstraction for game state management
- Search capabilities are implemented independently of core game logic

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

## License

[Your chosen license]

## Author

[Your name/organization]
