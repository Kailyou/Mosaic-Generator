package View;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import Controller.Controller_tileSettings;
import Model.Model;

/**
 * A view class which shows the settings.
 * @author Kailyou
 */
public class SettingView extends JDialog implements Observer
{
	private static final long serialVersionUID = 8561191758088115650L;

	// Model
	private Model model;
	
	// Slider values
	private final int SLIDER_MIN  = 10;
	private final int SLIDER_MAX  = 100;
	
	// Slider
	private JSlider sliderTilesWidth;
	private JSlider sliderTilesHeight;
	private JSlider sliderMaxRGBDifference;
	
	private JLabel sliderTilesWidthValue;
	private JLabel sliderTileHeightValue;
	private JLabel sliderMaxRGBDifferenceValue;
	
	// Check box
	private JCheckBox checkbox_isSquare;
	private JCheckBox checkbox_useOwnTiles;
	
	private Controller_tileSettings controller;
	
	private Font labelFont;
	
	
	/**
	 * The constructor of the class.
	 * @param parentFrame -
	 * Reference to the parent frame (mainView)
	 * @param model -
	 * A reference to the model
	 */
	public SettingView(JFrame parentFrame, Model model)
	{
		super(parentFrame, "Tile Settings", true);
		
		this.setLayout(new GridLayout(0,1));
		
		// Model
		this.model = model;

		// Controller
		controller = new Controller_tileSettings(model, this);
		
		labelFont = new Font("Serif", Font.BOLD, 14);
		
		buildSettingView();
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}
	
	
	/**
	 * Builds the setting view.
	 */
	private void buildSettingView()
	{
		JPanel panelTileWidth = new JPanel();
		panelTileWidth.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// Tile width
		JLabel label = new JLabel("Tile Width                ");

		label.setFont(labelFont);
		
		panelTileWidth.add(label);
		
		// Slider tile width
		sliderTilesWidth = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, model.getTileWidthSourceCurrentUsed());
		sliderTilesWidth.setName("tileWidthSlider");
		sliderTilesWidth.addChangeListener(controller);
		sliderTilesWidth.setMajorTickSpacing(15);
		sliderTilesWidth.setMinorTickSpacing(5);
		sliderTilesWidth.setPaintTicks(true);
		sliderTilesWidth.setPaintLabels(true);
		sliderTilesWidth.setEnabled(false); // Disable slider as default because of the use of example tiles
		
		panelTileWidth.add(sliderTilesWidth);
		
		sliderTilesWidthValue = new JLabel("  " + String.valueOf(sliderTilesWidth.getValue()));
		sliderTilesWidthValue.setFont(labelFont);
		panelTileWidth.add(sliderTilesWidthValue);
		
		this.getContentPane().add(panelTileWidth);
		
		// Tile Height
		JPanel panelTileHeight = new JPanel();
		panelTileHeight.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		label = new JLabel("Tile Height               ");
		label.setFont(labelFont);
		panelTileHeight.add(label);
		
