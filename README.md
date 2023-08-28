# CSC2002S
// Omolemo Modise
// MDSOMO001
// PCP2

Club Simulation Project
This project is a Java-based simulation of a club where patrons and a barman interact in a predefined environment. The simulation is implemented using multithreading and graphical user interface (GUI) components.

Overview
The club simulation consists of the following components:

ClubGrid: Represents the club's layout as a grid of blocks. Manages movement and occupancy of blocks by patrons and the barman.
GridBlock: Represents a block within the club's grid. Keeps track of occupancy by patrons and the barman.
PeopleCounter: Manages the count of people inside, outside, and leaving the club.
PeopleLocation: Represents the location and attributes of a patron.
Clubgoer: Thread class representing a patron's behavior in the club.
Barman: Thread class representing the barman's behavior in the club.
ClubView: GUI panel displaying the club's layout and current state.
CounterDisplay: Threaded display of people counters.
ClubSimulation: Main class to start the simulation.
Features
Patrons enter the club, dance, and exit.
Patrons wait outside the club if the maximum capacity is reached.
Patrons avoid each other and obstacles while moving within the club.
Patrons are assigned unique colors for identification.
The barman serves drinks to patrons at the bar.
The barman moves behind the bar and serves drinks to patrons.
Patrons and the barman use multithreading for concurrent behavior.
GUI panel provides a visual representation of the club's layout and state.
Counters display the number of people inside, outside, and leaving the club.
