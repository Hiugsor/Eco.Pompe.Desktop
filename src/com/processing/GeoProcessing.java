package com.processing;


public class GeoProcessing {

	//Attributs
	//Definition Maths
	public static final double PI = Math.PI;	
	//Definition de l'ellipsoide WGS84
	public static final double EarthRadius = 6378.137; //en km
	
	
	//Methodes
	public static double distance(Double lat1, Double lng1, Double lat2, Double lng2)
	{
		//Conversion Degrés decimaux en radian		
		lat1 = lat1 * PI / 180;
		lng1 = lng1 * PI / 180;
		lat2 = lat2 * PI / 180;
		lng2 = lng2 * PI / 180;		
		return EarthRadius * Math.acos(Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2-lng1) + Math.sin(lat1) * Math.sin(lat2));		
	}
	
	public static float azimuth(Double lat1, Double lng1, Double lat2, Double lng2)
	{
		float azm = (float) Math.toDegrees(Math.atan2(lng2 - lng1, lat2 - lat1));
	    if(azm < 0){
	    	azm = azm % 360;
	    }
	    return azm;
	}
			
	//Nota pour calculer le point du carré Nord-Ouest 
	// dist = r * Math.sqrt(2) avec Azm @ 315°	
	//Nota pour calculer le point du carré Sud-Est 
	// dist = r * Math.sqrt(2) avec Azm @ 135°
	
	public static Point Polar(Double latOrigine, Double longOrigine, float distance, float azm)
	{
		//Conversion Degrés decimaux en radian	
		latOrigine = latOrigine * PI / 180;
		longOrigine = longOrigine * PI / 180;		
		
		//Calcul de la Distance Angulaire en metre
		double angularDistance = distance/(EarthRadius*1000);	
		
		//Calcul Lat Long en Radian
		double newLat = Math.asin(Math.sin(latOrigine) * Math.cos(angularDistance) + Math.cos(latOrigine) * Math.sin(angularDistance) * Math.cos(azm));
		double newLng = longOrigine + Math.atan2(Math.sin(azm) * Math.sin(angularDistance) * Math.cos(latOrigine), Math.cos(angularDistance) - Math.sin(latOrigine) * Math.sin(newLat));
		
		//Consersion Radian vers Degrés dec
		newLat = newLat * 180 / PI;
		newLng = newLng * 180 / PI;
		Point pt = new Point("Point Recherché", newLat, newLng);
		return pt;			
	}
	
}
