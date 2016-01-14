package com.database.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.param.*;

public class UsineArequetes {
private static UsineArequetes _instance = null;
	
	private static Connection _connexion;
	
	private UsineArequetes()
	{}	
	
	private static Statement recupererPrepareStatement(String requeteSQL)
	{
		Statement statement = null;
		
		try 
		{
			statement =  _connexion.prepareStatement(requeteSQL);
		}
		catch (SQLException e) 
		{
			System.out.println("ERREUR DANS LA CREATION D'UNE REQUETE PARAMETREE DANS L'USINE");
			System.out.println(e.getMessage());
		}		
		catch (Exception e) 
		{
			System.out.println("ERREUR INCONNUE DANS <recupererPrepareStatement>");
			System.out.println(e.getMessage());	
		}		
		
		return statement;
	}
	
	private static Statement recupererStatement()
	{
		Statement statement = null;
		
		try 
		{
			statement =  _connexion.createStatement();
		}
		catch (SQLException e) 
		{
			System.out.println("ERREUR DANS LA CREATION D'UNE REQUETE STANDARD DANS L'USINE");
			System.out.println(e.getMessage());
		}		
		catch (Exception e) 
		{
			System.out.println("ERREUR INCONNUE DANS <recupererStatement>");
			System.out.println(e.getMessage());	
		}		
		
		return statement;
	}
		
	private static Statement recupererCallableStatement(String requeteSQL)
	{
		Statement statement = null;
		
		try 
		{
			statement =  _connexion.prepareCall(requeteSQL);
		}
		catch (SQLException e) 
		{
			System.out.println("ERREUR DANS LA CREATION D'UNE REQUETE DE PROCEDURE STOCKEE DANS L'USINE");
			System.out.println(e.getMessage());
		}		
		catch (Exception e) 
		{
			System.out.println("ERREUR INCONNUE DANS <recupererCallableStatement>");
			System.out.println(e.getMessage());	
		}	
		
		return statement;
	}
		
	/*
	public static Connection GetConnection()
	{
		return _connexion;		
	}
	*/
	
	public static final UsineArequetes GetInstance()
	{
		if(_instance == null)
		{	
			synchronized(UsineArequetes.class) 
			 {
				if (UsineArequetes._instance == null) 
	              {	            	  
	            	  	try 
	      				{	
	            	  		Class.forName(ConnexionParameters.getDriverUrl()).newInstance();
	            	  		UsineArequetes._instance = new UsineArequetes();
	            	  		
	      				}
	            	  	catch (ClassNotFoundException e) 
	        			{
	        				System.out.println("ERREUR A L'ENREGISTREMENT DU PILOTE DANS LA METHODE <GET INSTANCE>");
	        				System.out.println(e.getMessage());
	        			} 
	            	  	catch (InstantiationException e) 
	        			{
	        				System.out.println("ERREUR A L'INSTANCIATION DU SINGLETON DANS LA METHODE <GET INSTANCE>");
	        				System.out.println(e.getMessage());
	        			} 
	            	  	catch (IllegalAccessException e) 
	        			{	        				
	            	  		//An IllegalAccessException is thrown when an application tries 
	            	  		//to reflectively create an instance (other than an array), 
	            	  		//set or get a field, or 
	            	  		//invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor.
	            	  		System.out.println("ERREUR ILLEGALE DANS LA METHODE <GET INSTANCE>");
	        				System.out.println(e.getMessage());
	        			} 
	            	  	catch (Exception e) 
	        			{
	        				System.out.println("ERREUR INCONNUE DANS LA METHODE <GET INSTANCE>");
	        				System.out.println(e.getMessage());
	        			}
	        		  	finally 
	        			{
	            	  		//_instance = null;
	        			}
	              }
			 }
		}
		
		return _instance;
	}
		
	public boolean open()
	{
		if(_connexion == null)
		{
			boolean enErreur = false;
			
			try 
			{	
				_connexion = DriverManager.getConnection(ConnexionParameters.getDatabaseUrl(), ConnexionParameters.getUserName(), ConnexionParameters.getPassword());
				
				System.out.println("CONNEXION DESORMAIS OUVERTE");
				
				return true;
			} 
			catch (SQLException e) 
			{
				System.out.println("ERREUR A LA TENTATIVE DE CONNEXION DANS LA METHODE OPEN");
				System.out.println(e.getMessage());
				enErreur = true;
			}
			catch (Exception e) 
			{
				System.out.println("ERREUR INDETERMINEE DANS LA METHODE OPEN");
				System.out.println(e.getMessage());
				enErreur = true;
			}
			finally 
			{
			    if(enErreur)
			    {
					try { 
				    		if (_connexion != null)
					    	{
				    			System.out.println("TENTATIVE DE FERMETURE DE LA CONNEXION APRES LEVE D'EXCEPTION DANS LA METHODE OPEN");
				    			_connexion.close(); 
				    			_connexion = null;  
					    	}
						}
				    	catch (SQLException e) 
				    	{			    		
				    		System.out.println("ERREUR LORS DE LA FERMETURE DE LA CONNEXION APRES LEVE D'EXCEPTION DANS LA METHODE OPEN");
				    		e.printStackTrace();
				    	}			    	
					    catch (Exception e) 
						{
							System.out.println("ERREUR INDETERMINEE LORS DE LA FERMETURE DE LA CONNEXION APRES LEVE D'EXCEPTION DANS LA METHODE OPEN");
							System.out.println(e.getMessage());
						}
					
				    	return false;
			    }
			}
			
		}
		
		System.out.println("CONNEXION DEJA OUVERTE");
		return true;
	}
	
	public  boolean close()
	{
		if (_connexion != null)
    	{			
			boolean enErreur = false;
			
			try 
			{
				_connexion.close();
				_connexion = null;                     //http://stackoverflow.com/questions/18307524/difference-between-connection-close-and-connection-null
				System.out.println("CONNEXION DESORMAIS FERMEE");
				return true;
			} 
			catch (SQLException e) 
			{
				System.out.println("ERREUR LORS DE LA FERMETURE DE LA CONNEXION DANS LA METHODE CLOSE");
				System.out.println(e.getMessage());
				enErreur = true;
			}
			catch (Exception e) 
			{
				System.out.println("ERREUR INCONNUE DANS LA METHODE CLOSE");
				System.out.println(e.getMessage());
				enErreur = true;
			}
			finally 
			{				
				if(enErreur)
			    {
					System.out.println("LA TENTATIVE DE FERMETURE DE LA CONNEXION S'EST MAL PASSEE DANS LA METHODE CLOSE");
					return false;
			    }
			}
    	}
		
		System.out.println("CONNEXION DEJA FERMEE");
		
		return true;
	}

	public Statement recupererStatement(TypeStatement type, String requete)
	{		
		if((requete == null || requete.isEmpty()) && type != TypeStatement.Standard)
		{
			System.out.println("REQUETE INVALIDE DANS <recupererStatement>");
			return null;
		}
		
		if( _connexion == null)
		{
			System.out.println("CONNEXION FERMEE DANS <recupererStatement>");
			return null;
		}
		
		switch(type)
		{
			case Callable :
				return recupererCallableStatement(requete);
	
			case Prepared :
				return recupererPrepareStatement(requete);
	
			case Standard :
				return recupererStatement();
				
				default : 
					return null;		
		}
	}

}
