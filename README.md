
# IQ Puzzler Pro Solver #

![shinji](image/shinji.png)

## Description
This program finds exactly one solution to the IQ Puzzler Pro game using a brute force algorithm, 
or displays that no solution is found if no possible solution exists for the puzzle.

---

## Requirements
1. Java Development Kit (JDK) version 8 or higher
2. JavaFX SDK (Download from https://openjfx.io/)

--- 

## Installation
1. Clone this repository:
```bash
  git clone https://github.com/SayyakuHajime/Tucil1_13523009.git
```

---

## How to Compile
compile the program first
### For CLI Mode Only:
```bash
  javac -d bin src/model/*.java src/parser/*.java src/solver/*.java src/Main.java
```
### For GUI + CLi Mode:
```bash
  javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -d bin src/model/*.java src/parser/*.java src/solver/*.java src/gui/*.java src/Main.java
```
> NOTE: change `/path/to/javafx-sdk/lib` with the path to the JavaFX SDK library

---

## How to Run
### For CLI Mode Only:
```bash
  javac -cp bin Main
```
or with
```
java -cp bin Main dir_to_input.txt
```
> NOTE: change `dir_to_input.txt` with the path to the input file
### For GUI + CLI Mode:
```bash
  java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp bin Main
```
> NOTE: change `/path/to/javafx-sdk/lib` with the path to the JavaFX SDK library
> 
---

## Structure
```
.
├── src/          
│   ├── gui/     
│   │   ├── GUI.java
│   ├── model/   
│   │   ├── Block.java  
│   │   └── Board.java  
│   ├── parser/  
│   │   └── FileParser.java  
│   ├── solver/  
│   │   └── BruteForceSolver.java  
│   └── Main.java 
├── bin/          
├── test/         
├── doc/    
├── .gitignore      
└── README.md     
```

---

## Author
M Hazim R Prajoda (13523009)

