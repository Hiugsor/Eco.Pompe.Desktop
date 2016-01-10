package com.processing;
import com.processing.*;

public class GeoProcessing {

	//Attributs
	//Definition Maths
	public static final double PI = Math.PI;	
	//Definition de l'ellipsoide WGS84
	public static final double EarthRadius = 6378.137; //en km
	
	
	//Methodes
	public static double distance(Double lat1, Double lng1, Double lat2, Double lng2)
	{
		//convertion degres decimaux en radian		
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
	
	public static Point Polar(Double lat1, Double lng1, float distance, float azm)
	{
		//Conversion Degré vers Radian
		lat1 = lat1 * PI / 180;
		lng1 = lng1 * PI / 180;		
		
		//Calcul de la Distance Angulaire
		double angularDistance = distance/(EarthRadius*1000);	
		
		//Calcul Lat Long en Radian
		double newLat = Math.asin(Math.sin(lat1) * Math.cos(angularDistance) + Math.cos(lat1) * Math.sin(angularDistance) * Math.cos(azm));
		double newLng = lng1 + Math.atan2(Math.sin(azm) * Math.sin(angularDistance) * Math.cos(lat1), Math.cos(angularDistance) - Math.sin(lat1) * Math.sin(newLat));
		
		//Consersion Radian vers Degré
		newLat = newLat * 180 / PI;
		newLng = newLng * 180 / PI;
		Point pt = new Point("Point Calculé", newLat, newLng);
		return pt;			
	}
	
}
