package com.processing;

import java.util.ArrayList;
import java.util.List;

public class traitementString {

	// transformer une chaine en liste de nom
	public static List<String> recupererDonnées(String colonne, String charSplit) {
		ArrayList<String> mots = new ArrayList<String>();

		String[] motsCol = colonne.split(charSplit);

		for (String string : motsCol) {
			
				//System.out.println(string);
				mots.add(string);
			
		}
		//System.out.println("mot "+mots.size());
		
		return mots;
	}

	public static List<String[]> recupererDonnéesTab(String colonne, String charSplit) {
		ArrayList<String[]> mots = new ArrayList<String[]>();

		String[] motsCol = colonne.split(charSplit);

		// for (String string : motsCol) {
		// mots.add(string);
		// }

		return mots;
	}

}
