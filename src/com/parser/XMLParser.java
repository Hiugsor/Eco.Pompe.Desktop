package com.parser;

import java.io.*;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.xml.parsers.*;
import org.jdom2.*;
import org.jdom2.input.DOMBuilder;

import com.teamdev.jxbrowser.chromium.Browser;
import com.API.googlemaps.Marker;
import com.processing.GeoProcessing;

public class XMLParser {

	// [Attributs]
	static String workingDir = System.getProperty("user.dir");
	private static String xmlSource = workingDir + "\\src\\Data\\XML\\PrixCarburants_quotidien_20151210.xml";

	// Limites Géographiques de la France et corse
	public static double latMin = 41.319505;
	public static double latMax = 51.190288;
	public static double longMin = -5.182504;
	public static double longMax = 9.588748;

	/**
	 * Methode d"analyse d'un fichier CSV
	 * @param browser
	 */
	public static void GenerateStationFromCSV(Browser browser, JTextField txtLat, JTextField txtLong, JSlider slider) {
		// >>> Déclarations
		CsvFileRead csvFile;
		List<String[]> lines;
		int counter = 0;

		try {
			// >>> Lecture du Fichier csv
			csvFile = new CsvFileRead(new File(workingDir + "\\src\\Data\\XML\\PrixCarburants_quotidien_20151214.csv"));
			lines = csvFile.getData();

			for (String[] column : lines) {				
				// >>> Contrôles des données 1/3 - Vérification Si CodePostal Vide
				if (column[3].length() == 0) {
					column[3] = "0";
					/*newLogFile.write("CP Vide," + column[0] + "," + column[1] + "," + column[2] + "," + column[4] + ","	+ column[3] + "," + column[5] + "\n");*/
				}
				int cp = Integer.parseInt(column[3]);
				// >>> Contrôles des données 2/3 - Vérification de valeurs vides sur les latitudes & les longitudes
				if (column[1].length() == 0 && column[2].length() != 0) {
					column[1] = "0";
				} else if (column[2].length() == 0 && column[1].length() != 0) {
					column[2] = "0";
				} else if (column[1].length() == 0 && column[2].length() == 0) {
					column[1] = column[2] = "0";					
				}

				// >>> Contrôles des données 3/3 - Vérification d'inversion des lat-long dans le fichier csv
				double lat = Double.parseDouble(column[1]) / 100000;
				double lng = Double.parseDouble(column[2]) / 100000;				
				if ((lat < lng)) {
					if (longMin <= lat && lat <= longMax && latMin <= lng && lng <= latMax) {
						lat = Double.parseDouble(column[2]) / 100000;
						lng = Double.parseDouble(column[1]) / 100000;
					}
				}

				// >>> Création des Markers sous conditions d'être dans les limites géographiques de la france et corse
				if (latMin <= lat && lat <= latMax && longMin <= lng && lng <= longMax) {
					double distance = GeoProcessing.distance(Double.parseDouble(txtLat.getText()), Double.parseDouble(txtLong.getText()), lat, lng);
					if (distance <= slider.getValue()) {
						new Marker(browser, lat, lng, column[0], column[4], column[3], column[5]); //création du marker
						counter++;
					}
				}
			}		
			System.out.println(">>> Job Done ! with " + counter + " stations imported. []");
		}
		catch (Exception e) {
			System.out.println(">>> Execption " + /*e.getMessage()) +*/ e.getStackTrace());
		}
	}

	
	/**
	 * Methode d"analyse d'un fichier XML [à retravailler en focntion du code de
	 * traitement de la methode GenerateStationFromCSV()]
	 * 
	 * @param browser
	 */
	public static void GenerateStationFromXML(Browser browser) {
		try {
			int counter = 0; // Compteur des Markers
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dombuilder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3cDocument = dombuilder.parse(xmlSource);

			DOMBuilder builder = new DOMBuilder();
			Document doc = builder.build(w3cDocument);
			Element root = doc.getRootElement();
			List row = root.getChildren("pdv");

			for (int i = 0; i < row.size(); i++) {
				Element pdv = (Element) row.get(i);
				String idPdv = pdv.getAttribute("id").getValue();
				int cpPdv = Integer.parseInt(pdv.getAttribute("cp").getValue());
				if (30000 <= cpPdv && cpPdv < 35000) {
					double latitudePdv = Double.parseDouble(pdv.getAttribute("latitude").getValue()) / 100000;
					double longitudePdv = Double.parseDouble(pdv.getAttribute("longitude").getValue()) / 100000;
					browser.executeJavaScript(
							"var myLatlng = new google.maps.LatLng(" + latitudePdv + "," + longitudePdv + ");\n"
									+ "var marker = new google.maps.Marker({\n" + "    position: myLatlng,\n"
									+ "    map: map,\n" + "    title: '" + idPdv + "!'\n" + "});");
					System.out.println("NUM " + counter + " : " + cpPdv);
					counter++;
				}
			}
			System.out.println(counter);
		} catch (Exception e) {
			System.out.println("Execption " + e.getMessage());
	
			
		}
		System.out.println("Done!");

	}// [End Method]
}// [End Class]
