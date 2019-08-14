package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Model.Model;

/**
 * A view class with some informations about the target image and the tiles.
 * @author Kailyou
 */
public class InfoView extends JFrame implements Observer
{
	private static final long serialVersionUID = 5767645976025232035L;

	// START SIZE
	private final int		WIDTH  = 400;
	private final int 		HEIGHT = 600;
	private final Dimension	DIMENSION;
	
	// Reference
	Model model;
	
	// Source image data
	private JLabel targetImageNameValue;
	private JLabel targetImageOriginalDimensionValue;
	private JLabel targetImageScaledDimensionValue;
	private JLabel sourceTilesNeededPerRowValue;
	private JLabel sourceTilesNeededPerColumnValue;
	private JLabel sourceTilesNeededOverallValue;
	
	// Tile data
	private JLabel tilesSourceValue;
	private JLabel tilesWidthValue;
	private JLabel tilesHeightValue;
	private JLabel maxRGBDiffValue;
	private JLabel generatedSampleTilesValue;
	private JLabel generatedSourceTilesValue;
	private JLabel generatedResultTilesValue;
	
	// Fonts
	private Font headerFont;
	private Font titleFont;
	private Font textFont;
	
	/**
	 * The constructor of the class.
	 * @param title -
	 * The title of the tool
	 * @param model - 
	 * A reference to the model
	 */
	public InfoView(String title, Model model)
	{
		super(title);
		
		this.model = model;
		
		this.setLayout(new GridLayout(0,2));
		
		// Set a minimum frame size
		DIMENSION = new Dimension(WIDTH, HEIGHT);
		this.setMinimumSize(DIMENSION);
		this.setResizable(false);
		
		// Set font
		headerFont = new Font("Serif", Font.BOLD, 20); 
		titleFont  = new Font("Serif", Font.BOLD, 16); 
		textFont   = new Font("Serif", Font.PLAIN, 16); 
		
		// Initialization
		buildInfoView();
			
		this.setLocationRelativeTo(null);
	}
	
	
	/**
	 * Builds the view elements.
	 */
	private void buildInfoView()
	{
		buildTargetImageInfo();
		
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		
		buildTileInfo();
		
		this.pack();
	}
	
	
	/**
	 * Builds the target image information elements.
	 */
	private void buildTargetImageInfo()
	{
		JLabel target = new JLabel("TARGET IMAGE");
		target.setFont(headerFont);
		this.add(target);
		this.add(new JLabel(""));
		
		JLabel targetImageName = new JLabel("Name");
		targetImageName.setFont(titleFont);
		this.add(targetImageName);
		
		targetImageNameValue = new JLabel(model.getSourceImageName());
		targetImageNameValue.setFont(textFont);
		this.add(targetImageNameValue);
		
		JLabel targetImageOriginalDimension = new JLabel("Original Dimension: ");
		targetImageOriginalDimension.setFont(titleFont);
		this.add(targetImageOriginalDimension);
		
		targetImageOriginalDimensionValue = new JLabel(String.valueOf(model.getSourceImageCopy().getWidth() + "px / " + model.getSourceImageCopy().getHeight() + "px"));
		targetImageOriginalDimensionValue.setFont(textFont);
		this.add(targetImageOriginalDimensionValue);
		
		JLabel targetImageScaledDimension = new JLabel("Scaled Dimension: ");
		targetImageScaledDimension.setFont(titleFont);
		this.add(targetImageScaledDimension);
		
		targetImageScaledDimensionValue = new JLabel(String.valueOf(model.getSourceImage().getWidth() + "px / " + model.getSourceImage().getHeight() + "px"));
		targetImageScaledDimensionValue.setFont(textFont);
		this.add(targetImageScaledDimensionValue);
		
		JLabel sourceTilesNeededPerRow = new JLabel("Tiles needed (Row)");
		sourceTilesNeededPerRow.setFont(titleFont);
		this.add(sourceTilesNeededPerRow);
		
		sourceTilesNeededPerRowValue = new JLabel(String.valueOf(model.getTilesNeededRow()));
		sourceTilesNeededPerRowValue.setFont(textFont);
		this.add(sourceTilesNeededPerRowValue);
		
		JLabel sourceTilesNeededPerColumn = new JLabel("Tiles needed (Column)");
		sourceTilesNeededPerColumn.setFont(titleFont);
		this.add(sourceTilesNeededPerColumn);
		
		sourceTilesNeededPerColumnValue = new JLabel(String.valueOf(model.getTilesNeededColumns()));
		sourceTilesNeededPerColumnValue.setFont(textFont);
		this.add(sourceTilesNeededPerColumnValue);
		
		JLabel sourceTilesNeededOverall = new JLabel("Tiles needed Overall");
		sourceTilesNeededOverall.setFont(titleFont);
		this.add(sourceTilesNeededOverall);
		
		sourceTilesNeededOverallValue = new JLabel(String.valueOf(model.getTilesNeededOverall()));
		sourceTilesNeededOverallValue.setFont(textFont);
		this.add(sourceTilesNeededOverallValue);
	}
	
	
	/**
	 * Builds the source tiles information elements.
	 */
	private void buildTileInfo()
	{
		JLabel tiles = new JLabel("TILES");
		tiles.setFont(headerFont);
		this.add(tiles);
		this.add(new JLabel(""));
		
		JLabel tilesSource = new JLabel("Source:");
		tilesSource.setFont(titleFont);
		this.add(tilesSource);
		
		tilesSourceValue = new JLabel(model.getUsedTiles());
		tilesSourceValue.setFont(textFont);
		this.add(tilesSourceValue);

		JLabel tilesWidth = new JLabel("Width:");
		tilesWidth.setFont(titleFont);
		this.add(tilesWidth);

		tilesWidthValue = new JLabel(String.valueOf(model.getTileWidthSourceCurrentUsed()));
		tilesWidthValue.setFont(textFont);
		this.add(tilesWidthValue);

		JLabel tilesHeight = new JLabel("Height:");
		tilesHeight.setFont(titleFont);
		this.add(tilesHeight);
		
		tilesHeightValue = new JLabel(String.valueOf(model.getTileHeightSourceCurrentUsed()));
		tilesHeightValue.setFont(textFont);
		this.add(tilesHeightValue);
		
		JLabel maxRGBDiff = new JLabel("Max RGB Difference:");
		maxRGBDiff.setFont(titleFont);
		this.add(maxRGBDiff);
		
		maxRGBDiffValue = new JLabel(String.valueOf(model.getMaxRGBDifference()));
		maxRGBDiffValue.setFont(textFont);
		this.add(maxRGBDiffValue);

		JLabel generatedSampleTiles = new JLabel("Generated Sample Tiles");
		generatedSampleTiles.setFont(titleFont);
		this.add(generatedSampleTiles);

		generatedSampleTilesValue = new JLabel(String.valueOf(model.getTileListSourceCurrentUsed().size()));
		generatedSampleTilesValue.setFont(textFont);
		this.add(generatedSampleTilesValue);

		JLabel generatedSourceTiles = new JLabel("Generated Source Tiles");
		generatedSourceTiles.setFont(titleFont);
		this.add(generatedSourceTiles);

		generatedSourceTilesValue = new JLabel(String.valueOf(model.getAmountOfsourceTiles()));
		generatedSourceTilesValue.setFont(textFont);
		this.add(generatedSourceTilesValue);
		
		JLabel generatedResultTiles = new JLabel("Generated Result Tiles");
		generatedResultTiles.setFont(titleFont);
		this.add(generatedResultTiles);
		
		generatedResultTilesValue = new JLabel(String.valueOf(model.getAmountOfResultTiles()));
		generatedResultTilesValue.setFont(textFont);
		this.add(generatedResultTilesValue);
	}

	
	/**
	 * Update function, which will be called if the model has been changed.
	 * Updates the values.
	 * @param o -
	 * The observable object
	 * @param arg -
	 * An argument, not needed in this function
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		// Update source value labels
		targetImageNameValue.setText(model.getSourceImageName());
		targetImageOriginalDimensionValue.setText(String.valueOf(model.getSourceImageCopy().getWidth() + "px / " + model.getSourceImageCopy().getHeight() + "px"));
		targetImageScaledDimensionValue.setText(String.valueOf(model.getSourceImage().getWidth() + "px / " + model.getSourceImage().getHeight() + "px"));
		sourceTilesNeededPerRowValue.setText(String.valueOf(model.getTilesNeededRow()));
		sourceTilesNeededPerColumnValue.setText(String.valueOf(model.getTilesNeededColumns()));
		sourceTilesNeededOverallValue.setText(String.valueOf(model.getTilesNeededOverall()));
		
		// Update tile value labels
		tilesSourceValue.setText(String.valueOf(model.getUsedTiles()));
		tilesWidthValue.setText(String.valueOf(model.getTileWidthSourceCurrentUsed()));
		tilesHeightValue.setText(String.valueOf(model.getTileHeightSourceCurrentUsed()));
		maxRGBDiffValue.setText(String.valueOf(model.getMaxRGBDifference()));
		generatedSampleTilesValue.setText(String.valueOf(model.getTileListSourceCurrentUsed().size()));
		generatedSourceTilesValue.setText(String.valueOf(model.getAmountOfsourceTiles()));
		generatedResultTilesValue.setText(String.valueOf(model.getAmountOfResultTiles()));
	}
}
