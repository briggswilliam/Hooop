# Hooop!

A digital recreation of the board game **"Hooop!"** developed using Java and Java Swing. This project leverages object-oriented programming (OOP) principles and Java Swing to deliver the core mechanics, board interactions, and graphical user interface.

The game takes place on a pond filled with lily pads connected by bridges. Players control a team of frogs aiming to reach opponent home lily pads, utilizing spatial strategy and one-time special action cards to outmaneuver rivals.

---

## Game Rules

- **Team & Cards:** Each player controls **3 frogs** and starts with **4 one-time-use action cards**:
  - *Parachute*
  - *Extra Jump*
  - *Extra Bridge*
  - *Bridge Removal*
- **Movement:** Jump your frogs across adjacent lily pads using bridges.
- **Bridge Removal:** Crossing a bridge removes it from the board behind you.
- **Alternate Actions:** Instead of moving a frog, a player may place a new bridge or play an action card.
- **Bumping Mechanics:** Landing on an occupied lily pad pushes the resident frog to a connected pad.
- **Winning Conditions:**
  - **2-Player Mode:** First player to get all 3 frogs onto the opponent's home lily pad wins.
  - **4-Player Mode:** First player to land one frog on each opponent's home lily pad wins.

---

## ✨ Features

- **2 & 4 Player Modes:** Scalable setup supporting head-to-head or four-player pond battles.
- **Save & Load Functionality:** Serialized game states allow pausing and resuming ongoing games.
- **Accessibility:** Color vision deficiency (CVD) support via distinct numerical identifiers on each frog.
- **Turn & Action Tracking:** Visual feedback for remaining special action cards and bridge inventory.

---

## 🐸 Installation & Setup

### Prerequisites
- [Java Development Kit (JDK) 8+](https://www.oracle.com/java/technologies/downloads/) installed and configured in your `PATH`.

### Running from JAR
1. Clone the repository:
   ```bash
   Clone the repository: git clone https://github.com/briggswilliam/Hooop

2. In the installation directory, open `Hooop.jar`. 

3. Play the game
---

## Credit
This project is a recreation of the board game Hooop! and was completed in a group as part of COMP 2005 "Software Engineering" at Memorial University of Newfoundland. This project is not affiliated with or endorsed by the designer, artist, or any publisher.

Other contributions were made by my classmates Hamzeh Alhyari, kiara Jenkins and Zach Power.

**Designer**: Adam Kałuża

**Illustrator**: Piotr Socha
