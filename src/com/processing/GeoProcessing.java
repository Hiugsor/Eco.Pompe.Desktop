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
		//convertion degres decimaux en radian		
		lat1 = lat1 * PI / 180;
		lng1 = lng1 * PI / 180;
		lat2 = lat2 * PI / 180;
		lng2 = lng2 * PI / 180;		
		return EarthRadius * Math.acos(Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng2-lng1) + Math.sin(lat1) * Math.sin(lat2));		
	}
	 
	
	/*public static float azimuth(Double lat1, Double lng1, Double lat2, Double lng2)
	{
		lat1 = lat1 * PI / 180;
		lng1 = lng1 * PI / 180;
		lat2 = lat2 * PI / 180;
		lng2 = lng2 * PI / 180;	
		float azm = (float) Math.toDegrees(Math.atan2(lat2 - lat1, lng2 - lng1));
	    if(azm < 0 || azm > 360)
	    {
	    	azm = azm % 360;
	    }
	    return azm;
	}*/
		
	
	public static Point Polar(Double latitude, Double longitude, float distance, float azimuth)
	{
		double distanceAngulaire = distance/EarthRadius;
		latitude = latitude * PI /180;
		longitude = longitude * PI / 180;
		azimuth = (float) (azimuth * PI / 180);
		double newLat = Math.asin(Math.sin(latitude)* Math.cos(distanceAngulaire) + Math.cos(latitude)*Math.sin(distanceAngulaire)*Math.cos(azimuth));
		System.out.println("New Lat (Rad)" + newLat);
		double newLong = longitude + Math.atan2(Math.sin(azimuth) * Math.sin(distanceAngulaire) * Math.cos(latitude), Math.cos(distanceAngulaire) - Math.sin(latitude) * Math.sin(newLat));
		//Conversion rad en deg
		newLat = newLat * 180 / PI;
		newLong = newLong * 180 / PI;		
		Point pt = new Point("Point Calculé", newLat, newLong);
		return pt;		
	}
	
	
	public static Limite calculLimiteZone(double latitude, Double longitude, float distance)
	{
		double angularDistance = distance/EarthRadius;
		
		//Azimuth des points encadrant le cercle de recherche
		float azmNO = 315;
		float azmSE = 135;
				
		//Conversion Degré vers Radian		
		latitude = latitude * PI /180;
		longitude = longitude * PI / 180;
		azmNO = (float) (azmNO * PI / 180);	
		azmSE = (float) (azmSE * PI / 180);	
		
		//Calcul de la Latitude du Point du cadre Nord Ouest
		double latNO = Math.asin(Math.sin(latitude)* Math.cos(angularDistance) + Math.cos(latitude)*Math.sin(angularDistance)*Math.cos(azmNO));
		//Calcul de la Longitude du Point du cadre Nord Ouest
		double longNO = longitude + Math.atan2(Math.sin(azmNO) * Math.sin(angularDistance) * Math.cos(latitude), Math.cos(angularDistance) - Math.sin(latitude) * Math.sin(latNO));
		//Conversion rad en deg
		latNO = latNO * 180 / PI;
		longNO = longNO * 180 / PI;		
		Point ptNO = new Point("Point Nord Ouest", latNO, longNO);
		
		
		//Calcul de la Latitude du Point du cadre Sud Est
		double latSE = Math.asin(Math.sin(latitude)* Math.cos(angularDistance) + Math.cos(latitude)*Math.sin(angularDistance)*Math.cos(azmSE));
		//Calcul de la Longitude du Point du cadre Sud Est
		double longSE = longitude + Math.atan2(Math.sin(azmSE) * Math.sin(angularDistance) * Math.cos(latitude), Math.cos(angularDistance) - Math.sin(latitude) * Math.sin(latSE));
		//Conversion rad en deg
		latSE = latSE * 180 / PI;
		longSE = longSE * 180 / PI;		
		Point ptSE = new Point("Point Sud Est", latSE, longSE);
		
		return new Limite(ptNO, ptSE);		
	}	
}
