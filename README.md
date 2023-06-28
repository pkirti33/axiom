# axiom

## SolvingDuplicates.java
Java pipeline to condense Axiom Microarray data and remove duplicate probes.

The input code must follow the proper format. The first three columns must be occupied, maybe by the Domain, Species, and Family of the probe in the corresponding row. The fourth column must be the Probe or Target Description. All the following columns must be the samples. The first two rows must be occupied by column descriptions. The first row may be the Sample Name (ex: NP-001.1) and the second row may be the tag (ex: a123456-1234567-123456-424-A01). All the data within the table (below the second row and right of the fourth column) must be either "", Secondary, or DETECTED.

The program will take in a .csv from a specified path + the number of rows and columns in the .csv, convert it to an array, remove all duplicate probes and condense the data, and download a new .csv with a specified name into the same directory the original .csv was uploaded from.

### Command Line Instructions
1. Download the .zip file from the GitHub by going to Code --> Download .zip
2. Extract the .zip file and remove the suffix from the title so it is just "axiom"
3. Open a command line window 
4. Use the commands `cd <directory>` (move into a directory) and `cd ..` (step out of a directory) to move into the directory that contains the "axiom" folder
5. `javac axiom/SolvingDuplicates.java` to compile the script.
6. `java axiom/SolvingDuplicates` to run the script

## R Code Files
Analysis of data completed in R. Contains code for alpha diversity, beta diversity, and two versions of a heatmap. This code is NOT final. 
