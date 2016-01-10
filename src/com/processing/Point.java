package com.processing;

public class Point {

	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private double latitute;
	public double getLatitute() {
		return latitute;
	}
	public void setLatitute(double latitute) {
		this.latitute = latitute;
	}

	private double longitude;
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	
	
	
	public Point(String name, double lat, double lng)
	{
		this.name = name;
		this.latitute = lat;
		this.longitude = lng;
	}
}
