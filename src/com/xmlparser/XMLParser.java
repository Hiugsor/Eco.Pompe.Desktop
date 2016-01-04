package com.xmlparser;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import com.teamdev.jxbrowser.chromium.Browser;

public class XMLParser {
	
	// -------------------------------------------------------  [Attributs]
	static String workingDir = System.getProperty("user.dir");
	private static String xmlSource = workingDir +"\\src\\Data\\PrixCarburants_quotidien_20151210.xml";
	public static double latMin = 41.319505;
	public static double latMax = 51.190288;
	public static double longMin = -5.182504;
	public static double longMax = 9.588748;

	
	/**
	 * Methode d"analyse d'un fichier CSV
	 * @param browser
	 */
	public static void GenerateStationFromCSV(Browser browser) {
		// ---------------------------------------------------  [Déclarations]
		CsvFileRead csvFile;
		List<String[]> lines;
		
		try 
		{
			int counter = 0, countuerInvLatLG = 0;
			// -----------------------------------------------  [Lecture du Fichier csv]
			csvFile = new CsvFileRead(new File(workingDir +"\\src\\Data\\PrixCarburants_quotidien_20151214.csv"));
			lines = csvFile.getData();

			// -----------------------------------------------  [Logging]			
			String errorFile = workingDir +"\\src\\Data\\Positions_Errors.csv";
	        File logFile = new File(errorFile);
	        if (logFile.exists()) logFile.delete();
			FileWriter newLogFile = new FileWriter(errorFile);			
			newLogFile.write("Errors,ID,Latitude,Longitude,Adresse,CP,Ville\n");
			
			for (String[] column : lines) 
			{	
				// -------------------------------------------  [Contrôles des données - Vérif Si CodePostal Vide]			
				if (column[3].length() == 0){
					column[3] = "0";
					newLogFile.write("CP Vide," + column[0] + "," + column[1] + "," + column[2] + "," + column[4] + "," + column[3] + "," + column[5] + "\n");
				}//[End If 1]				
				int cp = Integer.parseInt(column[3]);				
					
				
				
				// -------------------------------------------  [Choix des départements dans la condition]
				if (1000 <= cp && cp < 96000){					
					// ---------------------------------------  [Contrôles des données - Vérification de valeurs vides sur les latitudes & les longitudes]				
					if (column[1].length() == 0 && column[2].length() != 0){
						newLogFile.write("Lat Vide," + column[0] + "," + column[1] + "," + column[2] + "," + column[4] + "," + column[3] + "," + column[5] + "\n");
						column[1] = "0";
					}						
					else if (column[2].length() == 0 && column[1].length() != 0){						
						newLogFile.write("Long Vide," + column[0] + "," + column[1] + "," + column[2] + "," + column[4] + "," + column[3] + "," + column[5] + "\n");
						column[2] = "0";				
					}
					else if (column[1].length() == 0 && column[2].length() == 0) {
						newLogFile.write("Lat Long Vides," + column[0] + "," + column[1] + "," + column[2] + "," + column[4] + "," + column[3] + "," + column[5] + "\n");
						column[1] = "0";
						column[2] = "0";
					}//[End If 2]
					
					
					
					// ---------------------------------------  [Contrôles des données - Vérification d'inversion des lat-long]
					double lat = Double.parseDouble(column[1]) / 100000;
					double lg = Double.parseDouble(column[2]) / 100000;					
					if ((lat < lg)) {
						if (longMin <= lat && lat <= longMax && latMin <= lg && lg <= latMax) {
							lat = Double.parseDouble(column[2]) / 100000;
							lg = Double.parseDouble(column[1]) / 100000;
							newLogFile.write("Inversion Lat Long," + column[0] + "," + column[1] + "," + column[2] + "," + column[4] + "," + column[3] + "," + column[5] + "\n");
						}
					}//[End If 3]
					
					
					
					// ---------------------------------------  [Création des Markers]				
					if (latMin <= lat && lat <= latMax && longMin <= lg && lg <= longMax){
						browser.executeJavaScript("var myLatlng = new google.maps.LatLng(" + lat + "," + lg + ");\n"
								+ "var marker = new google.maps.Marker({\n" + "    position: myLatlng,\n"
								+ "    map: map,\n" + "    title: '" + column[0] + "!'\n" + "});");
						counter ++;
					}
					else // ----------------------------------  [si Marker hors des limites -> log]
					{
						newLogFile.write("Marker Hors Zone," + column[0] + "," + column[1] + "," + column[2] + "," + column[4] + "," + column[3] + "," + column[5] + "\n");
					}//[End If 4]							
				}				
			}//[End For]
			
			// -----------------------------------------------  [Fermeture du fichier Logging d'erreurs]
			newLogFile.close();
			System.out.println(">>> Job Done !");
			
		}//[End Try]	
		catch (Exception e) 
		{
			System.out.println(">>> Execption " + e.getMessage());
			System.out.println(">>> Error !");
		}//[End Catch]	
	}//[End Method]
	
	
	/**
	 * Methode d"analyse d'un fichier XML [à retravailler en focntion du code de traitement de la methode GenerateStationFromCSV()]
	 * @param browser
	 */
	public static void GenerateStationFromXML(Browser browser) {
		try {
			int counter = 0; //Compteur des Markers
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
					// browser.executeJavaScript("");
					
				}//[End If]
			}//[End For]
			System.out.println(counter);
		} catch (Exception e) {
			System.out.println("Execption " + e.getMessage());
		}//[End Catch]
		System.out.println("Done!");
	
	}//[End Method]
}//[End Class]
