# Rain Development

## Team Introduction

- Leader
    - `Jaehun Jung`
- Team Member & Role
    - `Hyunjun Kim`
    - `Jinyoung Park`
    - `Seonwoo Kang`
    - `Seongwon Hwang`
    - `Jaeseon Kim`
    - `Changjeong Choi`

## Team Requirements

1. Player Control and Animation
2. Enemy (Monster) **Spawn and Death Logic Implementation**
3. System and Map: **Single Fixed Image Screen** without scrolling (Brotato style)
4. Experience Points and Leveling System
5. Items and Weapons
6. HUD

## **Detailed Requirements**

- Player
    - WASD movement, directional normalize
    - Implement Move and Idle animations
- HP Bar
    - Display player HP (global variable) gauge, follows player below
- EXP Bar
    - Gauge at top of screen, displays player EXP (global variable)
- Map
    - Scroll X, fixed-screen play using Brotato method
    Single image
- Weapons
    - Individual objects per weapon, controls upgrades, etc.
    - Periodic attacks
    - Fixed events per level
- Items
    - Each item is a separate object, controls upgrades, etc.
    - Controls player stats (global variable) based on level
- Enemies
    - Controlled via mob cloning from a single object.
    - Spawns after an effect at a random position on screen
    - Pursues the player and possesses HP
    - Enemies do not have HP bars, making them invisible to the player
    - Drops experience particles upon death
- Effects
    - Controlled via cloning from a single object. Effects called via messages execute at specified locations.
- Experience Particles
    - Controlled via cloning from a single object.
    - Add experience when touching the player
    - Level up when experience is maxed
    - Experience required per level is set via level-specific lists and controlled by index
- Weapon Acquisition UI
    - Adds all weapons and items to the list.
    - Controlled by three lists: Item List / Item Level List / Item Max Level List. Accessed via index.
    - Randomly selects 3 items from the list that are not at max level.
    - If fewer than 3 non-max-level items exist, display considerations will be addressed later
    - Display 3 boxes on screen showing item information
    - Game pause function
    - Information control lists
    - Item icon name list / Item description image name list
    Pre-populate data and load it.
- Item UI composition
    - Item icon image
    - Next level (current level + 1)
    - Item description image
    - Select by clicking UI, then approach corresponding weapon to level up
- Timer
    - Display timer on screen, decreasing over time
- Held items UI
    - Display currently held items and their levels
- Number Display
    - Draws a number at a specific screen location
- Game Manager
    - Controls timer and logic for summoning mobs based on time
    - Configures settings like summoning entity, mob name, health, speed, frame count, frame delay variable, etc.
    - Sends a signal to the enemy to summon the mob
