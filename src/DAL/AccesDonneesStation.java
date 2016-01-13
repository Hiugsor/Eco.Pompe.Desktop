package DAL;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.xml.parsers.*;
import org.jdom2.*;
import org.jdom2.input.DOMBuilder;

import Boite_a_outil.*;

public class AccesDonneesStation {

	private AccesDonneesStation()
	{}

	/**
	 * Insert all service in services table
	 * @param noms
	 * @throws SQLException 
	 */
	public static void insertStations(List<String[]> pdvs) throws SQLException{

		int idPdv ;
		String nom = "";
		String adresse ="";
		String ville ="";
		String cp ="";
		String type_route;
		String[] ouvert= new String[3];
		String[] fermer= new String[3];
		Time hor_Ouv = null;
		hor_Ouv.valueOf("00:00:00");
		Time hor_Ferm = null;		
		hor_Ferm.valueOf("00:00:00");
		double latitudePdv;
		double longitudePdv;


		String path = "src\\DAL\\StationsNOM.csv";
		List <String> nomStations = LectureFichier.readTXT(path);

		Random rand = new Random();
		int nbrand =2;



		String requete ="INSERT INTO stations "
				+ "(id_Station,Nom,Adresse,Ville,CP,Type_route,Horaire_ouverture,Horaire_fermeture,latitude,longitude) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		//int[] count= new int[0];


		Connection connection = ConnexionManager.GetInstance().GetConnection();
		for (String[] pdv : pdvs) {


			if (!pdv[0].equals("id")) {
				idPdv = Integer.parseInt(pdv[0]);
				//integration des noms généré aléatoirement
				nbrand = rand.nextInt(nomStations.size()-2)+1;
				nom  = nomStations.get(nbrand);
				adresse = pdv[1];
				ville = pdv[2];
				cp = pdv[3];
				type_route = pdv[4];
				ouvert = pdv[5].split(":");
				fermer = pdv[6].split(":");
				//fermer = pdv[6].replace(':', '-');
				hor_Ouv= new Time(Integer.parseInt(ouvert[0]),Integer.parseInt(ouvert[1]),Integer.parseInt(ouvert[2])); 
				hor_Ferm=new Time(Integer.parseInt(fermer[0]),Integer.parseInt(fermer[1]),Integer.parseInt(fermer[2]));
				latitudePdv = Double.parseDouble(pdv[7]);
				longitudePdv = Double.parseDouble(pdv[8]);


				try {
					PreparedStatement requeteSql = connection.prepareStatement(requete);			
					requeteSql.setInt(1, idPdv);
					requeteSql.setString(2, nom);
					requeteSql.setString(3, adresse);
					requeteSql.setString(4, ville);
					requeteSql.setString(5, cp);
					requeteSql.setString(6, type_route);
					requeteSql.setTime(7, hor_Ouv);
					requeteSql.setTime(8, hor_Ferm);
					requeteSql.setDouble(9, latitudePdv);
					requeteSql.setDouble(10, longitudePdv);
					requeteSql.addBatch();
					int[] count =requeteSql.executeBatch();
					//requeteSql.executeBatch();
					//System.out.println(count.length);
				} catch (SQLException sqle) {
					// TODO Auto-generated catch block

					ConnexionManager.GetInstance().GetConnection().rollback();

					sqle.printStackTrace();

				}

			}
		}
	}

	public static void insertInfoStations(List<String[]> stations) throws SQLException{

		int idStation;
		String proposer = "";
		String vendre = "";
		String jNonTravailler = "";




		Connection connection = ConnexionManager.GetInstance().GetConnection();
		for (String[] station : stations) {


			if (!station[0].equals("id")) {
				idStation = Integer.parseInt(station[0]);

				proposer = station[9];
				vendre = station[11];
				jNonTravailler = station[10];


				if(!proposer.isEmpty()){
				System.out.println(idStation);
				AccesDonneesProposer.insertProposer(idStation, traitementString.recupererDonnées(proposer, "/"));
				//System.out.println("poposer OK !!");
				}
				if(!jNonTravailler.isEmpty()){
				//System.out.println(idStation);
				//System.out.println("Travailler !!");
				AccesDonneesNonTravailler.insertNonTravailler(idStation, traitementString.recupererDonnées(jNonTravailler, "/"));
				//System.out.println("jNontravailler OK !!");
				}
				if(!vendre.isEmpty()){
				//System.out.println(idStation);
				//System.out.println(vendre);
				AccesDonneesVendre.insertVendre(idStation, vendre);
				//System.out.println("vendre OK !!");
				}
			}
		}
	}

}
