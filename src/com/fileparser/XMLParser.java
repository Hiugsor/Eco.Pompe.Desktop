package com.fileparser;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.xml.parsers.*;
import org.jdom2.*;
import org.jdom2.input.DOMBuilder;

import com.teamdev.jxbrowser.chromium.Browser;

import com.api.googlemaps.Marker;
import com.bo.Carburant;
import com.bo.Station;
import com.processing.GeoProcessing_xtof;

public class XMLParser
{

	// [Attributs]
	static String workingDir = System.getProperty("user.dir");
	private static String xmlSource = workingDir + "\\src\\Data\\XML\\PrixCarburants_quotidien_20151210.xml";

	// Limites G�ographiques de la France et corse
	public static double latMin = 41.319505;
	public static double latMax = 51.190288;
	public static double longMin = -5.182504;
	public static double longMax = 9.588748;
	
	static String cochGreen= "cochonVert";
	static String cochYellow = "cochonJaune";
	static String cochRed = "cochonRouge";

	/**
	 * Methode d"analyse d'un fichier CSV
	 * 
	 * @param browser
	 */
	
	/*
	public static void GenerateStationFromCSV(Browser browser, JTextField txtLat, JTextField txtLong, JSlider slider,
			List<String[]> ListeStations)
	{
		// >>> Déclarations
		CsvFileRead csvFile;
		List<String[]> lines;
		int counter = 0;
		ListeStations.clear();

		try
		{
			// >>> Lecture du Fichier csv
			csvFile = new CsvFileRead(new File(workingDir + "\\src\\Data\\XML\\PrixCarburants_quotidien_20151214.csv"));
			lines = csvFile.getData();

			for (String[] column : lines)
			{
				// >>> Contr�les des donn�es 1/4 - V�rification Si
				// CodePostal
				// Vide
				if (column[3].length() == 0)
				{
					column[3] = "0";
				}
				// int cp = Integer.parseInt(column[3]);

				// >>> Contr�les des donn�es 2/4 - V�rification de valeurs
				// vides
				// sur les latitudes & les longitudes
				if (column[1].length() == 0 && column[2].length() != 0)
				{
					column[1] = "0";
				}
				else if (column[2].length() == 0 && column[1].length() != 0)
				{
					column[2] = "0";
				}
				else if (column[1].length() == 0 && column[2].length() == 0)
				{
					column[1] = column[2] = "0";
				}

				// >>> Contr�les des donn�es 3/4 - Suppression du premier
				// espace
				// dans l'adresse.
				if (column[4].substring(0, 1).contains(" "))
				{
					column[4] = column[4].substring(1, (column[4].length()));
				}

				// >>> Contr�les des donn�es 4/4 - V�rification
				// d'inversion des
				// lat-long dans le fichier csv
				double lat = Double.parseDouble(column[1]) / 100000;
				double lng = Double.parseDouble(column[2]) / 100000;
				if ((lat < lng))
				{
					if (longMin <= lat && lat <= longMax && latMin <= lng && lng <= latMax)
					{
						lat = Double.parseDouble(column[2]) / 100000;
						lng = Double.parseDouble(column[1]) / 100000;
					}
				}

				// >>> Cr�ation des Markers sous conditions d'�tre dans les
				// limites g�ographiques de la france et corse
				if (latMin <= lat && lat <= latMax && longMin <= lng && lng <= longMax)
				{
					double distance = GeoProcessing_xtof.getDistance(Double.parseDouble(txtLat.getText()),
							Double.parseDouble(txtLong.getText()), lat, lng);
					if (distance <= slider.getValue())
					{
						// cr�ation du marker
						new Marker(browser, lat, lng, column[0], column[4], column[3], column[5]);
						// Creation d'un tableau des infos station
						String[] station = new String[] { column[0], column[4], column[3], column[5],
								Double.toString(lat), Double.toString(lng) };
						// Ajout du tableau dans la liste des Stations
						ListeStations.add(station);
						counter++;
					}
				}
			}
			System.out.println(">>> Nb de Stations inserées dans la ListeStations : " + ListeStations.size());
			System.out.println(">>> Job Done ! with " + counter + " stations imported. [Good]");
		}
		catch (Exception e)
		{
			System.out.println(">>> Execption " + /* e.getMessage()) +  e.getStackTrace());
		}
	}*/

