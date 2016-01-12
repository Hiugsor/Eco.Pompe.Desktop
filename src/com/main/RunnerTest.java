package com.main;

import DAL.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

import org.jdom2.Element;

import Boite_a_outil.LectureFichier;
import Boite_a_outil.traitementString;
import ecopompe.BO.*;
import com.xmlparser.*;

public class RunnerTest {


	static void insertServices() {

		//***************lecture du fichier texte
		String path = "src\\DAL\\services.txt";

		//remplissage de la table services
		AccesDonneesServices.insertServices(LectureFichier.readTXT(path));
	}


	static void insertCarburants() {

		//remplissage de la table carburants
		List<String> carbus= new ArrayList<String>();
		carbus.add("Gazole");
		carbus.add("SP95");
		carbus.add("E85");
		carbus.add("GPLc");
		carbus.add("E10");
		carbus.add("SP98");
		AccesDonneesCarburants.insertCarburants(carbus);
	}

	static void insertJours() {

		//remplissage de la table jourss
		List<String> jours= new ArrayList<String>();
		jours.add("lundi");
		jours.add("mardi");
		jours.add("mercredi");
		jours.add("jeudi");
		jours.add("vendredi");
		jours.add("samedi");
		jours.add("dimanche");
		
		AccesDonneesJours.insertJours(jours);
	}
	
	static void insertStations(String path) {

		//remplissage de la table stations	
		 
		
		try {
			AccesDonneesStation.insertStations(LectureFichier.readCSV(path));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void insertInfoStations(String path) {

		//remplissage de la table stations	
		 
		
		try {
			AccesDonneesStation.insertInfoStations(LectureFichier.readCSV(path));
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void insertNonTravailler(int idStation,String colonne) {

		//remplissage de la table non travailler
		try {
			
			//String colonne= "mardi/dimanche"; // a récuperer du fichier CSV
			//int idStation = 9999999; // a modifier
			List<String> nomJours= traitementString.recupererDonnées(colonne, "/");
			
			AccesDonneesNonTravailler.insertNonTravailler(idStation,nomJours);
			
		
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	}
	
	static void insertProposer(int idStation, String colonne) {

		//remplissage de la table non travailler
		try {
			
			//String colonne= "bar/GPL"; // a récuperer du fichier CSV
			//int idStation = 9999999; // a modifier
			List<String> nomServices = traitementString.recupererDonnées(colonne, "/");
			
			AccesDonneesProposer.insertProposer(idStation, nomServices);
			
		
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	}

	static void insertVendre(int idStation,String colonne){

		//remplissage de la table non vendre
		try {
			
			//String colonne= "Gazole,1,2015-12-10T10:12:48,1027/SP95,2,2015-12-10T10:12:48,1259"; // a récuperer du fichier CSV
			//int idStation = 9999999; // a modifier
			List<String> listeCarbus = traitementString.recupererDonnées(colonne, "/");
			System.out.println(listeCarbus.size());
			for (String listeCarbu : listeCarbus) {
				//List<String> listePrix = traitementString.recupererDonnées(listeCarbu, ";");
				System.out.println("listecarbu "+listeCarbu);
				AccesDonneesVendre.insertVendre(idStation,listeCarbu );
			}
			
			//AccesDonneesProposer.insertProposer(idStation, nomServices);
			
		
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub


		String path = "src/Data/PrixCarburants_quotidien_20160104_final_6.csv";
			
		
		if(ConnexionManager.GetInstance().open() == false){				
			System.out.println("ATTENTION LA METHODE OPEN DONNE UN RESULTAT FAUX DANS <AccesDonneesService> / <inertion service>");
		}

		ConnexionManager.GetInstance().GetConnection().setAutoCommit(false);


		try {

			//Appel  de la méthode de remplissage de la table services
			//insertServices();

			//Appel  de la méthode de remplissage de la table Carburants 
			//insertCarburahnt();

			//Appel  de la méthode de remplissage de la table Jours 
			//insertJours();

			
			//Appel  de la méthode de remplissage de la table Stations 
			//insertStations(path);
			//System.out.println("Stations inserré");
			
			// Appel de la méthode pour l'insertion des infos des stations
			//insertInfoStations(path);
			
			//Appel  de la méthode de remplissage de la table NonTravailler
			//insertNonTravailler(path);
			
			//Appel  de la méthode de remplissage de la table Proposer
			//insertProposer();
			
			//Appel  de la méthode de remplissage de la table Vendre
			//insertVendre();
			
			
			
			
			
			//execute les requetes préparé
			ConnexionManager.GetInstance().GetConnection().commit();


		}catch (Exception e){
			ConnexionManager.GetInstance().GetConnection().rollback();
			System.out.println(e.toString());
		}

		if(ConnexionManager.GetInstance().close() == false)
		{
			System.out.println("ATTENTION LA METHODE CLOSE DONNE UN RESULTAT FAUX DANS <AccesDonneesService> / <inertion service>");
		}
		
		
	}

}
