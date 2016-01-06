package com.API.googlemaps;

import javax.swing.*;

import com.API.googlemaps.Marker;
import com.teamdev.jxbrowser.chromium.Browser;

public class Circle 
{
	public Circle(Browser browser, JTextField txtLAT, JTextField txtLONG, JSlider slider)
	{		
		browser.executeJavaScript("myArea.setMap(null);"); //suppression du cercle precedent si existe
		browser.executeJavaScript("var myArea = new google.maps.Circle({center:new google.maps.LatLng(" + txtLAT.getText() +"," + txtLONG.getText() +"),radius:"+ Integer.toString(slider.getValue()*1000) + ",strokeColor:\"#0000FF\",strokeOpacity:0.5,strokeWeight:2,fillColor:\"#0000FF\",fillOpacity:0.1});\n"
			                    + "myArea.setMap(map);\n");
		JavaScript.deletePosition(browser); //suppression des points Positions
		JavaScript.deleteStations(browser); //suppression des points Stations
		new Marker(browser, txtLAT, txtLONG);		// Marker central avec couleur differente des stations
	}
}
