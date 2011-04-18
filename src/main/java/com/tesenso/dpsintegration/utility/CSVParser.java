package com.tesenso.dpsintegration.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tesense.dpsintegration.service.Location;

public class CSVParser {

	public static List parseLocation(File f) throws FileNotFoundException {

		ArrayList<String> column = new ArrayList<String>();
		ArrayList row = new ArrayList();
		// Get the headers of the table.

		Scanner lineScan = new Scanner(f);
		String line = lineScan.nextLine();
		line = line.replaceAll("\"", "");
		String[] cols = line.split(",");

		while (lineScan.hasNextLine()) {
			column = new ArrayList<String>();
			for (String col : cols) {
				column.add(col);
			}
			row.add(column);

			if (lineScan.hasNextLine()) {
				line = lineScan.nextLine();
				line = line.replace("\"", "");
				cols = line.split(",");
			}

		}
		column = new ArrayList<String>();
		for (String col : cols) {
			column.add(col);
		}
		row.add(column);

		return row;
	}

	public static List<Location> getLocation(List rows) {
		ArrayList<String> cols = (ArrayList<String>)rows.get(0);
		
		boolean isColSeqFound = false;
		boolean isColAddFound = false;
		int colSequence = -1;
		int colAdd1 = -1;
		
		for( String col : cols) {
			if(!isColSeqFound && col.toUpperCase().equals("sequence".toUpperCase())){
				colSequence = cols.indexOf(col);
				isColSeqFound = true;
			}
			
			if (!isColAddFound && col.toUpperCase().equals("address_1".toUpperCase())){
				colAdd1 = cols.indexOf(col);
				isColAddFound = true;
			}			
			
			if (isColAddFound && isColSeqFound){
				break;
			}
		}	
		System.out.println(colSequence + " - " + colAdd1  );
		
		return null;
	}

}
