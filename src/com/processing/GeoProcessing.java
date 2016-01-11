package com.processing;


public class GeoProcessing {

	//Attributs
	public static final double PI = Math.PI;
	public static final double EarthaRadiusWGS84 = 6378.137; //Definition de l'ellipsoide WGS84en km	
	
	
	//Methodes
	public static double getDistance(double lat1d, double lng1d, double lat2d, double lng2d)
	{
		//conversion degres decimaux en radian
		double lat1Rad = lat1d * PI / 180;
		double lng1Rad = lng1d * PI / 180;
		double lat2Rad = lat2d * PI / 180;
		double lng2Rad = lng2d * PI / 180;		
		//Formule de calcul de la distance
		double distanceAngulaire = Math.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(lng2Rad-lng1Rad));
		double distanceKM = distanceAngulaire * EarthaRadiusWGS84;		
		return distanceKM;
	}
	 
	
	public static double getAzimuth(double lat1Deg, double lng1Deg, double lat2Deg, double lng2Deg)
	{
		//conversion degres decimaux en radian
		double lat1Rad = lat1Deg * PI / 180;
		double lng1Rad = lng1Deg * PI / 180;
		double lat2Rad = lat2Deg * PI / 180;
		double lng2Rad = lng2Deg * PI / 180;		
		//Formule de calcul du Gisement
		double bearingRad = Math.atan2(Math.sin(lng2Rad-lng1Rad) * Math.cos(lat2Rad), Math.cos(lat1Rad) * Math.sin(lat2Rad) - Math.sin(lat1Rad) * Math.cos(lat2Rad));
		double bearingDeg = ((bearingRad * 180 / PI) + 360) % 360;		
	    return bearingDeg;
	}
		
	
	public static Point PolarToWGS84(double latitudeDeg, double longitudeDeg, float distanceKM, float azimuthDeg)
	{
		double distanceAngulaireRad = distanceKM / EarthaRadiusWGS84;
		//conversion degres decimaux en radian	
		double latitudeRad = latitudeDeg * PI /180;
		double longitudeRad = longitudeDeg * PI / 180;
		double azimuthRad = (float) (azimuthDeg * PI / 180);
		//Calcul de la Latitude en Radian
		double newLatRad = Math.asin(Math.sin(latitudeRad)* Math.cos(distanceAngulaireRad) + Math.cos(latitudeRad)*Math.sin(distanceAngulaireRad)*Math.cos(azimuthRad));
		//Calcul de la Longitude en Radian
		double newLongRad = longitudeRad + Math.atan2(Math.sin(azimuthRad) * Math.sin(distanceAngulaireRad) * Math.cos(latitudeRad), Math.cos(distanceAngulaireRad) - Math.sin(latitudeRad) * Math.sin(newLatRad));
		//Conversion Rad en Deg
		double newLatDeg = newLatRad * 180 / PI;
		double newLongDeg = newLongRad * 180 / PI;
		//Creation du Point pour sortie
		Point pt = new Point(newLatDeg, newLongDeg);
		return pt;		
	}
	
	
	public static Borders getWGS84FrameLimits(double latitudeDeg, double longitudeDeg, int distanceKM)
	{
		double distanceAngulaireRad = distanceKM / EarthaRadiusWGS84;
		
		//Azimuth en Radian des points encadrant la zone de recherche
		float azmRadNO = (float) (315 * PI / 180);
		float azmRadSE = (float) (135 * PI / 180);
				
		//Conversion Degré vers Radian		
		double latitudeRad = latitudeDeg * PI /180;
		double longitudeRad = longitudeDeg * PI / 180;
		
		//Calcul de la Latitude du Point du cadre Nord Ouest
		double newLatRadNO = Math.asin(Math.sin(latitudeRad)* Math.cos(distanceAngulaireRad) + Math.cos(latitudeRad)*Math.sin(distanceAngulaireRad)*Math.cos(azmRadNO));
		//Calcul de la Longitude du Point du cadre Nord Ouest
		double newLongRadNO = longitudeRad + Math.atan2(Math.sin(azmRadNO) * Math.sin(distanceAngulaireRad) * Math.cos(latitudeRad), Math.cos(distanceAngulaireRad) - Math.sin(latitudeRad) * Math.sin(newLatRadNO));
		//Conversion rad en deg
		double newLatDegNO = newLatRadNO * 180 / PI;
		double newLongDegNO = newLongRadNO * 180 / PI;		
		
		//Calcul de la Latitude du Point du cadre Sud Est
		double newLatRadSE = Math.asin(Math.sin(latitudeRad)* Math.cos(distanceAngulaireRad) + Math.cos(latitudeRad)*Math.sin(distanceAngulaireRad)*Math.cos(azmRadSE));
		//Calcul de la Longitude du Point du cadre Sud Est
		double newLongRadSE = longitudeRad + Math.atan2(Math.sin(azmRadSE) * Math.sin(distanceAngulaireRad) * Math.cos(latitudeRad), Math.cos(distanceAngulaireRad) - Math.sin(latitudeRad) * Math.sin(newLatRadSE));
		//Conversion rad en deg
		double newLatDegSE = newLatRadSE * 180 / PI;
		double newLongDegSE = newLongRadSE * 180 / PI;		
				
		return new Borders(new Point(newLatDegNO, newLongDegNO), new Point(newLatDegSE, newLongDegSE));		
	}
	
	
	public static String convert_DegDEC_to_DegSEXA(double angle)
	{
		boolean isNegative = false;
		String conversion = "";
		if (angle<0) {
			angle = Math.abs(angle);
			isNegative = true;
		}		
		//Calul des degres
		int Deg = (int) angle;
		//Calcul des Minutes
		double DecimalDeg = angle - Deg;		
		double Minute = DecimalDeg * 60;
		int Min = (int) Minute;
		//Calcul des Secondes
		double DecimalMin = Minute - Min;		
		double Secondes = DecimalMin * 60;		
		if (isNegative) {
			conversion = "-"+ Deg + "°" + Min + "\'"  + Secondes + "\"";
			
		}
		else
		{
			conversion = Deg + "°" + Min + "\'"  + Secondes + "\"";
		}
		return conversion;
	} 
	
	
	// ////////////////////////////////////////////////////////////////////////////////////////////
	//Exemple d'usage
	/*
	 * 			// Test Temporaire de calcul de coordonnées géographique
				//Point de Depart
				double latOrigine = Double.parseDouble(txtLAT.getText());//43.610769;
				double lngOrigine = Double.parseDouble(txtLONG.getText());//3.876622;										
							
				//Calcul des coordonnées d'un point par methode polaire  (Lat Départ, Long Départ , Distance en km)
				com.processing.Borders border = GeoProcessing.getWGS84FrameLimits(latOrigine, lngOrigine, slider.getValue());
				
				//Recuperation des données
				//Border Nord Ouest
				System.out.println("Point Nord Ouest");
				System.out.println("Lat >>> "+ border.getBorderNO().getLatitude() + " ou "  + GeoProcessing.convert_DegDEC_to_DegSEXA(border.getBorderNO().getLatitude()));
				System.out.println("Long>>> "+ border.getBorderNO().getLongitude() + " ou "  + GeoProcessing.convert_DegDEC_to_DegSEXA(border.getBorderNO().getLongitude()));
				//Controle
				double distNO = GeoProcessing.getDistance(latOrigine, lngOrigine, border.getBorderNO().getLatitude(), border.getBorderNO().getLongitude());
				System.out.println("Distance (km): " + distNO);
				double azmNO = GeoProcessing.getAzimuth(latOrigine, lngOrigine, border.getBorderNO().getLatitude(), border.getBorderNO().getLongitude());
				System.out.println("Azimuth (Deg): " + azmNO);
				
				System.out.println("");
				
				//Border Sud Est
				System.out.println("Point Sud Est");
				System.out.println("Lat >>> "+ border.getBorderSE().getLatitude() + " ou "  + GeoProcessing.convert_DegDEC_to_DegSEXA(border.getBorderSE().getLatitude()));
				System.out.println("Long>>> "+ border.getBorderSE().getLongitude() + " ou "  + GeoProcessing.convert_DegDEC_to_DegSEXA(border.getBorderSE().getLongitude()));
				//Controle				
				double distSE = GeoProcessing.getDistance(latOrigine, lngOrigine, border.getBorderSE().getLatitude(), border.getBorderSE().getLongitude());
				System.out.println("Distance (km): " + distSE);
				double azmSE = GeoProcessing.getAzimuth(latOrigine, lngOrigine, border.getBorderSE().getLatitude(), border.getBorderSE().getLongitude());
				System.out.println("Azimuth (Deg): " + azmSE);
				System.out.println("");
	 * */
	
	// ////////////////////////////////////////////////////////////////////////////////////////////
}
