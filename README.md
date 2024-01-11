"Santorini" is a captivating board game which challenges players to strategically construct buildings on a 5x5 grid. 
With the goal of being the first to reach the third level, players employ clever movement and building tactics, enhanced by the unique powers of god cards. 

## Starting a Game

To start the game, first navigate to the back-end folder and enter on the command line:

### `mvn exec:exec`

Once the program starts running successfully, navigate to the front-end folder and enter on the command line:

### `npm start`

First, choose the version of the game, classic or with god cards. For God card version, both players select the God cards and then the game starts.
The instructions start with initializing the workers. Players can initialize their workers by simply clicking on the squares.

To perform move and build, click on the square that has the worker that you want to move, then choose a square adjacent to the chosen
worker to move. Then, choose a square adjacent to the worker's new location to build. 

Any invalid move/build/initialization will be rejected, and the game will not progress until a valid action is taken.

If you wish to pass an optional move, click on pass. 

To restart the game at any time, click on the "New Game" button. 
