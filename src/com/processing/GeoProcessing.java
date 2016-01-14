package com.processing;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

import com.bo.Coordonnees;
import com.bo.Borders;
import com.bo.Point;
import com.bo.Recherche;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class GeoProcessing {

	// Attributs
	// Definition Maths
	public static final double PI = Math.PI;
	// Definition de l'ellipsoide WGS84
	public static final double EarthaRadiusWGS84 = 6378.137; // en km

	

	//Methodes
	/**
	 * Calcul de la distance entre deux points avec les coordonnées géographiques
	 * @param lat1d Double
	 * @param lng1d Double
	 * @param lat2d Double
	 * @param lng2d Double
	 * @return distance Double
	 */
	public static double getDistance(double lat1d, double lng1d, double lat2d, double lng2d)
	{
		//conversion degres decimaux en radian
		double lat1Rad = lat1d * PI / 180;
		double lng1Rad = lng1d * PI / 180;
		double lat2Rad = lat2d * PI / 180;
		double lng2Rad = lng2d * PI / 180;		
		//Formule de calcul de la distance (Arc)
		double distanceAngulaire = Math.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(lng2Rad-lng1Rad));
		double distanceKM = distanceAngulaire * EarthaRadiusWGS84;		
		return distanceKM;
	}
	 
	/**
	 * Calcul de l'azimuth entre deux points avec les coordonnées géographiques
	 * @param lat1Deg
	 * @param lng1Deg
	 * @param lat2Deg
	 * @param lng2Deg
	 * @return azimuth double
	 */
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
		
	

	/**
	 * Calcule les coordonnées d'un point en fonction d'une distance et d'un
	 * azimut
	 * 
	 * @param latitude
	 *            Type : Double
	 * @param longitude
	 *            Type : Double
	 * @param distance
	 *            Type : Float
	 * @param azimuth
	 *            Type : Float
	 * @return renvoie une position
	 */
	public static Point Polar(Double latitude, Double longitude, float distance, float azimuth) {
		double distanceAngulaire = distance / EarthaRadiusWGS84;
		latitude = latitude * PI / 180;
		longitude = longitude * PI / 180;
		azimuth = (float) (azimuth * PI / 180);
		double newLat = Math.asin(Math.sin(latitude) * Math.cos(distanceAngulaire)
				+ Math.cos(latitude) * Math.sin(distanceAngulaire) * Math.cos(azimuth));
		System.out.println("New Lat (Rad)" + newLat);
		double newLong = longitude + Math.atan2(Math.sin(azimuth) * Math.sin(distanceAngulaire) * Math.cos(latitude),
				Math.cos(distanceAngulaire) - Math.sin(latitude) * Math.sin(newLat));
		// Conversion rad en deg
		newLat = newLat * 180 / PI;
		newLong = newLong * 180 / PI;
		Point pt = new Point(newLat, newLong);
		return pt;
	}

	/**
	 * Permet de recuperer les coordonnées des coins Nord Ouest et Sud Est Carré qui inscrit le cercle du rayon de recherche
	 * @param latitudeDeg Double
	 * @param longitudeDeg Double
	 * @param rayon int
	 * @return
	 */
	public static Borders getWGS84FrameLimits(double latitudeDeg, double longitudeDeg, int rayon)
	{
		
		double distanceKM = rayon * Math.sqrt(2);
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


	/**
	 * Géolocalise une adresse incluse dans une recherche
	 * 
	 * @param recherche
	 *            critere de recherche : adresse ou position
	 * @return renvoie une position
	 */
	public static Point geolocalise(Recherche recherche) {

		LatLng long_lat;

		Point position = null;
		Coordonnees coordonnee = null;
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBI_p53rZMpZby3ZK93f0HgnK7UBE73zEM");

		SocketAddress addr = new InetSocketAddress("10.127.254.1", 80);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);

		context.setProxy(proxy);

		// System.out.println("CA COINCE ?");
		GeocodingResult[] results;

		try {

			// System.out.println("AVANT le results");
			results = GeocodingApi.geocode(context,
					recherche.getCritere().getAdresse().getRue() + ", "
							+ recherche.getCritere().getAdresse().getCodepostal() + ", "
							+ recherche.getCritere().getAdresse().getVille())
					.await();
			// System.out.println("APRES le results");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return position;
	}

}
