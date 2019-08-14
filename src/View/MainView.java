package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.Controller_mainView;
import Model.Model;
import Others.ImagePanel;


/**
 * This is the main view of the tool.
 * The frame will use the border layout within the title of the tool,
 * a panel in the middle where the image chosen by the user will be displayed on,
 * as well as a button for generating the mosaic out of the chosen image.
 * @author  Thielen M.
 */

public class MainView extends JFrame implements Observer
{
	private static final long serialVersionUID = -1111147139303829760L;
	
	// Model
	private Model 		model;
	
	// Controller
	private Controller_mainView controller;
	
	// Menu bar
	private JMenuBar 			menubar;
	private JMenu 				menu;
	private JMenuItem			menuItem;
	private JCheckBoxMenuItem	checkBoxMenuItemShowMosaicImage;
	private JCheckBoxMenuItem	checkBoxMenuItem;
	
	// START SIZE
	private final int		WIDTH  = 800;
	private final int 		HEIGHT = 600;
	private final Dimension	DIMENSION;
	
	// Name of the tool with version number
//	private JLabel		 	titleLabel;
	
	// The preview of the image
	private ImagePanel imagePanel;
	
	// Generate Mosaic Button
	private JPanel 	pageEndPanel;
	private JLabel 	spacerLabel1;
	private JButton	generate;
	private JLabel 	spacerLabel2;
	
	private boolean showMosaicImage = false;
		
	


