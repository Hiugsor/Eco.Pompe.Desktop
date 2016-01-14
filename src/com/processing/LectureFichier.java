package com.processing;

import java.io.*;
import java.util.*;

import com.fileparser.CsvFileHelper;
import com.fileparser.CsvFileRead;

public class LectureFichier {

	/**
	 * Read a txt file
	 * @param path Source file
	 * @return String List
	 */
	public static List<String> readTXT(String path){
		ArrayList<String> rows = new ArrayList<String>();
		try{
			InputStream ips=new FileInputStream(path); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String row;
			
			
			while ((row=br.readLine())!=null){
				//System.out.println(row);
				rows.add(row);

			}	
			
			br.close();
		}catch (IOException ioe){
			ioe.getStackTrace();
		}
		return rows;
	}
	
	public static  List<String[]> readCSV(String path){
		
		List<String[]> lines = new ArrayList<String[]>();
		File fileCsv = CsvFileHelper.getResource(path);
		CsvFileRead StationCsv;
		
		StationCsv = new CsvFileRead(fileCsv);
		lines = StationCsv.getData();
		//vérification des données récupérédu fichier csv
//		for (String[] colonne : lines)
//		{
//			for (int i = 0; i < colonne.length; i++) {
//				System.out.println(colonne[i] + " ");
//			}
//			System.out.println("*******************");
//			System.out.println(colonne[4]+", "+colonne[3]+", "+colonne[5]);
//			
//			//System.out.println(colonne[4]);
//
//		}
				
		
		return lines;
	}
}
