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

## Phase 4: Task 2
Mon Nov 27 21:04:25 PST 2023\
Tile added to discards\
Mon Nov 27 21:04:25 PST 2023\
Tile added to discards\
Mon Nov 27 21:04:28 PST 2023\
Discard Tile removed\
Mon Nov 27 21:04:29 PST 2023\
Tile added to discards\
Mon Nov 27 21:04:29 PST 2023\
Tile added to discards\
Mon Nov 27 21:04:31 PST 2023\
Discard Tile removed

## Phase 4: Task 3
If I had more time to work on the project, one potential improvement would be to reduce the number of associations and
thus reduce the coupling between some different classes. For example, both the MahjongAppGUI and the MahjongAppFrameGUI
has a field of DiscardGUI, while MahjongAppFrameGUI also has a field of MahjongAppGUI. This suggests that at least one
of the associations arrows can be broken without changing the functionality of the program. Another potential improvement
would be to apply the Singleton pattern on the JsonReader and the JsonWriter classes since with the current state of my
program, it really only needs one shared JsonReader/Writer for all of my classes. One additional possible refactoring is 
to apply the Observer pattern on the EventLog
class. To be more specific, since the EventLog class is "observing", in other words, changing with respect to, the Hand
class and the DiscardedTiles class, I can potentially make a new class named Observable or Subject with both Hand and
DiscardedTiles extending it, and a new Observer interface with the EventLog implementing. Although that may seem
unnecessary for my current state of the program, applying the pattern will reduce coupling and give more flexibility for
potential additional features added in the future. 

