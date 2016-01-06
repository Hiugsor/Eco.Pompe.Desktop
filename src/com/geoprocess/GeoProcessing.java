package com.geoprocess;

public class GeoProcessing {

	//Attributs
	//Definition Maths
	public static final double PI = Math.PI;	
	//Definition de l'ellipsoide WGS84
	public static final double EarthRadius = 6378.137;
	
	
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
	
}
