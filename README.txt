# Chrome Dino Game - OOP Lab

This is a Java project that includes code divided into three folders: GameObject, Handler, and UserInterface. The main file, GameWindow.java, is located in the UserInterface folder.

## Prerequisites

To run this project, you need to have Java Development Kit (JDK) installed on your system.

## Getting Started

Follow the steps below to compile and run the Java code:

1. Open your terminal or command prompt and navigate to the root directory of the project.

2. Compile the code using the following command:

   javac -d bin src/GameObject/*.java src/Handler/*.java src/UserInterface/*.java

   This command compiles all the Java files in their respective packages and places the compiled .class files in a new folder called bin.

3. Run the program using the following command:

   java -cp bin UserInterface.GameWindow

   This command runs the GameWindow class in the UserInterface package, assuming it contains the main method.

Make sure you execute these commands from the root directory of the project to ensure the correct paths are used. Adjust the commands accordingly if you're using an IDE or build tool.
