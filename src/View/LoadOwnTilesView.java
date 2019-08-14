package View;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import Controller.Controller_loadOwnTiles;
import Model.Model;

/**
 * A view class which is used to set up the load own tiles option.
 * @author Kailyou
 */
public class LoadOwnTilesView extends JDialog
{
	private static final long serialVersionUID = 657797883155709943L;

	// Model
	private Model model;
		
	// Slider
	private final int SLIDER_MIN  = 5;
	private final int SLIDER_MAX  = 100;
	
	// Slider
	private JSlider sliderTilesWidth;
	private JSlider sliderTilesHeight;
		
	private JLabel sliderTilesWidthValue;
	private JLabel sliderTileHeightValue;
	
	// Controller
	private Controller_loadOwnTiles controller;
	
	private Font titleFont;
	private Font labelFont;
	
	/**
	 * The constructor of the class.
	 * @param parentFrame -
	 * The parent frame (mainView)
	 * @param model -
	 * A reference to the model
	 */
	public LoadOwnTilesView(JFrame parentFrame, Model model)
	{
		super(parentFrame, "Load Own Tiles", true);
		
		this.setLayout(new GridLayout(0,1));
		
		// Model
		this.model = model;

		// Controller
		controller = new Controller_loadOwnTiles(model, this);
		
		// Fonts
		titleFont = new Font("Serif", Font.BOLD, 18);
		labelFont = new Font("Serif", Font.BOLD, 14);
		
		buildLoadOwnTilesView();
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Builds the view elements.
	 */
	private void buildLoadOwnTilesView()
	{
		// Title label
		JPanel labelPanel = new JPanel();
		JLabel label = new JLabel("Adjust the values for your own tiles.");
		labelPanel.add(label);
		label.setFont(titleFont);
		this.getContentPane().add(labelPanel, SwingConstants.CENTER);
		
		// Slider
		JPanel panelTileWidth = new JPanel();
		panelTileWidth.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// Tile width
		label = new JLabel("Tile Width ");
		label.setFont(labelFont);
		
		panelTileWidth.add(label);
		
		// Slider tile width
		sliderTilesWidth = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, model.getTileWidthSourceCurrentUsed());
		sliderTilesWidth.setName("tileWidthSlider");
		sliderTilesWidth.addChangeListener(controller);
		sliderTilesWidth.setMajorTickSpacing(10);
		sliderTilesWidth.setMinorTickSpacing(1);
		sliderTilesWidth.setPaintTicks(true);
		sliderTilesWidth.setPaintLabels(true);
		
		panelTileWidth.add(sliderTilesWidth);
		
		sliderTilesWidthValue = new JLabel(String.valueOf(sliderTilesWidth.getValue()));
		
		panelTileWidth.add(sliderTilesWidthValue);
		
		this.getContentPane().add(panelTileWidth);
		
		// Tile Height
		JPanel panelTileHeight = new JPanel();
		panelTileHeight.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		label = new JLabel("Tile Height");
		label.setFont(labelFont);
		
		panelTileHeight.add(label);
		
		// Slider tile height
		sliderTilesHeight = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, model.getTileHeightSourceCurrentUsed());
		sliderTilesHeight.setName("tileHeightSlider");
		sliderTilesHeight.addChangeListener(controller);
		sliderTilesHeight.setMajorTickSpacing(10);
		sliderTilesHeight.setMinorTickSpacing(1);
		sliderTilesHeight.setPaintTicks(true);
		sliderTilesHeight.setPaintLabels(true);
		
		panelTileHeight.add(sliderTilesHeight);
		
		sliderTileHeightValue = new JLabel(String.valueOf(sliderTilesHeight.getValue()));
		
		panelTileHeight.add(sliderTileHeightValue);
		
		this.getContentPane().add(panelTileHeight);
		
		// okay and cancel button
		JPanel panelButtons = new JPanel();
		
		JButton button = new JButton("Ok");
		this.getRootPane().setDefaultButton(button); // Sets okay button as default button
		button.addActionListener(controller);
		panelButtons.add(button);
		
		button = new JButton("Cancel");
		button.addActionListener(controller);
		panelButtons.add(button);
		
		this.getContentPane().add(panelButtons);
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
	
	public JLabel getSliderTilesWidthValue()
	{
		return sliderTilesWidthValue;
	}

	public JLabel getSliderTileHeightValue()
	{
		return sliderTileHeightValue;
	}
}
