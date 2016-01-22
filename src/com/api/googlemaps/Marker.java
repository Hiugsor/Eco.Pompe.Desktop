package com.api.googlemaps;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextField;

import com.bo.Station;
import com.fileparser.XMLParser;
import com.processing.GestionRecherche;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.demo.JxBrowserDemo;

public class Marker {
	
	
	/**
	 * Creation d'un marker POSITION DEPART
	 * @param browser
	 * @param txtLAT
	 * @param txtLONG
	 */
	public Marker(Browser browser, JTextField txtLAT, JTextField txtLONG)
	{
		
		String contentString = "<img src=\"img/farmer.png\" ALT=\"missing pics\"><h3>Vous êtes ici</h3><br/> Lat : " + txtLAT.getText() + "<br/> Long : " + txtLONG.getText();
		browser.executeJavaScript(
				"var myLatlng = new google.maps.LatLng(" + txtLAT.getText() + "," + txtLONG.getText() + ");\n"
			  + "var myinfowindow = new google.maps.InfoWindow({content: '"+ contentString +"' });" 
			  + "var marker = new google.maps.Marker({\n" + "position: myLatlng,\n"
			  + "map: map,\n"
			  + "draggable: true,\n"
			  + "infowindow: myinfowindow,\n"
			  + "title: '" + "Position" + "'\n" + "});"
		      + "marker.addListener('click', function() {this.infowindow.open(map, this);});"
		      + "marker.setIcon('img/farmer.png');"
			  + "markerPositionArray.push(marker);"
			  );
	}
	
	
	/**
	 * Creation d'un marker STATION
	 * @param browser
	 * @param lat = latitude
	 * @param lng = longitude
	 * @param column = ID point
	 */
	public Marker(Browser browser, double lat, double lng, String ID, String adr, String CP, String ville, String nom, Time horaireOuv, Time horaireFer  )
	{		
		
		String pathPics = "img/miniCochon.png";
		
		String contentString = "<img src=\"img/miniCochon.png\" ALT=\"Station\"><h1>"
				+ nom 
				+ "</h1><br/> Lat : " + lat 
				+ "<br/> Long : " + lng 
				+ "<br/> Adresse : " + adr 
				+ "<br/> CP : " + CP 
				+ "<br/> Ville : "+ ville.toUpperCase()
				+ "<br/><br/> Horaire ouverture : " + horaireOuv 
				+ "<br/> Horaire fermeture : "+ horaireFer;
		
		browser.executeJavaScript(
				"var myLatlng = new google.maps.LatLng(" + lat + "," + lng + ");\n"
			  + "var myinfowindow = new google.maps.InfoWindow({content: '"+ contentString +"' });"
			  + "var marker = new google.maps.Marker({\n" 
			  + "position: myLatlng,\n"
			  + "map: map,\n"
			  + "infowindow: myinfowindow,\n"
			  + "title: '" +  ID + "'\n" + "});\n"
			  + "marker.setIcon('" + pathPics + "');"
			  + "marker.addListener('click', function() {this.infowindow.open(map, this);});"
			  + "marker.setIcon('img/miniCochon.png');"
			  + "markerStationArray.push(marker);"
			  );
	}
}
