package com.processing;

public class Point {

	private double latitude;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitute(double latitude) {
		this.latitude = latitude;
	}

	private double longitude;
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public Point(double lat, double lng)
	{		
		this.latitude = lat;
		this.longitude = lng;
	}
}
