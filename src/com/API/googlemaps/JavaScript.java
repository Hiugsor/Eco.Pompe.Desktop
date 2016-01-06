package com.API.googlemaps;

import com.teamdev.jxbrowser.chromium.Browser;

public class JavaScript {

	public static final void deleteStations(Browser browser){
		browser.executeJavaScript("if (markerStationArray) {for (i in markerStationArray) {markerStationArray[i].setMap(null);} markerStationArray.length = 0;}"); //suppression des points stations
	}
	
	public static final void deletePosition(Browser browser){
		browser.executeJavaScript("if (markerPositionArray) {for (i in markerPositionArray) {markerPositionArray[i].setMap(null);} markerPositionArray.length = 0;}"); //suppression des points Positions
		
	}
}
