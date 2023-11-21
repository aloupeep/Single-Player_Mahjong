# Single-Player Mahjong Term-project

## Proposal

The project I propose to design this term is a single-player mahjong game
that follows a modified version of only the *basic rules* that applies to almost all local mahjong variants 
in the world. For reference to such mentioned rules, see https://www.coololdgames.com/tile-games/mahjong/
but without the calling parts as calling is impossible for a single-player game. A completed version
of this application should be able to allow a player to draw and an arbitrary number of tiles
until the hand is completed (with 4 melds and a pair in hand) or the player decides to end the game. 
The player should be able to save and exit the game at any time to continue the game later.   

People who wish to play mahjong in solitary will be able to use this application to
enjoy the game without any stress from competition with other players/ai. Also, since a single-player
mahjong game takes only several minutes on average (no proof, based on estimation), 
people who don't have a lot of time will also be able to use the application. 
Personally, mahjong was always one of my favorite games ever since it was introduced to me 
around age 10, and even now, I would often go to the UBC
mahjong club to play one or two rounds of mahjong, which is why I chose to propose this project.
Due to the constraint of time and my coding skill, I think it would be beyond my capabilities to 
attempt to capture the multiplayer aspect of mahjong, hence my proposal of a single-player version.


## User Stories
- As a user, I want to be able to draw thirteen tiles at the beginning of a game 
and draw one tile at the beginning of each turn
- As a user, I want to be able to discard one tile in my hand and add it to a list of previously 
discarded tiles of arbitrary size after drawing one tile to end the current turn
- As a user, I want to be able to remove one tile from my discards and send it to the shadow realm 
an arbitrary number of times during my turn
- As a user, I want to be able to view the list of previously discarded tiles during any turn.
- As a user, I want to be able to view my current hand when I'm declaring a win or
drawing/discarding a tile.
- As a user, I want to be able to declare that the hand is completed manually.
- As a user, I want to be able to close and save a game at any time to come back to it later
- As a user, I want to be able to reload a previous game when starting the mahjong application

## Instructions for Grader
- To add a discard to the list of previously discarded tiles, select any tile from the current hand
and press the "discard" button
- To remove a discard from the list of previously discarded tiles, select any tile from the current
discards and press the "remove discard" button
- For the visual component added to my project, press the declare win button; after
minimizing and maximizing the window again, the fail image will most probably appear, which is a sign
that the declaration of win has failed (if you are curious about the win image, just load the previous
hand; the saved hand is a valid winning hand).
- To save the state of the mahjong game, press the save button at the bottom of the screen (most probably)
- To reload the state of a saved mahjong game, press the load button at the bottom of the screen



