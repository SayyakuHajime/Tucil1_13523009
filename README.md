
# IQ Puzzler Pro Solver #

![shinji](image/shinji.png)

## Description
Program ini menemukan cukup satu solusi dari permainan IQ Puzzler Pro dengan
menggunakan algoritma Brute Force, atau menampilkan bahwa solusi tidak ditemukan jika
tidak ada solusi yang mungkin dari puzzle.

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
atau dengan 
```
java -cp bin Main dir_to_input.txt
```
> NOTE: ubah `dir_to_input.txt` dengan direktori file input yang ingin diuji
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