	/**
	 * Methode d"analyse d'un fichier XML [� retravailler en focntion du code de
	 * traitement de la methode GenerateStationFromCSV()]
	 * 
	 * @param browser
	 */
	public static void GenerateStationFromXML(Browser browser)
	{
		try
		{
			int counter = 0; // Compteur des Markers

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dombuilder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3cDocument = dombuilder.parse(xmlSource);

			DOMBuilder builder = new DOMBuilder();
			Document doc = builder.build(w3cDocument);
			Element root = doc.getRootElement();
			List row = root.getChildren("pdv");

			for (int i = 0; i < row.size(); i++)
			{
				Element pdv = (Element) row.get(i);
				String idPdv = pdv.getAttribute("id").getValue();
				int cpPdv = Integer.parseInt(pdv.getAttribute("cp").getValue());
				if (30000 <= cpPdv && cpPdv < 35000)
				{
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
		}
		catch (Exception e)
		{
			System.out.println("Execption " + e.getMessage());

		}
		System.out.println("Done!");

	}// [End Method]

	public static void CreateMarkerFromBdd(Browser browser, JTextField txtLat, JTextField txtLong, JSlider slider,
			ArrayList<Station> ListeStations,String typeCarbu)
	{
		

		if (ListeStations != null)
		{						
			
			
			String cochColor = "";
			//Trouver le min et max des tarifs dans la ListeStations
			float max = 0;					// Initialisation a zero
			float min = 100000000;			// Initialisation sur un gd nombre
			float prixD = 0;
			float ecartTarif = 0;
			float borneOrange = 0;
			float borneRouge = 0;
			
			// Calcul Min Max
			for (Station st : ListeStations)
			{				
				int i = 0;
				int nbcarb = st.getCarburants().size();				
				// Determination de Min Max du Tarif du carburant selectionné
				while (i < nbcarb)
				{					
					if (st.getCarburants().get(i).getNom().equalsIgnoreCase(typeCarbu))
					{						
						prixD = st.getCarburants().get(i).getPrix() / 1000;						
						if(prixD > max) max = prixD;						
						if(prixD < min) min = prixD;						
					}
					i++;
				}
				//Calcul des paliers de colorisation
				ecartTarif = max - min;						
			}
				
				
			for (Station st : ListeStations)
			{
				//Calcul des paliers de colorisation
				borneOrange = min + ecartTarif /3;
				borneRouge = min + (ecartTarif /3)*2;
				int i = 0;
				int nbcarb = st.getCarburants().size();				
				// Determination de Min Max du Tarif du carburant selectionné
				while (i < nbcarb)
				{					
					if (st.getCarburants().get(i).getNom().equalsIgnoreCase(typeCarbu))
					{						
						prixD = st.getCarburants().get(i).getPrix() / 1000;												
					}
					i++;
				}
				//Selection de la couleur pour choix de l'icon du Marker
				if (prixD > borneRouge)	cochColor = cochRed;
				else if (prixD > borneOrange && prixD <= borneRouge) cochColor = cochYellow;
				else cochColor = cochGreen; 
				
				//Creation d'un Marker du package com.api.googlemaps.Marker.java
				new Marker(browser, st.getAdresse().getPosition().getCoordonnee().getLatitude(),
						st.getAdresse().getPosition().getCoordonnee().getLongitude(), st.getId().toString(),
						st.getAdresse().getRue(), st.getAdresse().getCodepostal(), st.getAdresse().getVille(),
						st.getNom(),st.getHeureOuverture(),st.getHeureFermeture(), cochColor, prixD, typeCarbu);
				}
		
			}
			
			
			
		}

	
}// [End Class]
