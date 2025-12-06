# COMP-2522-202530-GameProject

## Your name:
Mason Young

## Your student number:
A01180997

## Your GitHub name:
MasonYoung24

## Game name:
Bulwark

## Game Description:
Bulwark is a top down 2D action-survival shooter game with a pixel style. It was built using Java
and the LibGDX framework. In the game, the players controls the player character, a powerful wizard known as a Bulwark
that is tasked with defending a region of the world from invading enemies. The player character can move, shoot
projectiles to damage enemies, and cast some helpful abilities. Enemies will either chase you, or shoot their own
projectiles to try and slay you. You must survive for as along as possible. At the moment, there is no winning, just
seeing how long you can survive.

# Game instructions and controls
## Starting the game:
The main menu will open upon running the game. From here, you can either choose to start a fresh game by clicking the
START button, or you can click LOAD to start from as existing save using data locally saved to a json.

## Controlling the player character:
Use the W, A, S, D keys to move the Bulwark in the corresponding cardinal directions. Left click with the mouse on the
game screen to fire a projectile in the direction of where you clicked. Hitting an enemy with a projectile will damage
it. Damaging an enemy enough times will slay it and cause it to disappear. The Bulwark also has a limited set of
abilities that it can cast at the moment. Pressing the space bar will cast the Blink ability causing the player to
teleport a short distance in the direction of movement. Right-clicking the mouse on the screen will cast the Frost Wave
ability which will send out a wave of frost around the Bulwark which will damage enemies and freeze them in place for
a short period on contact. Use this information to survive as long as possible.

## Pausing and saving the game:
During the game, you can pause the game at any time by pressing the ESC (escape) key. This will freeze everything in
place. The pause overlay gives you the option to save the game manually by clicking the SAVE button,
or just take a break. To return to the game, either press the ESC key again, or click the CONTINUE button. Please note
that the game will automatically save every 15 seconds.

## Restarting the game.
I did not get to implementing a functional restart game button on the game over screen, so replaying the game
requires rerunning the program.

# Game Features

- **Action-based combat system**
    - Multiple enemy types with distinct behaviors
    - Projectile-based or physical based attacks and collision handling
    - Status effects such as frozen to stop enemies


- **Ability system**
    - Castable abilities with damage and cooldowns
    - Support for collidable and non-collidable abilities
    - Ability factory for clean creation and expansion


- **Enemy system**
    - Enemy manager for spawning and updating enemies
    - Factory-based enemy creation
    - Support for different enemy types and configurations


- **Projectile system**
    - Centralized projectile manager using the Singleton pattern
    - Sprite-based projectile rendering and movement
    - Collision detection with players and enemies


- **Status effects**
    - Only got to implementing a single status (frozen) which freezes enemies in place
    - Frozen is applied to enemies hit by the Frost Wave ability


- **Collision management**
    - Central collision manager to with collision logic
    - Handles interactions between abilities, projectiles, enemies, and the player


- **User Interface**
    - Main Menu Screen (start or load game)
    - Gameplay HUD (timer, health, enemy count)
    - Pause Screen (continue or save game)
    - Game Over Screen displaying final stats


- **Save / Load system**
    - Serializable save data objects
    - Player and enemy state persistence


- **Asset management**
    - Handle loading assets using LibGDX's AssetManager
    - Organized texture, sprite sheet, UI, and tileset paths

## Technologies Used

- **Java**
- **LibGDX**
- **Scene2D UI**
    - To create UI for the main menu and game over screens
- **Tiled Maps (TMX)**
    - I used the Tiled software to create the main game background
- **Object-Oriented Design Patterns**
    - Factory
        - Static ability and enemy factor classes
    - Singleton
        - For the projectile manager class.
        - Not positive if it was the best design decision, but I game it a go.
    - Inheritance
        - e.g. Enemy, sub Enemy classes, PlayerCharacter all extend Actor
        - Parent Ability class for the abilities
        - Parent StatusEffect class for status effects.

# Future Improvements and Comments

The following are all things that I would have liked to implement, change, or fix.

## Additional enemy types and boss encounters

Right now I only managed to three different enemy types in. I would have liked to get more in.
More enemies that get introduced as the game progresses would definitely be a target for the future.

## More abilities

I only managed to make two abilities. I ended up struggling to implement abilities since my
original idea was to just have a super Ability class that all abilities extended from their own subclasses.
I had a hard time getting the player character to actually cast an ability because each ability required
different information, and I was just trying to cast the Ability super class, so I ended up calling a factory method
to make an instance of the ability in a factory class (AbilityFactory). Then I have an AbilityManager to update each
ability instance (render it and trigger things like collision effect) but then I ran into problems as the array of the
AbilityManager was of type Ability, so each ability needed the same methods, even if they did not really need it (e.g. 
Blink needing an onCollision method).

## More status effects

More status effects would have been fun, but they would likely just be tied in with future abilities.

## Difficulty scaling and progression systems

I did not get to implement a progression system. This would have included player levelling up and getting stats
that would help as the game difficulty scale. Speaking of difficult scaling, I would have liked to implement a better
system given this. Right now every additional minute survived an additional enemy spawns. Without player progression,
this really gets our of hand pretty quick as you just can't keep up with the spawns.

- Improved save system versioning
  I have an automatic and manual save system implemented. A distant goal would have been to implement be able to have
  multiple different save files, and you can choose which to load from the main menu.

- Audio (music and sound effects)
  Would have been a nice to have if I had the time, but too much else to focus on.

- Improved animations and particle effects
  I did not implement any animations till late in the project. So, the only animation I have is for the fireball
  projectile because I wanted to at least try implementing a LibGDX animation. If I could go back I probably would have
  tried to find sprite sheets with animations for the player and enemies, but I still like the static 2D sprite.

- Game design and code improvements
  If I have more time I would definitely like to put more time into making game design improvements. For instance,
  I don't think trying to handle all different enemies and abilities in an array of the top level super class was the
  best
  choice, even if it seemed to make the most sense at the time. I kept running into the pitfall of wanting to use
  instance of to access enemy or ability specific methods while integrating through the array (during update), and then
  had to add abstract methods that didn't get use by some of the subclasses to satisfy the method call on the super
  class

- Game over screen
  I would have like to implement a restart game button, and figure out a better way to do text that did not look so
  fuzzy.

In terms of code improvements, I did not have enough time to fix all errors like replacing all magic numbers, making
sure everything has Javadocs, and final parameters and such. I would have like to put more time into this, but I got
caught up making the core game, and need to spend the time studying for finals now.