		// Slider 
		sliderTilesHeight = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, model.getTileHeightSourceCurrentUsed());
		sliderTilesHeight.setName("tileHeightSlider");
		sliderTilesHeight.addChangeListener(controller);
		sliderTilesHeight.setMajorTickSpacing(15);
		sliderTilesHeight.setMinorTickSpacing(5);
		sliderTilesHeight.setPaintTicks(true);
		sliderTilesHeight.setPaintLabels(true);
		sliderTilesHeight.setEnabled(false); //Disable slider as default because of the use of example tiles
		
		panelTileHeight.add(sliderTilesHeight);
		
		sliderTileHeightValue = new JLabel("  " + String.valueOf(sliderTilesHeight.getValue()));
		sliderTileHeightValue.setFont(labelFont);
		panelTileHeight.add(sliderTileHeightValue);
		
		this.getContentPane().add(panelTileHeight);
		
		// Max RGB Difference
		JPanel panelMaxRGBDifference = new JPanel();
		panelMaxRGBDifference.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		label = new JLabel("Max RGB Difference");
		label.setFont(labelFont);
		
		panelMaxRGBDifference.add(label);
		
		// Slider
		sliderMaxRGBDifference = new JSlider(JSlider.HORIZONTAL, 50, 150, model.getMaxRGBDifference());
		sliderMaxRGBDifference.setName("maxRGBDifferenceSlider");
		sliderMaxRGBDifference.addChangeListener(controller);
		sliderMaxRGBDifference.setMajorTickSpacing(25);
		sliderMaxRGBDifference.setMinorTickSpacing(5);
		sliderMaxRGBDifference.setPaintTicks(true);
		sliderMaxRGBDifference.setPaintLabels(true);
		
		panelMaxRGBDifference.add(sliderMaxRGBDifference);
		
		sliderMaxRGBDifferenceValue = new JLabel("  " + String.valueOf(model.getMaxRGBDifference()));
		sliderMaxRGBDifferenceValue.setFont(labelFont);
		panelMaxRGBDifference.add(sliderMaxRGBDifferenceValue);
		
		this.getContentPane().add(panelMaxRGBDifference);
		
		// Square check box
		JPanel panelCheckbox = new JPanel();
		panelCheckbox.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		checkbox_isSquare = new JCheckBox("Is Square");
		checkbox_isSquare.addItemListener(controller);
		checkbox_isSquare.setName("checkboxIsSquare");
		checkbox_isSquare.setToolTipText("If a tile is a square the width and height always will have the same value.");
		checkbox_isSquare.setSelected(model.isSquare());
		checkbox_isSquare.setEnabled(false); //Disable slider as default because of the use of example tiles
		panelCheckbox.add(checkbox_isSquare);
		
		JCheckBox checkbox = new JCheckBox("Use Best Matches");
		checkbox.addItemListener(controller);
		checkbox.setToolTipText("<html>"
                              + "Activating this option will effect the"
                              +"<br>"
                              + "resulting tiles always will be the best ones"
                              +"<br>"
                              + "This may cause the result having many"
                              + "<br>"
                              + "repetitions of the same tiles."
                              + "<br>"
                              + "<br>"
                              + "Deactivation this option to have more"
                              + "<br>"
                              + "randomness in the result mosaic image."
                              + "</html>");
		checkbox.setName("useBestMatches");
		panelCheckbox.add(checkbox);
		
		checkbox_useOwnTiles = new JCheckBox("Use own tiles");
		checkbox_useOwnTiles.addItemListener(controller);
		checkbox_useOwnTiles.setToolTipText("<html>"
                + "Use own tiles which are inside the Tiles folder"
                +"<br>"
                + "instead of the example ones."
                +"<br>"
                + "Own tiles are scalable at any time."
                + "</html>");
		checkbox_useOwnTiles.setName("useOwnTiles");
		panelCheckbox.add(checkbox_useOwnTiles);
		
		this.getContentPane().add(panelCheckbox);
	
		// okay and cancel button
		JPanel panelButtons = new JPanel();
		
		JButton button = new JButton("Ok");
		// Set okay button as default button
		this.getRootPane().setDefaultButton(button);
		button.addActionListener(controller);
		panelButtons.add(button);
		
		button = new JButton("Cancel");
		button.addActionListener(controller);
		panelButtons.add(button);
		
		this.getContentPane().add(panelButtons);
	}
	
	
	
	
	/* OBSERVER */
	
	@Override
	public void update(Observable o, Object arg)
	{
		checkbox_isSquare.setSelected(model.isSquare());
		
		// Update labels
		sliderTilesWidthValue.setText(String.valueOf(model.getTileWithSourceOwn()));
		sliderTileHeightValue.setText(String.valueOf(model.getTileHeightSourceOwn()));
		sliderMaxRGBDifferenceValue.setText(String.valueOf(model.getMaxRGBDifference()));
		
		this.repaint();
	}	
	
	
	
	
	/* GETTER */
	
	public JSlider getSliderTilesWidth()
	{
		return sliderTilesWidth;
	}
	
	public JSlider getSliderTilesHeight()
	{
		return sliderTilesHeight;
	}
	
	public JSlider getSliderMaxRGBDifference()
	{
		return sliderMaxRGBDifference;
	}
	
	public JCheckBox getCheckboxIsSquare()
	{
		return checkbox_isSquare;
	}
	
	public JCheckBox getCheckboxUseOwnTiles()
	{
		return checkbox_useOwnTiles;
	}
	
	public JLabel getSliderTilesWidthValue()
	{
		return sliderTilesWidthValue;
	}

	public JLabel getSliderTileHeightValue()
	{
		return sliderTileHeightValue;
	}
	
	public JLabel getSliderMaxRGBValue()
	{
		return sliderMaxRGBDifferenceValue;
	}
}