package com.database.Params;

public final class ConnexionParameters 
{	
	private static final String _databaseURL = "jdbc:mysql://10.111.61.87/ECOPOMPE"; 
	private static final String _password = "mereCastor"; 
	private static final String _userName = "remote";
	private static final String _driverUrl = "com.mysql.jdbc.Driver";
	
	public static String getDatabaseUrl() 
	{
		return _databaseURL;
	}

	public static String getPassword() 
	{
		return _password;
	}

	public static String getUserName() 
	{
		return _userName;
	}

	public static String getDriverUrl() 
	{
		return _driverUrl;
	}
	
	private ConnexionParameters()
	{}
}