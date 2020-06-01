=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: annipan
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections
A Collection<CleverBug> will be used to keep track of the CleverBugs that are currently alive. 
At the beginning of each round, some random number of CleverBugs will be generated，and each of them will be put into the collection. 
When the player kills a bug, that bug would be removed from the collection. There’s also a similar Collection<DumbBug> that keeps track of the DumbBugs that are currently alive.
A Collection<Snack> will be used to keep track of the snacks that are still on the floor. At the beginning of the game, a reasonable number of snacks will be generated and put into the collection. When a snack is either eaten by the bug or collected by the player, it is removed from the collection.
A Collection<Bullet> will be used to keep track of the pesticide(or bullets). A bullet is added to this collection when a player fires one, and will be removed from this collection when it moves out of the screen area or hit a bug. 
  
  2. File I/O
After the player finishes a round, the score he got in the round, along with the time he spent in this round, will be written into a text file. 
The RecordIterator, which implements the Iterator interface, is responsible for reading the data we stored line by line.
When a GameCourt object is initialized, by calling readFromFile() method, the data from previous games will be stored in the form (score, time) in a field called gameRecords. The displayHighestScore() method would take data from the field and display the highest scores (3 maximum) along with their associated time. 

  3. Inheritance/Subtyping for Dynamic Dispatch
DumbBug, CleverBug, Bullet, Snack and Player will all be subtypes of GameObj. 
They share some common functionalities: they can report their locations, sizes, hp, and velocity, move in four directions and draw themselves on the screen. These methods are defined in the abstract class GameObj. Some of them are the same for all game objects, while draw() is left unimplemented in GameObj and each subtype of GameObj must have its own implementation of draw().

  4. Testable Component
Some tests can be written to make sure that the methods in each game object work correctly.
There's a tick() method in GameCourt that would update the game states. I could write functions that call the method and check whether the game states are updated correctly. 


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

GameObj:
Functions as a blueprint for all the game objects(bullets, bugs and player), with some of the common methods defined. 
Includes data about the position, the size, the HP, the velocity and the range of movement, each of which can be accessed through a public getter method and set to a new value by a public setter method. 
The class also contains four methods that allows the object to move in four directions. In order to prevent the object from moving out of range, a private clip() method is provided, such that if a movement causes an object to move out of its range, it would only stay at the bound. 
A boolean intersects(GameObj that) is included, whose function is to detect whether two game objects intersect with each other. This function would be helpful in implementing the game logic in GameCourt.
Finally, there's an abstract method draw() that needs to be overridden by its subclasses, whose function is to describe how the game object is drawn on the screen.

Bullet (extends GameObj):
A subtype of GameObj that models the bullets the player shoots. 
A bullet is initialized with a certain direction which will not change later. Once fired, the bullet will continue moving in that direction until it hits a bug or hits a wall. 

Player (extends GameObj):
A subtype of GameObj that models the player in the game.
HP is initialized to 1, in the sense that once the player runs into a bug, he will be MERTed.
The move() method describes how the player moves. His movements are controlled by changing the velocity using keyboard control, which would be described in GameCourt.

DumbBug (extends GameObj):
A subtype of GameObj that models the dumbBug in the game. The distinctive feature of this bug is that it does not know the locations of snacks and in each step it moves towards the four directions with an equal chance.

CleverBug (extends GameObj):
A subtype of GameObj that models the cleverBug in the game. The distinctive feature of this bug is that it knows whether the snacks are and will always take the shortest route to the snack closest to it. Its move method takes a parameter, Collection<Snack>, to compute which direction the bug is going in the next step. 

Snack (extends GameObj):
A subtype of GameObj that models the snack in the game. I provided three different images for snacks (mooncake, cake and cookie) in order to make it more interesting. The picture representation of the snack is determined when it's initialized, with equal chance of being any one of the three images. The HP is set to 1, in the sense that once the snack is hit by a bug, it gets eaten and disappears.

Direction (enum):
Can take any of the four values: UP, DOWN, LEFT and RIGHT. Represent the directions in the game.

GameCourt:
Describes the game logic.
Game states, such as the score and the time elapsed, is stored in this class.
Game constants, such as the size of the court and the interval for the timer, is also stored in this class.
The ways that the game objects interact with each other is described in the public method tick(). A call to tick() will cause every object in the game to update its state. If a game object runs into another object, the consequences are also described.
In the constructor for GameCourt, a timer is added, and when an interval has passed, the tick() method will be called. It has the effect of updating the game state after every time interval. The user's interaction with the game is also described by a KeyAdapter.
The reset() method has the effect of restarting the game and setting every game state back to original. 

Game:
Describes the layout of the game page. Includes some buttons for users to interact with the game.

RecordIterator (implements Iterator<String>): 
The iterator that can iterate through the score data from previous games.

Pair (implements Comparable<Pair>): 
Like a tuple, in which the first element stores the score in a game and the second stores the time associated with the score. To compare two pairs, we can compare the score of the pairs.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

Yes. I wanted to use a grid to describe the locations of game objects, but I found that, since every object moves at different speeds, it's hard to determine whether two objects have intersected each other during the interim of two updates. (Eg: in one update, bug1 could go from (3, 5) to (3, 7), and bug 2 could go from (2, 4) to (2, 9). Their initial and final locations are both different from each other, but they have intersected each other along the way). So I abandoned the idea of confining the objects in grids and decided to let them move freely, just like what the source code did. 

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I think my design is good since it clearly separates what a game object can do and how the game objects interact with each other. Also, the underlying game logic is separated from the GUI, so tests can be written to test the game logic. Also when I played my game during an office hour, the TA says it's super cool.
Unfortunately, in order to make sure that the internal game state is updated correctly, I made many fields package accessible. This would add some vulnerability to the game since these important game states can be altered outside of the GameCourt class. But as long as the game package doesn't get leaked, thing will work fine. 
I would create another abstract class, Movable, that extends the GameObj class. I would then make every game object that implements the move() method be subtypes of this Movable class.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

' Images are screenshots of Apple emojis.
' https://www.mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
' https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
' https://stackoverflow.com/questions/13510641/add-controls-vertically-instead-of-horizontally-using-flow-layout