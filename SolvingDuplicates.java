package axiom;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class SolvingDuplicates {

	public static void main(String[] args) {
		//get file path from the user
		Scanner sc1 = new Scanner(System.in);
		System.out.println("Enter the location of the .csv file to be condensed.");
		String filePath = sc1.next();
		
		//upload the .csv file, remove duplicates, clear all blank lines, and get the conclusion data
		String[][] data = convertToArray(filePath, sc1);
		
		//make a copy of data so data array is preseved in memory
		String[][] dataCopy = new String[data.length][];
		for (int i = 0; i < data.length; i++) {
			dataCopy[i] = data[i].clone();
		}
		
		//remove duplicates and get conclusion data
		String[][] dataDupRemoved = removeDuplicates(dataCopy);
		String[][] finalData = clearBlankLines(dataDupRemoved);
		int[] conclData = getConclusionData(data, finalData);
		
		//print conclusion data
		System.out.println("\n----- CONCLUSION DATA -----");
		System.out.println("Total probes in original data: " + conclData[0]);
		System.out.println("Total duplicates in original data: " + conclData[1]);
		System.out.println("Total probes in output data: " + conclData[2]);
		System.out.println("Total duplicates in output data: " + conclData[3]);
		if (conclData[0]-conclData[1]==conclData[2] && conclData[3]==0) System.out.println("Conclusion data looks good.");
		else System.out.println("Something went wrong.");
		System.out.println("---------------------------\n");

		//download the completed data file with desired file name 
		System.out.println("Enter the desired name of the output file. Include .csv at the end. The file will be downloaded in the same location as the uploaded .csv file.");
		String outputName = sc1.next();
		convertToCSV(finalData, Paths.get(filePath).getParent().toString(), outputName);		
		sc1.close();
	}

	public static void convertToCSV(String[][] data, String parentDir, String fileName) {
		/**
		 * This method converts an array into a CSV and downloads it. 
		 * 
		 * data: the array to convert
		 * parentDir: the folder/directory the file will be downloaded into
		 * fileName: the desired file name, with .csv included at the end
		 */
		
		//set up filePath as the parent directory and filename
		String filePath = parentDir + "/" + fileName;
		
		//use FileWriter to add each row from the data array into the new file with data separated by commas
		try {
			FileWriter fw = new FileWriter(filePath);
			for (String[] rowData : data) {
				fw.append(String.join(",", rowData));
				fw.append("\n");
			}
			fw.flush();
			fw.close();
			System.out.println("CSV file created successfully at " + filePath);
		} 
		catch (IOException e) {
			System.out.println("Error while creating CSV file at " + filePath + ":" + e.getMessage());
		}

	}
	
	private static int[] getConclusionData(String [][] originalData, String [][] finalData) {
		/**
		 * Returns an array of format [total probes in original, number of duplicates in original, total probes in final, number of duplicates in final]
		 * 
		 * originalData: the original .csv file submitted by user, as an array
		 * finalData: the array with all duplicates removed
		 */
		int[] conclusionArr = new int[4]; 
		
		//calculate real length of finalData, since there are blank rows
		int finalLen = 0;
		for (int i = 0; i < finalData.length; i++) {
			if (!finalData[i][0].equals("")) finalLen++;
		}
		
		//organize data into conclusionArr and return it
		conclusionArr[0] = originalData.length - 2;
		conclusionArr[1] = countDuplicates(originalData);
		conclusionArr[2] = finalLen - 2;
		conclusionArr[3] = countDuplicates(finalData);
		return conclusionArr;
	}
	
	private static int countDuplicates(String[][] data) {
		/**
		 * Returns the number of duplicate probes in the data array.
		 * 
		 * data: he array that needs to have its number of duplicate probes counted
		 */
		int numDuplicates = 0;
		
		//find the real length of the array, in case there are blank rows at the bottom
		int len = 0;
		for (int i = 0; i < data.length; i++) {
			if (!data[i][0].equals("")) len++;
		}
		
		//iterate through rows and count the number of duplicate probes, then return that value
		for (int i = 2; i < len; i++) {
			if (data[i][3].equals(data[i-1][3])) numDuplicates++;
		}
		return numDuplicates;
	}

	private static String[][] clearBlankLines(String[][] dataClear) {
		/**
		 * Removes all blank lines from the array and returns the array.
		 * 
		 * data: the array to remove blank lines from
		 */
		int rowsRemovedCount = 0;
		for (int i = 0; i < dataClear.length; i++) {
			
			//if there is nothing in the first item of the row, the entire row must be blank
			if (dataClear[i][0].equals("")) { 
				rowsRemovedCount++;
				
				//for every row below the blank one being iterated on currently, shift all data up one row
				for (int r = i; r < dataClear.length - 1; r++) {
					for(int c = 0; c < dataClear[0].length; c++) {
						//
						dataClear[r][c] = dataClear[r+1][c];
					}
				}
				i--;
			}
		}

		//starting at the bottom row, clear rowRemovedCount rows
		for (int i = 1; i <= rowsRemovedCount; i++) {
			for (int j = 0; j < dataClear[0].length; j++) {
				dataClear[dataClear.length - i][j] = "";
			}
		}

		return dataClear;
	}

	private static String[][] removeDuplicates(String[][] data) {
		/**
		 * Returns an array with duplicate rows removed and the Secondary, DETECTED, or "" values collapsed into one row
		 * per probe.
		 * 
		 * data: The array that will have its duplicates removed.
		 */
		boolean testIf = false;

		for (int i = 2; i < data.length-1; i++) {
			if (data[i][3].equals(data[i+1][3])) {
				for (int j = 5; j < data[0].length; j++) {
					testIf = false;
					
					//if nothing is detected, don't change anything
					if (data[i][j].equals("") && data[i+1][j].equals("")) {
						testIf = true;
					}
					
					//if at least one cell is secondary, set it equal to secondary
					else if ((data[i][j].equals("Secondary") && data[i+1][j].equals("Secondary")) || (data[i][j].equals("Secondary") && data[i+1][j].equals("")) || (data[i][j].equals("") && data[i+1][j].equals("Secondary"))) {
						data[i+1][j] = "Secondary";
						testIf = true;
					}
					
					//if at least one cell is DETECTED, set it equal to DETECTED
					else if (data[i][j].equals("DETECTED") || data[i+1][j].equals("DETECTED")) {
						data[i+1][j] = "DETECTED";
						testIf = true;
					}
					
					//if none of the above conditions are satisfied, note an error at this location
					if (!testIf) System.out.println("\n ERROR: LINES NOT CONDENSED at row " + i + ", col " + j + "\n");
				}
				
				//clear the row that is now unused, this row will be removed in clearBlankLines()
				if (testIf) {
					for (int j = 0; j < data[0].length; j++) {
						data[i][j] = "";
					}
				}
			}
		}
		return data;
	}

	public static String[][] convertToArray(String filePath, Scanner sc1) {
		/**
		 * Converts a .csv file that user will input into an array.
		 * 
		 * filePath: path to the .csv to be converted
		 * sc1: scanner object
		 * 
		 */
		
		//take in necessary data: the file path, the number of rows, and the number of columns
		System.out.println("Enter the number of rows in the .csv file.");
		int rowCountTrue = sc1.nextInt(); 
		System.out.println("Enter the number of columns in the .csv file");
		int colCountTrue = sc1.nextInt();
		
		//set up delimiter, file, and empty array to load data into
		File file = new File(filePath);
		String[][] data = null;

		try {
			
			//further set up initializing variables
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			data = new String[rowCountTrue][colCountTrue];
			int rowCount = 0;
			int colCount = colCountTrue;
			
			//run while loop as long as there is data to be read, increment rowCount each time
			while ((line = br.readLine()) != null) {
				rowCount++;
				
				//split up data by the delimiter and add each datum to corresponding cell in the array
				String[] values = line.split(",");
				for (int i = 0; i < colCount; i++) {
					if (i < values.length) data[rowCount - 1][i] = values[i];
					else data[rowCount - 1][i] = "";
				}
			}
			br.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
