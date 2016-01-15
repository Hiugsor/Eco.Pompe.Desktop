package com.processing;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

import com.bo.Borders;
import com.bo.Coordonnees;
import com.bo.Point;
import com.bo.Recherche;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class GeoProcessing_xtof {

	// Attributs
	public static final double PI = Math.PI;
	public static final double EarthaRadiusWGS84 = 6378.137; // Definition de
																// l'ellipsoide
																// WGS84 en km

	// Methodes
	/**
	 * Get the Distance between two points based on the WGS84 Coordinates
	 * submitted
	 * 
	 * @param lat1d
	 *            double
	 * @param lng1d
	 *            double 
	 * @param lat2d
	 *            double
	 * @param lng2d
	 *            double
	 * @return distance in km
	 */
	public static double getDistance(double lat1d, double lng1d, double lat2d, double lng2d) {
		// conversion degres decimaux en radian
		double lat1Rad = lat1d * PI / 180;
		double lng1Rad = lng1d * PI / 180;
		double lat2Rad = lat2d * PI / 180;
		double lng2Rad = lng2d * PI / 180;
		// Formule de calcul de la distance (Arc)
		double distanceAngulaire = Math.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad)
				+ Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(lng2Rad - lng1Rad));
		double distanceKM = distanceAngulaire * EarthaRadiusWGS84;
		return distanceKM;
	}

	/**
	 * Get the Azimuth between two points based on the WGS84 Coordinates
	 * submitted
	 * 
	 * @param lat1d
	 *            double
	 * @param lng1d
	 *            double
	 * @param lat2d
	 *            double
	 * @param lng2d
	 *            double
	 * @return azimuth in decimal degres
	 */
	public static double getAzimuth(double lat1Deg, double lng1Deg, double lat2Deg, double lng2Deg) {
		// conversion degres decimaux en radian
		double lat1Rad = lat1Deg * PI / 180;
		double lng1Rad = lng1Deg * PI / 180;
		double lat2Rad = lat2Deg * PI / 180;
		double lng2Rad = lng2Deg * PI / 180;
		// Formule de calcul du Gisement
		double bearingRad = Math.atan2(Math.sin(lng2Rad - lng1Rad) * Math.cos(lat2Rad),
				Math.cos(lat1Rad) * Math.sin(lat2Rad) - Math.sin(lat1Rad) * Math.cos(lat2Rad));
		double bearingDeg = ((bearingRad * 180 / PI) + 360) % 360;
		return bearingDeg;
	}

	/**
	 * Get the WGS84 coordinates of end Point from start point, distance and
	 * azimuth - Using method Polar to Rectangular
	 * 
	 * @param latitudeDeg
	 * @param longitudeDeg
	 * @param distanceKM
	 * @param azimuthDeg
	 * @return Point with Lat & Long
	 */
	public static Point PolarToWGS84(double latitudeDeg, double longitudeDeg, float distanceKM, float azimuthDeg) {
		double distanceAngulaireRad = distanceKM / EarthaRadiusWGS84;
		// conversion degres decimaux en radian
		double latitudeRad = latitudeDeg * PI / 180;
		double longitudeRad = longitudeDeg * PI / 180;
		double azimuthRad = (float) (azimuthDeg * PI / 180);
		// Calcul de la Latitude en Radian
		double newLatRad = Math.asin(Math.sin(latitudeRad) * Math.cos(distanceAngulaireRad)
				+ Math.cos(latitudeRad) * Math.sin(distanceAngulaireRad) * Math.cos(azimuthRad));
		// Calcul de la Longitude en Radian
		double newLongRad = longitudeRad
				+ Math.atan2(Math.sin(azimuthRad) * Math.sin(distanceAngulaireRad) * Math.cos(latitudeRad),
						Math.cos(distanceAngulaireRad) - Math.sin(latitudeRad) * Math.sin(newLatRad));
		// Conversion Rad en Deg
		double newLatDeg = newLatRad * 180 / PI;
		double newLongDeg = newLongRad * 180 / PI;
		// Creation du Point pour sortie
		Point pt = new Point(newLatDeg, newLongDeg);
		return pt;
	}

	
	public static Borders getWGS84FrameLimits(double latitudeDeg, double longitudeDeg, int rayon)
	{
		
		double distanceKM = rayon * Math.sqrt(2);
		double distanceAngulaireRad = distanceKM / EarthaRadiusWGS84;
		
		//Azimuth en Radian des points encadrant la zone de recherche
		float azmRadNO = (float) (315 * PI / 180);
		float azmRadSE = (float) (135 * PI / 180);
				
		//Conversion Degr� vers Radian		
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

	/**
	 * Convert Decimal Degres to Sexagesimal Degres
	 * 
	 * @param angle
	 *            double
	 * @return angle in D�M'S"
	 */
	public static String convert_DegDEC_to_DegSEXA(double angle) {
		boolean isNegative = false;
		String conversion = "";
		if (angle < 0) {
			angle = Math.abs(angle);
			isNegative = true;
		}
		// Calul des degres
		int Deg = (int) angle;
		// Calcul des Minutes
		double DecimalDeg = angle - Deg;
		double Minute = DecimalDeg * 60;
		int Min = (int) Minute;
		// Calcul des Secondes
		double DecimalMin = Minute - Min;
		double Secondes = DecimalMin * 60;
		if (isNegative) {
			conversion = "-" + Deg + "�" + Min + "\'" + Secondes + "\"";

		} else {
			conversion = Deg + "�" + Min + "\'" + Secondes + "\"";
		}
		return conversion;
	}

	/**
	 * Convert Sexagesimal Degres to Decimal Degres
	 * 
	 * @param angle
	 *            double
	 * @return angle in D.ddd
	 */
	public static double convert_DegSEXA_to_DegDEC(String angle) {
		String sepDeg = "�";
		String sepMin = "\'";
		String sepSec = "\"";

		int posDeg = angle.indexOf(sepDeg);
		System.out.println(posDeg);
		int posMin = angle.indexOf(sepMin);
		System.out.println(posMin);
		int posSec = angle.indexOf(sepSec);
		System.out.println(posSec);

		double deg = Double.parseDouble(angle.substring(0, posDeg));
		System.out.println("Degres " + deg);
		double min = Double.parseDouble(angle.substring(posDeg + 1, posMin));
		System.out.println("Minutes " + min);
		double sec = Double.parseDouble(angle.substring(posMin + 1, posSec));
		System.out.println("Secondes " + sec);

		sec = sec / 60;
		min = min / 60;

		System.out.println(sec);
		System.out.println(min);

		return min;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////
	// Exemple d'usage
	/*
	 * // Test Temporaire de calcul de coordonn�es g�ographique //Point de
	 * Depart double latOrigine =
	 * Double.parseDouble(txtLAT.getText());//43.610769; double lngOrigine =
	 * Double.parseDouble(txtLONG.getText());//3.876622;
	 * 
	 * //Calcul des coordonn�es d'un point par methode polaire (Lat D�part, Long
	 * D�part , Distance en km) com.processing.Borders border =
	 * GeoProcessing.getWGS84FrameLimits(latOrigine, lngOrigine,
	 * slider.getValue());
	 * 
	 * //Recuperation des donn�es //Border Nord Ouest System.out.println(
	 * "Point Nord Ouest"); System.out.println("Lat >>> "+
	 * border.getBorderNO().getLatitude() + " ou " +
	 * GeoProcessing.convert_DegDEC_to_DegSEXA(border.getBorderNO().getLatitude(
	 * ))); System.out.println("Long>>> "+ border.getBorderNO().getLongitude() +
	 * " ou " +
	 * GeoProcessing.convert_DegDEC_to_DegSEXA(border.getBorderNO().getLongitude
	 * ())); //Controle double distNO = GeoProcessing.getDistance(latOrigine,
	 * lngOrigine, border.getBorderNO().getLatitude(),
	 * border.getBorderNO().getLongitude()); System.out.println(
	 * "Distance (km): " + distNO); double azmNO =
	 * GeoProcessing.getAzimuth(latOrigine, lngOrigine,
	 * border.getBorderNO().getLatitude(), border.getBorderNO().getLongitude());
	 * System.out.println("Azimuth (Deg): " + azmNO);
	 * 
	 * System.out.println("");
	 * 
	 * //Border Sud Est System.out.println("Point Sud Est"); System.out.println(
	 * "Lat >>> "+ border.getBorderSE().getLatitude() + " ou " +
	 * GeoProcessing.convert_DegDEC_to_DegSEXA(border.getBorderSE().getLatitude(
	 * ))); System.out.println("Long>>> "+ border.getBorderSE().getLongitude() +
	 * " ou " +
	 * GeoProcessing.convert_DegDEC_to_DegSEXA(border.getBorderSE().getLongitude
	 * ())); //Controle double distSE = GeoProcessing.getDistance(latOrigine,
	 * lngOrigine, border.getBorderSE().getLatitude(),
	 * border.getBorderSE().getLongitude()); System.out.println(
	 * "Distance (km): " + distSE); double azmSE =
	 * GeoProcessing.getAzimuth(latOrigine, lngOrigine,
	 * border.getBorderSE().getLatitude(), border.getBorderSE().getLongitude());
	 * System.out.println("Azimuth (Deg): " + azmSE); System.out.println("");
	 */

	// ////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Get Geolocation by typing the entire Adresse
	 * 
	 * @param recherche
	 *            Recherche
	 * @return Point
	 */
	public static Point geolocalise(Recherche recherche) {
		LatLng long_lat;
		Point position = null;
		Coordonnees coordonnee = null;
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBI_p53rZMpZby3ZK93f0HgnK7UBE73zEM");

		SocketAddress addr = new InetSocketAddress("10.127.254.1", 80);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
		context.setProxy(proxy);
		GeocodingResult[] results;

		try {

			results = GeocodingApi.geocode(context,
					recherche.getCritere().getAdresse().getRue() + ", "
							+ recherche.getCritere().getAdresse().getCodepostal() + ", "
							+ recherche.getCritere().getAdresse().getVille())
					.await();
			if (results.length != 0) {
				// System.out.println(results[0].geometry.location);
				long_lat = results[0].geometry.location;
				position = new Point();
				coordonnee = new Coordonnees();
				coordonnee.setLatitude(long_lat.lat);
				coordonnee.setLongitude(long_lat.lng);
				position.setCoordonnee(coordonnee);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return position;
	}
}
