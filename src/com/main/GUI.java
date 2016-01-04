package com.main;

//Import Library
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.xmlparser.XMLParser;

import java.awt.BorderLayout;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSlider;

//Class
public class GUI extends javax.swing.JFrame
{
	//Attributs 	
	public static final int MIN_ZOOM = 0;
	public static final int MAX_ZOOM = 21;
	private static int zoomValue = 4;
	private static String xmlSource = "src\\Data\\PrixCarburants_quotidien_20151210.xml";
	private JFrame frame;
	private JTextField textF_Lat;
	private JTextField text_Long;
	static String workingDir = System.getProperty("user.dir");
	
	/** Launch the application.*/
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GUI window = new GUI();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	


	/** Create the application.*/
	public GUI()
	{
		initialize();
	}

	/** Initialize the contents of the frame.*/
	private void initialize()
	{		
		   final Browser browser = new Browser();
	       BrowserView browserView = new BrowserView(browser);
	       
	       // TOOLBAR EN BAS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	       //JButtons du ruban Jpanel toolbar en bas
	       
	       JButton zoomInButton = new JButton("Zoom In");
	       zoomInButton.setBackground(new Color(39,39,39));
	       zoomInButton.setForeground(new Color(138,202,206));
	       zoomInButton.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	               if (zoomValue < MAX_ZOOM) {
	                   browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
	               }
	           }
	       });

	       JButton zoomOutButton = new JButton("Zoom Out");
	       zoomOutButton.setBackground(new Color(39,39,39));
	       zoomOutButton.setForeground(new Color(138,202,206));
	       zoomOutButton.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	               if (zoomValue > MIN_ZOOM) {
	                   browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
	               }
	           }
	       });

	       //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	       //Creation d'un POI (point of interest)
	       JButton setMarkerButton = new JButton("Generate Stations");
	       setMarkerButton.setBackground(new Color(39,39,39));
	       setMarkerButton.setForeground(new Color(138,202,206));
	       setMarkerButton.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) 
	           {	        	   
	        	try
				{     		   
	        		//Generation des Markers des stations
	        		XMLParser.GenerateStationFromCSV(browser);
				}
				catch (Exception ex)
				{
					System.out.println("Execption " + ex.getMessage());
				}
	          }
	       });

	       //toolBar
	       JPanel toolBar_bas = new JPanel();
	       toolBar_bas.setForeground(Color.BLACK);
	       toolBar_bas.setBackground(Color.BLACK);
	       toolBar_bas.add(zoomInButton);
	       toolBar_bas.add(zoomOutButton);
	       toolBar_bas.add(setMarkerButton);
	       
	       // JFRAME /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	       JFrame frame = new JFrame("map.html");
	       frame.getContentPane().setBackground(Color.BLACK);
	       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	       frame.getContentPane().add(toolBar_bas, BorderLayout.SOUTH);
	       frame.getContentPane().add(browserView, BorderLayout.CENTER);
	       
	       // JPANEL NAVIGATOR /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	       JPanel navigationBar = new JPanel();
	       navigationBar.setSize(new Dimension(100, 400));
	       navigationBar.setBackground(Color.BLACK);
	       navigationBar.setForeground(new Color(138,202,206));
	       navigationBar.setBorder(UIManager.getBorder("Button.border"));
	       frame.getContentPane().add(navigationBar, BorderLayout.WEST);
	       
	       GridBagLayout gbl_navigationBar = new GridBagLayout();
	       gbl_navigationBar.columnWidths = new int[]{234, 0};
	       gbl_navigationBar.rowHeights = new int[]{566, 0, 0};
	       gbl_navigationBar.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	       gbl_navigationBar.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
	       navigationBar.setLayout(gbl_navigationBar);
	       
	       JPanel NavBar_ZoneText = new JPanel();	       
	       NavBar_ZoneText.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(64, 224, 208), new Color(64, 224, 208), new Color(64, 224, 208), new Color(64, 224, 208)));
	       NavBar_ZoneText.setBackground(Color.DARK_GRAY);
	       GridBagConstraints gbc_NavBar_ZoneText = new GridBagConstraints();
	       gbc_NavBar_ZoneText.insets = new Insets(0, 0, 5, 0);
	       gbc_NavBar_ZoneText.fill = GridBagConstraints.BOTH;
	       gbc_NavBar_ZoneText.gridx = 0;
	       gbc_NavBar_ZoneText.gridy = 0;
	       navigationBar.add(NavBar_ZoneText, gbc_NavBar_ZoneText);
	       GridBagLayout gbl_NavBar_ZoneText = new GridBagLayout();
	       gbl_NavBar_ZoneText.columnWidths = new int[]{150, 0};
	       gbl_NavBar_ZoneText.rowHeights = new int[]{43, 37, 13, 42, 15, 24, 39, 0, 41, 14, 43, 25, 30, 16, 41, 39, 0};
	       gbl_NavBar_ZoneText.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	       gbl_NavBar_ZoneText.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	       NavBar_ZoneText.setLayout(gbl_NavBar_ZoneText);
	       
	       JLabel lblCarburant = new JLabel("Carburant");
	       lblCarburant.setFont(new Font("Tahoma", Font.BOLD, 20));
	       lblCarburant.setForeground(new Color(64, 224, 208));
	       GridBagConstraints gbc_lblCarburant = new GridBagConstraints();
	       gbc_lblCarburant.fill = GridBagConstraints.VERTICAL;
	       gbc_lblCarburant.insets = new Insets(0, 0, 5, 0);
	       gbc_lblCarburant.gridx = 0;
	       gbc_lblCarburant.gridy = 0;
	       NavBar_ZoneText.add(lblCarburant, gbc_lblCarburant);
	       
	       JComboBox<String> comboBox = new JComboBox<String>();
	       GridBagConstraints gbc_comboBox = new GridBagConstraints();
	       gbc_comboBox.fill = GridBagConstraints.BOTH;
	       gbc_comboBox.insets = new Insets(0, 0, 5, 0);
	       gbc_comboBox.gridx = 0;
	       gbc_comboBox.gridy = 1;
	       NavBar_ZoneText.add(comboBox, gbc_comboBox);
	       
	       JLabel lblPointDeDepart = new JLabel("Point de depart");
	       lblPointDeDepart.setForeground(new Color(64, 224, 208));
	       lblPointDeDepart.setFont(new Font("Tahoma", Font.BOLD, 20));
	       GridBagConstraints gbc_lblPointDeDepart = new GridBagConstraints();
	       gbc_lblPointDeDepart.fill = GridBagConstraints.VERTICAL;
	       gbc_lblPointDeDepart.insets = new Insets(0, 0, 5, 0);
	       gbc_lblPointDeDepart.gridx = 0;
	       gbc_lblPointDeDepart.gridy = 3;
	       NavBar_ZoneText.add(lblPointDeDepart, gbc_lblPointDeDepart);
	       
	       JLabel lblLatitude = new JLabel("Latitude :");
	       lblLatitude.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
	       GridBagConstraints gbc_lblLatitude = new GridBagConstraints();
	       gbc_lblLatitude.fill = GridBagConstraints.VERTICAL;
	       gbc_lblLatitude.insets = new Insets(0, 0, 5, 0);
	       gbc_lblLatitude.gridx = 0;
	       gbc_lblLatitude.gridy = 5;
	       NavBar_ZoneText.add(lblLatitude, gbc_lblLatitude);
	       lblLatitude.setForeground(new Color(64, 224, 208));
	       
	       textF_Lat = new JTextField();
	       textF_Lat.setSelectedTextColor(Color.DARK_GRAY);
	       textF_Lat.setForeground(new Color(64, 224, 208));
	       GridBagConstraints gbc_textF_Lat = new GridBagConstraints();
	       gbc_textF_Lat.fill = GridBagConstraints.BOTH;
	       gbc_textF_Lat.insets = new Insets(0, 0, 5, 0);
	       gbc_textF_Lat.gridx = 0;
	       gbc_textF_Lat.gridy = 6;
	       NavBar_ZoneText.add(textF_Lat, gbc_textF_Lat);
	       textF_Lat.setText("43.610769");
	       textF_Lat.setColumns(10);
	       
	       JLabel lblLongitude = new JLabel("Longitude :");
	       lblLongitude.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
	       GridBagConstraints gbc_lblLongitude = new GridBagConstraints();
	       gbc_lblLongitude.fill = GridBagConstraints.VERTICAL;
	       gbc_lblLongitude.insets = new Insets(0, 0, 5, 0);
	       gbc_lblLongitude.gridx = 0;
	       gbc_lblLongitude.gridy = 7;
	       NavBar_ZoneText.add(lblLongitude, gbc_lblLongitude);
	       lblLongitude.setForeground(new Color(64, 224, 208));
	       
	       text_Long = new JTextField();
	       text_Long.setSelectedTextColor(Color.DARK_GRAY);
	       text_Long.setForeground(new Color(64, 224, 208));
	       GridBagConstraints gbc_text_Long = new GridBagConstraints();
	       gbc_text_Long.insets = new Insets(0, 0, 5, 0);
	       gbc_text_Long.ipady = 1;
	       gbc_text_Long.fill = GridBagConstraints.BOTH;
	       gbc_text_Long.gridx = 0;
	       gbc_text_Long.gridy = 8;
	       NavBar_ZoneText.add(text_Long, gbc_text_Long);
	       text_Long.setText("3.876622");
	       text_Long.setColumns(10);
	       
	       JLabel lblDistance = new JLabel("Distance de recherche");
	       lblDistance.setForeground(new Color(0, 206, 209));
	       lblDistance.setFont(new Font("Tahoma", Font.BOLD, 20));
	       GridBagConstraints gbc_lblDistance = new GridBagConstraints();
	       gbc_lblDistance.insets = new Insets(0, 0, 5, 0);
	       gbc_lblDistance.gridx = 0;
	       gbc_lblDistance.gridy = 10;
	       NavBar_ZoneText.add(lblDistance, gbc_lblDistance);
	       
	       JSlider slider = new JSlider();
	       slider.setBackground(Color.DARK_GRAY);
	       GridBagConstraints gbc_slider = new GridBagConstraints();
	       gbc_slider.fill = GridBagConstraints.BOTH;
	       gbc_slider.insets = new Insets(0, 0, 5, 0);
	       gbc_slider.gridx = 0;
	       gbc_slider.gridy = 11;
	       NavBar_ZoneText.add(slider, gbc_slider);
	       
	       JLabel lblKm = new JLabel("0 Km                                    30km");
	       lblKm.setForeground(new Color(64, 224, 208));
	       lblKm.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
	       GridBagConstraints gbc_lblKm = new GridBagConstraints();
	       gbc_lblKm.anchor = GridBagConstraints.NORTH;
	       gbc_lblKm.insets = new Insets(0, 0, 5, 0);
	       gbc_lblKm.gridx = 0;
	       gbc_lblKm.gridy = 12;
	       NavBar_ZoneText.add(lblKm, gbc_lblKm);
	       
	       JLabel lblEnseigne = new JLabel("Enseigne");
	       lblEnseigne.setForeground(new Color(0, 206, 209));
	       lblEnseigne.setFont(new Font("Tahoma", Font.BOLD, 20));
	       GridBagConstraints gbc_lblEnseigne = new GridBagConstraints();
	       gbc_lblEnseigne.insets = new Insets(0, 0, 5, 0);
	       gbc_lblEnseigne.gridx = 0;
	       gbc_lblEnseigne.gridy = 14;
	       NavBar_ZoneText.add(lblEnseigne, gbc_lblEnseigne);
	       
	       JComboBox comboBox_1 = new JComboBox();
	       GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
	       gbc_comboBox_1.fill = GridBagConstraints.BOTH;
	       gbc_comboBox_1.gridx = 0;
	       gbc_comboBox_1.gridy = 15;
	       NavBar_ZoneText.add(comboBox_1, gbc_comboBox_1);
	       comboBox.addItem("SP95");
	       comboBox.addItem("SP98");
	       comboBox.addItem("Diesel");
	       comboBox.addItem("Diesel +");	       
	       comboBox.addItem("GPL");
	       
	       // JPANEL EN HAUT /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	       JPanel panel_haut = new JPanel();
	       panel_haut.setBackground(new Color(39,39,39));
	     
	       frame.getContentPane().add(panel_haut, BorderLayout.NORTH);
	       GridBagLayout gbl_panel_haut = new GridBagLayout();
	       gbl_panel_haut.columnWidths = new int[]{542, 937, 0};
	       gbl_panel_haut.rowHeights = new int[]{165, 0};
	       gbl_panel_haut.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
	       gbl_panel_haut.rowWeights = new double[]{1.0, Double.MIN_VALUE};
	       panel_haut.setLayout(gbl_panel_haut);
	       
	       JPanel panel = new JPanel();
	       panel.setForeground(Color.WHITE);
	       panel.setBackground(Color.DARK_GRAY);
	       GridBagConstraints gbc_panel = new GridBagConstraints();
	       gbc_panel.insets = new Insets(0, 0, 0, 5);
	       gbc_panel.fill = GridBagConstraints.BOTH;
	       gbc_panel.gridx = 0;
	       gbc_panel.gridy = 0;
	       panel_haut.add(panel, gbc_panel);
	       
	       JLabel lblNewLabel = new JLabel("");
	       lblNewLabel.setIcon(new ImageIcon(GUI.class.getResource("/Images/Logo4.png")));
	       panel.add(lblNewLabel);
	       
	       JPanel panel_1 = new JPanel();
	       panel_1.setBackground(Color.DARK_GRAY);
	       GridBagConstraints gbc_panel_1 = new GridBagConstraints();
	       gbc_panel_1.fill = GridBagConstraints.BOTH;
	       gbc_panel_1.gridx = 1;
	       gbc_panel_1.gridy = 0;
	       panel_haut.add(panel_1, gbc_panel_1);
	       GridBagLayout gbl_panel_1 = new GridBagLayout();
	       gbl_panel_1.columnWidths = new int[]{0, 365, 353, 327, 0};
	       gbl_panel_1.rowHeights = new int[]{55, 78, 26, 0};
	       gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	       gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
	       panel_1.setLayout(gbl_panel_1);
	       
	       JButton btnListePompe = new JButton(new ImageIcon(GUI.class.getResource("/Images/bouton_Liste_Off.png")));	      
	       GridBagConstraints gbc_btnListePompe = new GridBagConstraints();
	       gbc_btnListePompe.fill = GridBagConstraints.BOTH;
	       gbc_btnListePompe.insets = new Insets(0, 0, 5, 5);
	       gbc_btnListePompe.gridx = 1;
	       gbc_btnListePompe.gridy = 1;
	       panel_1.add(btnListePompe, gbc_btnListePompe);
	       btnListePompe.setFont(new Font("Tahoma", Font.PLAIN, 18));
	       btnListePompe.setPreferredSize(new Dimension(200, 40));
	       btnListePompe.setBackground(Color.DARK_GRAY);
	       btnListePompe.setForeground(Color.BLACK);
	       
	       JButton btnCarte = new JButton("Carte");
	       btnCarte.setIcon(new ImageIcon(GUI.class.getResource("/Images/bouton_Carte_Off.png")));
	       GridBagConstraints gbc_btnCarte = new GridBagConstraints();
	       gbc_btnCarte.fill = GridBagConstraints.BOTH;
	       gbc_btnCarte.insets = new Insets(0, 0, 5, 5);
	       gbc_btnCarte.gridx = 2;
	       gbc_btnCarte.gridy = 1;
	       panel_1.add(btnCarte, gbc_btnCarte);
	       btnCarte.setFont(new Font("Tahoma", Font.PLAIN, 18));
	       btnCarte.setPreferredSize(new Dimension(200, 40));
	       btnCarte.setBackground(new Color(39,39,39));
	       btnCarte.setForeground(new Color(138,202,206));
	       
	       JButton btnInfos = new JButton("Informations");
	       btnInfos.setIcon(new ImageIcon(GUI.class.getResource("/Images/bouton_Info_Off.png")));
	       GridBagConstraints gbc_btnInfos = new GridBagConstraints();
	       gbc_btnInfos.fill = GridBagConstraints.BOTH;
	       gbc_btnInfos.insets = new Insets(0, 0, 5, 0);
	       gbc_btnInfos.gridx = 3;
	       gbc_btnInfos.gridy = 1;
	       panel_1.add(btnInfos, gbc_btnInfos);
	       btnInfos.setFont(new Font("Tahoma", Font.PLAIN, 18));
	       btnInfos.setPreferredSize(new Dimension(200, 40));
	       btnInfos.setBackground(new Color(39,39,39));
	       btnInfos.setForeground(new Color(138,202,206));
	       frame.setSize(1361, 850);
	       frame.setLocationRelativeTo(null);
	       frame.setVisible(true);

	       //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	       //Import de la carte de la page HTML (du serveur?) sur le browser jxBrowser    
	       browser.loadURL(workingDir + "\\src\\Data\\map.html");
	}

}
