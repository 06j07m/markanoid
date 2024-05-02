# Markanoid

[About](#about) ◈ [Installation](#installation) ◈ [How to use](#how-to-use) ◈ [Features](#features) ◈ [How are points calculated?](#how-are-points-calculated)

<img src="display/sample-main.png" alt="screenshot of level 1 arkanoid screen" height=500 />

## About

> [!NOTE]  
> This project was made for a school assignment. It is no longer maintained.

Markanoid (Mock Arkanoid) is a replica of 2 levels of the arcade game Arkanoid. It includes sound effects and power-ups for the authentic experience.

## Installation

### Windows

1. Download source code as a *.zip* file
2. Unzip it into a new folder

## How to use

1. Run *Markanoid.java*
2. Select the level using up/down arrow keys
    - Level 1 corresponds to round 1 on the arkanoid map; level 2 corresponds to round 3
3. Press Enter to confirm selection and go to the level screen
4. Press Space to launch the ball and use the left/right arrow keys to move the platform

## Features

- Two levels

  <img src="display/sample-lvl1.png" alt="screenshot of level 1 setup" width=30% />
  <img src="display/sample-lvl2.png" alt="screenshot of level 2 setup" width=30% />

- 3 powerup types
  - <img src="img/pu01.png" alt="blue L powerup icon" height=15/> : Makes the paddle Longer
  - <img src="img/pu02.png" alt="red S powerup icon" height=15/> : Makes the ball Slow down
  - <img src="img/pu03.png" alt="grey D powerup icon" height=15 /> : Creates 2 more Duplicates of the ball
- Different points for each colour of block (see below)
- Displays high score

## How are points calculated?

| Block | Number of points |
|---|---|
| <img src="img/block01.png" alt="white block" height=15 /> | 50 |
| <img src="img/block02.png" alt="orange block" height=15 /> | 60 |
| <img src="img/block03.png" alt="blue block" height=15 /> | 70 |
| <img src="img/block04.png" alt="green block" height=15 />| 80 |
| <img src="img/block05.png" alt="red block" height=15 /> | 90 |
| <img src="img/block06.png" alt="dark blue block" height=15 /> | 100 |
| <img src="img/block07.png" alt="pink block" height=15 /> | 110 |
| <img src="img/block08.png" alt="yellow block" height=15 /> | 120 |
| <img src="img/block09.png" alt="silver block" height=15 /> | 50x the level number |
| <img src="img/block10.png" alt="gold block" height=15 /> | 0 ("wall") |