	/**
	 * Constructor of the class
	 * @param title -
	 * The title of the mainView
	 * @param model -
	 * A reference to the model
	 * @param infoView - 
	 * A reference to the infoView
	 * @param settingView -
	 * A reference to the settingView
	 */
	public MainView(String title, Model model, InfoView infoView, SettingView settingView)
	{
		super(title);
		
		this.model    	 = model;
	
//		this.getContentPane().setBackground(Color.WHITE);
		
		// Set a minimum frame size
		DIMENSION = new Dimension(WIDTH, HEIGHT);
		this.setMinimumSize(DIMENSION);
		this.setSize(DIMENSION);
		
		// Controller
		controller = new Controller_mainView(model, this, infoView, settingView);
		
		// Initialization
		buildMainView();
			
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}
	
	
	/**
	 * Builds the standard view of the tool.
	 * This includes the frame using a border layout with a title in the top,
	 * an image panel in the mid as well as a button to generate the mosaic in the bottom.
	 * Also builds the menu bar.
	 */
	private void buildMainView()
	{
		// Creating the menu bar
		buildMenubar();
		
		// Title of the tool
//		titleLabel = new JLabel("Double click the image for full screen");
//		titleLabel.setFont(new Font("Serif", Font.BOLD, 12));
//		titleLabel.setHorizontalAlignment(JLabel.CENTER);
//		titleLabel.setVerticalAlignment(JLabel.CENTER);
		
		// Image panel
		imagePanel = new ImagePanel(model.getSourceImage(), model.getTileWidthSourceCurrentUsed(), model.getTileHeightSourceCurrentUsed());
		imagePanel.setBackground(Color.WHITE);	
		imagePanel.addMouseListener(controller);
		
		// Generate button
		pageEndPanel = new JPanel();
		pageEndPanel.setBackground(Color.WHITE);
		
		spacerLabel1 = new JLabel("");
		generate = new JButton("Generate Mosaic!");
		generate.addActionListener(controller);
		spacerLabel2 = new JLabel("");
		pageEndPanel.add(spacerLabel1);
		pageEndPanel.add(generate);
		pageEndPanel.add(spacerLabel2);
		
//		this.add(titleLabel, BorderLayout.PAGE_START);
		this.add(imagePanel, BorderLayout.CENTER);
		this.add(pageEndPanel, BorderLayout.PAGE_END);
	}
	
	
	/**
	 * Builds the menu bar of the tool.
	 */
	private void buildMenubar()
	{
		menubar = new JMenuBar();
		
		/* FILE MENU */
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menubar.add(menu);

		menuItem = new JMenuItem("Open Image", new ImageIcon("Icons/open.png"));
		menuItem.setToolTipText("Opens the source image, which will be used to generate the mosaic of.");
		menuItem.addActionListener(controller);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Open Result Folder", new ImageIcon("Icons/folder.png"));
		menuItem.setToolTipText("Opens the folder where the mosaic images are saved in.");
		menuItem.addActionListener(controller);
		menu.add(menuItem);
				
		menuItem = new JMenuItem("Save Result", new ImageIcon("Icons/save.png"));
		menuItem.setToolTipText("Save the result mosaic image to the result folder.");
		menuItem.addActionListener(controller);
		menu.add(menuItem);
				
		menuItem = new JMenuItem("Exit", new ImageIcon("Icons/exit.png"));
		menuItem.setToolTipText("Closes the tool.");
		menuItem.addActionListener(controller);
		menu.add(menuItem);
				
		
		/* SETTINGS */
		
		menu = new JMenu("Settings");
		menubar.add(menu);
		
		checkBoxMenuItemShowMosaicImage = new JCheckBoxMenuItem("Show Mosaic Image", new ImageIcon("Icons/mosaic.png"));
		checkBoxMenuItemShowMosaicImage.setToolTipText("Show the mosaic image insetad of the source image.");
		checkBoxMenuItemShowMosaicImage.addItemListener(controller);
		checkBoxMenuItemShowMosaicImage.setName("showMosaicImage");
		menu.add(checkBoxMenuItemShowMosaicImage);
		
		checkBoxMenuItem = new JCheckBoxMenuItem("Enable / Disable Grid", new ImageIcon("Icons/grid.png"));
		checkBoxMenuItem.setToolTipText("Enables the grid which shows the tile size.");
		checkBoxMenuItem.addItemListener(controller);
		checkBoxMenuItem.setName("enableGridCheckbox");
		menu.add(checkBoxMenuItem);
		
		menu.addSeparator();
	
		menuItem = new JMenuItem("Show Info Frame", new ImageIcon("Icons/information.png"));
		menuItem.setToolTipText("Shows the frame with informations inside.");
		menuItem.addActionListener(controller);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Load own Tiles", new ImageIcon("Icons/home.png"));
		menuItem.setToolTipText("Loads the tiles of the Tile folder.");
		menuItem.addActionListener(controller);
		menuItem.setName("loadOwnTiles");
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Tile Settings", new ImageIcon("Icons/settings.png"));
		menuItem.setToolTipText("Change settings of own tiles and for those, which can be generated. Not the example tiles!");
		menuItem.addActionListener(controller);
		menu.add(menuItem);
		
		this.setJMenuBar(menubar);
	}
	
	
	/**
	 * Opens a JFileChooser dialog, where the user will be able of choose one image from (jpg, png).
	 * The chosen image will be added to the image panel in the center of the tool.
	 */
	public void loadImage()
	{
		JFileChooser chooser = new JFileChooser();
		
		//filter for images
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Images (jpg, png)", "jpg", "png");
	    chooser.setFileFilter(filter);
	    
	    int returnVal = chooser.showOpenDialog(this);
	    
	    if(returnVal == JFileChooser.APPROVE_OPTION) 
	    {
		    model.changeTargetImage(chooser.getSelectedFile().getAbsolutePath());
	    }
	}
	
	
	/**
	 * This function opens a dialog to save the mosaic image.
	 * If the name you want to use exists as file already,
	 * asks user to overwrite it.
	 */
	public void saveImage()
	{
		JFileChooser chooser = new JFileChooser("Results");
		chooser.setSelectedFile(new File("Result/mosaic.png"));
		
		// Filter for images
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Images (jpg, png)", "jpg", "png");
	    chooser.setFileFilter(filter);
	    
	    // Open the save dialog
	    int returnVal = chooser.showSaveDialog(this);
	    
	    if(returnVal == JFileChooser.APPROVE_OPTION) 
	    {
	    	String path = chooser.getSelectedFile().getAbsolutePath();
	    	
	    	// Check if file exists
	    	File f = new File(path);
	    	
	    	if(f.exists())
	    	{
	    		JOptionPane.showConfirmDialog(this, "File " + f.getName() + "already exists. Overwrite?");
	    		
	    		if(returnVal == JOptionPane.OK_OPTION)
	    		{
	    		    model.saveImage(path);
	    		}
	    		else
	    		{
	    			return;
	    		}
	    	}
	    	else
	    	{
    		    model.saveImage(path);
	    	}
	    }
	}
	
	
	/**
	 * Updates the image panel.
	 */
	public void updateImagePanel()
	{
		// Either draw the source or the mosaic image
		if(showMosaicImage)
		{
			if(model.getMosaicImage() == null)
			{
				imagePanel.setImage(model.getSourceImage());
			}
			else
			{
				imagePanel.setImage(model.getMosaicImage());
			}
		}
		else
		{
			imagePanel.setImage(model.getSourceImage());
		}
		
		// Update tile width and height
		imagePanel.setTileWidth(model.getTileWidthSourceCurrentUsed());
		imagePanel.setTileHeight(model.getTileHeightSourceCurrentUsed());
		
		this.repaint();
	}
		
	
	
	
	/* OBSERVER */
	
	@Override
	public void update(Observable o, Object arg)
	{
		updateImagePanel();
		this.repaint();
	}
	
	
	/* GETTER */
	
	public ImagePanel getImagePanel()
	{
		return imagePanel;
	}
	
	public boolean isMosaicImageShown() 
	{
		return showMosaicImage;
	}
	
	public JButton getGenerateButton()
	{
		return generate;
	}
	
	public JCheckBoxMenuItem getCheckBoxMenuItemShowMosaicImage()
	{
		return checkBoxMenuItemShowMosaicImage;
	}
	
	
	
	/* SETTER */
	
	public void setMosaicImageShown(boolean isMosaicImageShown)
	{
		showMosaicImage = isMosaicImageShown;
	}
}
