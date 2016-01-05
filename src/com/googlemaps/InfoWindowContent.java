package com.googlemaps;

import javax.swing.JTextField;

public class InfoWindowContent {

	public static final String contentString = "";
	
	//String contentString = "<img src=\"https://maps.google.com/mapfiles/ms/icons/blue-dot.png\" ALT=\"missing pics\"><h3>Vous êtes ici</h3><br/> Lat : " + txtLAT.getText() + "<br/> Long : " + txtLONG.getText();
	//"var myinfowindow = new google.maps.InfoWindow({content: '"+ contentString +"' });" 
	
	//EN COURS....
	
	public InfoWindowContent(String sourceIMG, JTextField txtLAT, JTextField txtLONG){
		String baliseIMG = "<img src=\"" + sourceIMG + "\" ALT=\"missing pics\">";
		String baliseHeader3 = "<h3>Vous êtes ici</h3>";
		String br = "<br/>";
		String latitude = " Lat : \""+ txtLAT.getText();
		String longitute = " Long : \""+ txtLONG.getText();
	}
	
	
}
