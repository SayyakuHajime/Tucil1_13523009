
# IQ Puzzler Pro Solver #

![shinji](image/shinji.png)

## Description
This program finds exactly one solution to the IQ Puzzler Pro game using a brute force algorithm, 
or displays that no solution is found if no possible solution exists for the puzzle.

## Requirements
- Java Development Kit (JDK) 8 or higher
---
## Compilation
compile the program first
```bash
  javac -d bin src/model/Block.java src/model/Board.java src/parser/FileParser.java src/solver/BruteForceSolver.java src/Main.java
```
---
## Run The Program
```bash
  java -cp bin Main 
```
or with
```
java -cp bin Main dir_to_input.txt
```
> NOTE: change `dir_to_input.txt` with the path to the input file
---
## Using GUI
> NOTE For GUI: please uncomment this line in `Main.java`
```java
// Application.launch(GUI.class, args);
```
then compile and run the program the same way as before
```bash
  javac -d bin src/model/Block.java src/model/Board.java src/parser/FileParser.java src/solver/BruteForceSolver.java src/gui/GUI.java src/Main.java
  java -cp bin Main
```

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

