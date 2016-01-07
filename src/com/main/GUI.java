package com.main;

import com.API.googlemaps.Circle;
import com.API.googlemaps.JavaScript;
import com.parser.XMLParser;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.UIManager.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

//Class
public class GUI extends JFrame {
	// Attributs
	public static final int MIN_ZOOM = 0;
	public static final int MAX_ZOOM = 21;
	private static int zoomValue = 4;
	//public static List<String[]> ListeStations;
	
	
	//private static String xmlSource = "src\\Data\\XML\\PrixCarburants_quotidien_20151210.xml";
	static String workingDir = System.getProperty("user.dir");

	/** Launch the application. */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
					new GUI();
					//GUI window = new GUI();
					//window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/** Create the application. */
	public GUI() {
		initialize();
	}

	/** Initialize the contents of the frame. */
	private void initialize() {
		final Browser browser = new Browser();
		BrowserView browserView = new BrowserView(browser);
		List<String[]> ListeStations = new ArrayList<String[]>(); //Liste des stations utilisée par le btnListeStation		
		
		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// JFRAME
		JFrame frame = new JFrame("EcoPompe");
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// JPANEL NAVIGATOR (Panneau de gauche)
		JPanel navigationBar = new JPanel();
		navigationBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		navigationBar.setSize(new Dimension(100, 400));
		navigationBar.setBackground(new Color(39, 39, 39));
		navigationBar.setForeground(new Color(138, 202, 206));
		navigationBar.setBorder(new LineBorder(new Color(138, 202, 206)));
		frame.getContentPane().add(navigationBar, BorderLayout.WEST);

		GridBagLayout gbl_navigationBar = new GridBagLayout();
		gbl_navigationBar.columnWidths = new int[] { 221, 0 };
		gbl_navigationBar.rowHeights = new int[] { 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_navigationBar.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_navigationBar.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0, Double.MIN_VALUE };
		navigationBar.setLayout(gbl_navigationBar);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("SP95");
		comboBox.addItem("SP98");
		comboBox.addItem("Diesel");
		comboBox.addItem("Diesel +");
		comboBox.addItem("GPL");

		JButton btnCarburant = new JButton("Carburant");
		btnCarburant.setBackground(new Color(39, 39, 39));
		btnCarburant.setForeground(new Color(138, 202, 206));
		GridBagConstraints gbc_btnCarburant = new GridBagConstraints();
		gbc_btnCarburant.fill = GridBagConstraints.BOTH;
		gbc_btnCarburant.insets = new Insets(0, 0, 5, 0);
		gbc_btnCarburant.gridx = 0;
		gbc_btnCarburant.gridy = 1;
		navigationBar.add(btnCarburant, gbc_btnCarburant);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 2;
		navigationBar.add(comboBox, gbc_comboBox);

		JTextField txtLAT = new JTextField();
		txtLAT.setText("43.610769");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 5;
		navigationBar.add(txtLAT, gbc_textField);
		txtLAT.setColumns(10);

		JTextField txtLONG = new JTextField();
		txtLONG.setText("3.876622");
		GridBagConstraints gbc_txtLONG = new GridBagConstraints();
		gbc_txtLONG.insets = new Insets(0, 0, 5, 0);
		gbc_txtLONG.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLONG.gridx = 0;
		gbc_txtLONG.gridy = 6;
		navigationBar.add(txtLONG, gbc_txtLONG);
		txtLONG.setColumns(10);

		// //////////////////////////////////////////////////////////
		// LABEL RADIUS
		JLabel lblRadius = new JLabel("Rayon : 30 km");
		lblRadius.setForeground(new Color(138, 202, 206));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 10;
		navigationBar.add(lblRadius, gbc_lblNewLabel);
		
		// //////////////////////////////////////////////////////////
		// SLIDER
		final int MIN = 0;
		final int MAX = 100;
		final int INIT = 30; // initial frames per second
		JSlider slider = new JSlider(SwingConstants.HORIZONTAL, MIN, MAX, INIT);
		slider.setPaintLabels(true);
		slider.setFont(new Font("Tahoma", Font.BOLD, 12));
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		slider.setForeground(new Color(138, 202, 206));
		slider.setBackground(new Color(39, 39, 39));
		// Event Change Value on SLIDER
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JavaScript.deleteStations(browser); // suppression des points Stations
				lblRadius.setText("Rayon : " + Integer.toString(slider.getValue()) + " km");
				new Circle(browser, txtLAT, txtLONG, slider);
				// Generation des Markers des stations
				XMLParser.GenerateStationFromCSV(browser, txtLAT, txtLONG, slider, ListeStations);
			}
		});
		// Event Mouse Wheel on SLIDER
		slider.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent evt) {
				if (evt.getWheelRotation() < 0) { // mouse wheel was rotated up/away from the user
					int iNewValue = slider.getValue() - slider.getMinorTickSpacing();
					if (iNewValue >= slider.getMinimum()) {
						slider.setValue(iNewValue);
					} else
						slider.setValue(0);
				} else {
					int iNewValue = slider.getValue() + slider.getMinorTickSpacing();
					if (iNewValue <= slider.getMaximum()) {
						slider.setValue(iNewValue);
					} else
						slider.setValue(slider.getMaximum());
				}
			}
		});
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 9;
		navigationBar.add(slider, gbc_slider);

		// //////////////////////////////////////////////////////////
		// Btn Point de depart
		JButton btnPosDep = new JButton("Position Initiale");
		btnPosDep.setBackground(new Color(39, 39, 39));
		btnPosDep.setForeground(new Color(138, 202, 206));
		btnPosDep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new Circle(browser, txtLAT, txtLONG, slider);
				} catch (Exception ex) {
					System.out.println("Execption " + ex.getMessage());
				}
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 4;
		navigationBar.add(btnPosDep, gbc_btnNewButton_2);

		JButton btnRayon = new JButton("Zone de Recherche");
		btnRayon.setBackground(new Color(39, 39, 39));
		btnRayon.setForeground(new Color(138, 202, 206));
		btnRayon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnRayon = new GridBagConstraints();
		gbc_btnRayon.fill = GridBagConstraints.BOTH;
		gbc_btnRayon.insets = new Insets(0, 0, 5, 0);
		gbc_btnRayon.gridx = 0;
		gbc_btnRayon.gridy = 8;
		navigationBar.add(btnRayon, gbc_btnRayon);

		// //////////////////////////////////////////////////////////
		// BTN Creation du POI (point of interest)
		JButton btnGenerateStations = new JButton("Generate Stations");
		GridBagConstraints gbc_setMarkerButton = new GridBagConstraints();
		gbc_setMarkerButton.fill = GridBagConstraints.BOTH;
		gbc_setMarkerButton.insets = new Insets(0, 0, 5, 0);
		gbc_setMarkerButton.gridx = 0;
		gbc_setMarkerButton.gridy = 12;
		navigationBar.add(btnGenerateStations, gbc_setMarkerButton);
		btnGenerateStations.setBackground(new Color(39, 39, 39));
		btnGenerateStations.setForeground(new Color(138, 202, 206));
		btnGenerateStations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					JavaScript.deleteStations(browser);	// Suppression des anciennes stations eventuelles
					XMLParser.GenerateStationFromCSV(browser, txtLAT, txtLONG, slider, ListeStations); // Generation des Markers des stations
				} 
				catch (Exception ex) 
				{
					System.out.println("Execption " + ex.getMessage());
				}
			}
		});

		// //////////////////////////////////////////////////////////
		// BTN Suppression des stations de la carte
		JButton btnDeleteStations = new JButton("Delete Stations");
		GridBagConstraints gbc_btnDeleteStations = new GridBagConstraints();
		gbc_btnDeleteStations.fill = GridBagConstraints.BOTH;
		gbc_btnDeleteStations.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteStations.gridx = 0;
		gbc_btnDeleteStations.gridy = 13;
		navigationBar.add(btnDeleteStations, gbc_btnDeleteStations);
		btnDeleteStations.setForeground(new Color(138, 202, 206));
		btnDeleteStations.setBackground(new Color(39, 39, 39));
		btnDeleteStations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ListeStations.clear();
					JavaScript.deleteStations(browser); // suppression des points Stations
				} catch (Exception ex) {
					System.out.println("Execption " + ex.getMessage());
				}
			}
		});

		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// JPANEL PANNEAU HAUT (3 btn)		
		JPanel panel_haut = new JPanel();
		panel_haut.setBackground(new Color(39, 39, 39));

		frame.getContentPane().add(panel_haut, BorderLayout.NORTH);
		GridBagLayout gbl_panel_haut = new GridBagLayout();
		gbl_panel_haut.columnWidths = new int[] { 83, 937, 0 };
		gbl_panel_haut.rowHeights = new int[] { 165, 0 };
		gbl_panel_haut.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_haut.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_haut.setLayout(gbl_panel_haut);

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.BLACK);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panel_haut.add(panel, gbc_panel);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/Logo4.png")));
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(39, 39, 39));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		panel_haut.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 15, 365, 0, 365, 0, 365, 15, 0 };
		gbl_panel_1.rowHeights = new int[] { 55, 78, 26, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// CARDLAYOUT (init)		
		JPanel myCards = new JPanel(new CardLayout());
		
		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// FENETRE CARTE
		JPanel tabCarte = new JPanel();
		tabCarte.setBackground(Color.BLACK);
		tabCarte.setLayout(new BorderLayout(0, 0));
		tabCarte.add(browserView, BorderLayout.CENTER);		
		myCards.add("Carte", tabCarte);
		
		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// FENETRE LISTE DES STATIONS
		JPanel tabListe = new JPanel();
		tabListe.setBackground(Color.BLACK); //Couleur de test visuel - ï¿½ supprimer plus tard
		//tabListe.setBackground(new Color(39, 39, 39));		
		myCards.add("Liste_Stations", tabListe);
		GridBagLayout gbl_tabListe = new GridBagLayout();
		gbl_tabListe.columnWidths = new int[]{0, 945, 0, 0};
		gbl_tabListe.rowHeights = new int[]{0, 177, 0, 46, 0, 46, 0};
		gbl_tabListe.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_tabListe.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		tabListe.setLayout(gbl_tabListe);
		
		
		//Frame Station 
		JPanel blocBox = new JPanel();
		blocBox.setBackground(Color.BLACK);
		GridBagConstraints gbc_blocBox = new GridBagConstraints();
		gbc_blocBox.insets = new Insets(0, 0, 5, 5);
		gbc_blocBox.fill = GridBagConstraints.BOTH;
		gbc_blocBox.gridx = 1;
		gbc_blocBox.gridy = 1;
		tabListe.add(blocBox, gbc_blocBox);
		blocBox.setLayout(new BorderLayout(0, 0));
		
		JLabel top = new JLabel("");
		top.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/FrameBoxListe/NorthFrameBox.png")));
		blocBox.add(top, BorderLayout.NORTH);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/FrameBoxListe/SouthFrameBox.png")));
		blocBox.add(label, BorderLayout.SOUTH);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/FrameBoxListe/WestFrameBox.png")));
		blocBox.add(label_1, BorderLayout.WEST);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/FrameBoxListe/EastFrameBox.png")));
		blocBox.add(lblNewLabel_1, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.BLACK);
		blocBox.add(panel_3, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{79, 131, 485, 244, 0};
		gbl_panel_3.rowHeights = new int[]{120, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel distance = new JLabel("< 500m");
		distance.setForeground(Color.GRAY);
		distance.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_distance = new GridBagConstraints();
		gbc_distance.insets = new Insets(0, 0, 0, 5);
		gbc_distance.gridx = 0;
		gbc_distance.gridy = 0;
		panel_3.add(distance, gbc_distance);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(GUI.class.getResource("/Data/HTML/img/Agip_logomini.png")));
		GridBagConstraints gbc_logo = new GridBagConstraints();
		gbc_logo.insets = new Insets(0, 0, 0, 5);
		gbc_logo.gridx = 1;
		gbc_logo.gridy = 0;
		panel_3.add(logo, gbc_logo);
		
		JLabel info = new JLabel("Station-Service AGIP \r\n544 Rue Paul Rimbaud \r\n04.67.63.08.18 ");
		info.setForeground(Color.GRAY);
		info.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_info = new GridBagConstraints();
		gbc_info.insets = new Insets(0, 0, 0, 5);
		gbc_info.gridx = 2;
		gbc_info.gridy = 0;
		panel_3.add(info, gbc_info);
		
		JLabel prix = new JLabel("1.33 euros");
		prix.setForeground(Color.WHITE);
		prix.setFont(new Font("Tahoma", Font.PLAIN, 28));
		GridBagConstraints gbc_prix = new GridBagConstraints();
		gbc_prix.gridx = 3;
		gbc_prix.gridy = 0;
		panel_3.add(prix, gbc_prix);		
		// Fin Frame Station
				
		
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// FENETRE STATISTIQUES
		JPanel tabStat = new JPanel();
		tabStat.setBackground(Color.BLUE);  //Couleur de test visuel - ï¿½ supprimer plus tard
		//tabStat.setBackground(new Color(39, 39, 39));
		myCards.add("Statistiques", tabStat);
				
		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// CARDLAYOUT (config)
		CardLayout myCardLayout = (CardLayout)(myCards.getLayout());
		frame.getContentPane().add(myCards, BorderLayout.CENTER);
		

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// JBUTTONS (Carte, Liste, Stat)
		JButton btnCarte = new JButton("Carte");
		btnCarte.setBackground(new Color(39, 39, 39));
		btnCarte.setForeground(new Color(138, 202, 206));
		btnCarte.setFont(new Font("Tahoma", Font.PLAIN, 36));
		btnCarte.setPreferredSize(new Dimension(200, 40));
		btnCarte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myCardLayout.show(myCards, "Carte");
			}
		});
		GridBagConstraints gbc_btnCarte = new GridBagConstraints();
		gbc_btnCarte.fill = GridBagConstraints.BOTH;
		gbc_btnCarte.insets = new Insets(0, 0, 5, 5);
		gbc_btnCarte.gridx = 1;
		gbc_btnCarte.gridy = 1;
		panel_1.add(btnCarte, gbc_btnCarte);

		JButton btnListeStations = new JButton("Liste des Stations");
		btnListeStations.setBackground(new Color(39, 39, 39));
		btnListeStations.setForeground(new Color(138, 202, 206));
		btnListeStations.setFont(new Font("Tahoma", Font.PLAIN, 36));
		btnListeStations.setPreferredSize(new Dimension(200, 40));
		btnListeStations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Affichage du panneau Liste des Stations
				myCardLayout.show(myCards, "Liste_Stations");
				// Recuperation des données du tableau listeStations issues de l'import du fichier xml-csv ou db (pour l'instant issue de la fonction XMLParser )
				if (ListeStations.size() > 0) {
					System.out.println(ListeStations.size());
					int index = 0;
					for (String[] station : ListeStations) {
						System.out.println("-> N° "+ ++index + " | Station ID : " + station[0] + " | Adresse : " + station[1] + " | Code Postal : "+station[2] + " | Ville : " + station[3] + " | Lat : " + station[4] + " | Long : " + station[5]);
					}
					System.out.println(">>> End of process [OK]");
				}
				else 
				{
					System.out.println(">>> La liste des staions est vide !!!");
				}
			}
		});
		GridBagConstraints gbc_btnListeStations = new GridBagConstraints();
		gbc_btnListeStations.fill = GridBagConstraints.BOTH;
		gbc_btnListeStations.insets = new Insets(0, 0, 5, 5);
		gbc_btnListeStations.gridx = 3;
		gbc_btnListeStations.gridy = 1;
		panel_1.add(btnListeStations, gbc_btnListeStations);

		JButton btnInfos = new JButton("Statistiques");
		btnInfos.setBackground(new Color(39, 39, 39));
		btnInfos.setForeground(new Color(138, 202, 206));
		btnInfos.setFont(new Font("Tahoma", Font.PLAIN, 36));
		btnInfos.setPreferredSize(new Dimension(200, 40));
		btnInfos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myCardLayout.show(myCards, "Statistiques");
			}
		});
		GridBagConstraints gbc_btnInfos = new GridBagConstraints();
		gbc_btnInfos.fill = GridBagConstraints.BOTH;
		gbc_btnInfos.insets = new Insets(0, 0, 5, 5);
		gbc_btnInfos.gridx = 5;
		gbc_btnInfos.gridy = 1;
		panel_1.add(btnInfos, gbc_btnInfos);
		
		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// JButtons du ruban Jpanel toolbar en bas
		JButton zoomInButton = new JButton("Zoom In");
		zoomInButton.setBackground(new Color(39, 39, 39));
		zoomInButton.setForeground(new Color(138, 202, 206));
		zoomInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (zoomValue < MAX_ZOOM) {
					browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
				}
			}
		});

		JButton zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.setBackground(new Color(39, 39, 39));
		zoomOutButton.setForeground(new Color(138, 202, 206));
		zoomOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (zoomValue > MIN_ZOOM) {
					browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
				}
			}
		});

		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// TOOLBAR EN BAS
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel toolBar_bas = new JPanel();
		toolBar_bas.setBackground(new Color(39, 39, 39));
		toolBar_bas.add(zoomInButton);
		toolBar_bas.add(zoomOutButton);
		frame.getContentPane().add(toolBar_bas, BorderLayout.SOUTH);

		
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		frame.setSize(1538, 900);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		
		// Import de la carte de la page HTML (du serveur?) sur le browser
		browser.loadURL(workingDir + "\\src\\Data\\HTML\\map.html");

	}

}
