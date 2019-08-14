package Model;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javax.imageio.ImageIO;

import Others.Helper;
import Others.Tile;

/**
 * The model class includes the logic behind the mosaic generator.
 * @author Thielen M.
 * 
 */
public class Model extends Observable
{
	// Tool informations
	private final String	toolName 		= "MOSAIC GENERATOR v. ";
	private final float 	versionNumber	= 1.0f;

	// Example source tiles
	private String 		tilePathSourceExample   = "Samples/gameCoverTiles/";
	private final int	tileWidthSourceExample	= 35;
	private final int 	tileHeightSourceExample	= 50; 
	
	// Own source tiles
	private String 	tilePathSourceOwn	= "Tiles/";
	private int 	tileWidthSourceOwn  = 50;
	private int 	tileHeightSourceOwn	= 75;
	private boolean	isSquare 	  		= false;
	
	// Tile data
	private int tileWidthCurrentUsed  = tileWidthSourceExample;
	private int tileHeightCurrentUsed = tileHeightSourceExample;
	private int tilesNeededRow;
	private int tilesNeededColumn;
	private int tilesNeededOverall;
	String usedTiles = "Templates";
	
	// Source image
	private String imagePathExample = "Samples/Images/bleach.jpg";
	private BufferedImage targetImage;
	private BufferedImage targetImageCopy;	// copy is taken to re scale from the original size
	private String sourceImageName;
	
	// Result mosaic image
	private String resultImagePath = System.getProperty("user.dir") + "\\Results\\";

	private BufferedImage mosaicImage;
	private boolean mosaikShown = false; 
	
	// Tile lists
	private ArrayList<Tile> tileListSourceExample;
	private ArrayList<Tile> tileListSourceOwn;
	private ArrayList<Tile> tileListSourceOwnCopy;
	private ArrayList<Tile> tileListSourceCurrentUsed;
	
	private ArrayList<Tile> tileListTargetImage;
	private ArrayList<Tile> tileListResult;
	
	// Configuration
	private boolean useBestTileMatches = false;
	private boolean useOwnTiles 	   = false;
	private int maxRGBDifference 	   = 100;
	
	// Others
	int loadImageTries = 0;
	
	/* CONSTRUCTOR */
	
	public Model()
	{
		// Initialize the tile lists
		tileListSourceExample			= new ArrayList<Tile>();
		tileListSourceOwn				= new ArrayList<Tile>();
		tileListSourceOwnCopy			= new ArrayList<Tile>();
		tileListSourceCurrentUsed  		= new ArrayList<Tile>();
		tileListTargetImage				= new ArrayList<Tile>();
		tileListResult 					= new ArrayList<Tile>();
		
		// Load example source image
		changeTargetImage(imagePathExample);
		sourceImageName = imagePathExample;
					
		// Load the Example tiles
		generate_tile_list_source(tilePathSourceExample, tileWidthSourceExample, tileHeightSourceExample, tileListSourceExample);
				
		// Start by using the template tiles
		tileListSourceCurrentUsed = tileListSourceExample;
	}
	
	
	
	
	/* SOURCE IMAGE */
	
	/**
	 * This method first loads a new image out of a given path.
	 * Then it adjusts the size of this image if needed
	 * and changes the target image finally.
	 * @param imagePath -
	 * The path of the image to load.
	 */
	public void changeTargetImage(String imagePath)
	{
		try 
		{ 
			BufferedImage image = ImageIO.read(new File(imagePath));
	    	targetImageCopy = image;
			adjustImageSize(image);
    		sourceImageName = imagePath;
			modelChanged();
		}
		catch (IOException ex)
	    {
			System.out.println(loadImageTries);
			
			// try again
			while(loadImageTries < 5)
			{
				changeTargetImage(imagePath);
				loadImageTries++;
			}
	    }
		
		loadImageTries = 0;
    }
	
	
	/**
	 * This method tests if the size of the given source image
	 * fits with the size of the current tiles.
	 * This is important to generate tiles out of the source image
	 * without having a gap left.
	 * It then adjusts the image if it is needed.
	 * @param imageToAdjust -
	 * The image which may will be adjusted.
	 */
    private void adjustImageSize(BufferedImage imageToAdjust)
    {
		int widthDifference  = imageToAdjust.getWidth()  % tileWidthCurrentUsed;
    	int heightDifference = imageToAdjust.getHeight() % tileHeightCurrentUsed;
    	
    	// Adjust width and height and re scale the image if needed
    	if(widthDifference != 0 || heightDifference != 0)
    	{
    		int newWidth  = imageToAdjust.getWidth()  - widthDifference;
        	int newHeight = imageToAdjust.getHeight() - heightDifference;
        	
        	BufferedImage resizedImage = Helper.resizeImage(imageToAdjust, newWidth, newHeight);
        	targetImage = resizedImage;
    	}
    	else
    	{
    		targetImage = imageToAdjust;
    	}
    	
    	// Update values
		calculateTileData();
    	modelChanged();
    }
    
    
    
    
    /* TILES */
    
    /**
	 * This function returns a new list of scaled tiles.
	 * This function just will be used to change the size of own tiles.
	 * The example tiles will be unaffected.
	 * @param tileList -
	 * The tile list which is supposed to be re scaled.
	 * @param newWidth - 
	 * The new width of the tile elements.
	 * @param newHeight -
	 * The new height of the tile elements.
	 * @return
	 * Returns a new list of scaled tiles.
	 */
	public ArrayList<Tile> changeSizeOfTiles(ArrayList<Tile> tileList, int newWidth, int newHeight)
	{
		ArrayList<Tile> scaledTileList = new ArrayList<Tile>();
		
		// Re scale the tiles
		for(Tile tile : tileList)
		{
			BufferedImage scaledImageOfTile = Helper.resizeImage(tile.getImage(), newWidth, newHeight);
			Tile scaledTile = new Tile(scaledImageOfTile);
			scaledTileList.add(scaledTile);
		}
		
		// Update tile size
		tileWidthCurrentUsed  = newWidth;
		tileHeightCurrentUsed = newHeight;
		
		// Update values 
		calculateTileData();
		
		return scaledTileList;
	}
	
	
	/**
	 * Changes the used templates to the sample ones and creates a new tile list.
	 */
	public void useSampleTemplates()
	{
		// Recover the original image
		targetImage = targetImageCopy;
		
		// update which tiles are used
		tileListSourceCurrentUsed = tileListSourceExample;
		
		// update tile size
		tileWidthCurrentUsed  = tileWidthSourceExample;
		tileHeightCurrentUsed = tileHeightSourceExample;
		
		// update tile informations
		calculateTileData();
		
		isSquare = false;
		
		usedTiles = "Templates";
		
		modelChanged();
	}
	
	
	/**
	 * Changes the used templates to the own ones and creates a new tile list.
	 */
	public void useOwnTamplates()
	{
		// Recover the original image
		targetImage = targetImageCopy;
				
		// update which tiles are used
		tileListSourceCurrentUsed = tileListSourceOwn;
		
		// update tile size
		tileWidthCurrentUsed  = tileWidthSourceOwn;
		tileHeightCurrentUsed = tileHeightSourceOwn;
		
		// update tile informations
		calculateTileData();
		
		usedTiles = "Own Tiles";
		
		modelChanged();
	}
	
	
	/**
	 * Re scales the own tile list and updates the values.
	 * @param newWidth -
	 * The new width of the tiles
	 * @param newHeight -
	 * the new height of the tiles
	 */
	public void updateOwnTiles(int newWidth, int newHeight)
	{
		// Re scale tile list if needed
		ArrayList<Tile> scaledList = new ArrayList<Tile>();
		scaledList 		   = changeSizeOfTiles(tileListSourceOwnCopy, newWidth, newHeight);
		tileListSourceOwn 	   = scaledList;
		tileListSourceCurrentUsed = tileListSourceOwn;
		
		// Update sizes
		tileWidthSourceOwn  = newWidth;
		tileHeightSourceOwn = newHeight;
		
		tileWidthCurrentUsed  = tileWidthSourceOwn;
		tileHeightCurrentUsed = tileHeightSourceOwn;
		
		// Update tile informations
		calculateTileData();
	}
	
    
    /**
     * This small helper function generates the following data.
     * How much tiles are needed to fill out  width of the source image completely
     * How much tiles are needed to fill out the height of the source image completely
     * How much tiles are needed overall.
     */
    private void calculateTileData()
    {
    	// Set data
		tilesNeededRow     = targetImage.getWidth()  / tileWidthCurrentUsed;
		tilesNeededColumn  = targetImage.getHeight() / tileHeightCurrentUsed;
		tilesNeededOverall = tilesNeededRow * tilesNeededColumn;
    }
	
	
	
	
	/* TILE LISTS */
    
    /**
	 * This method generates a list of source tiles by loading images of the given path.
	 * Also does a short check if the loaded tile has the correct size before adding it.
	 * @param folderPath -
	 * The path to the folder where the sample tiles are inside.
	 * @param width -
	 * The width of the tiles
	 * @param height - 
	 * the height of the tiles
	 * @param tileList
	 * The list in which to save the tile objects.
	 */
	private void generate_tile_list_source(String folderPath, int width, int height, ArrayList<Tile> tileList)
	{
		// Clear the old list
		tileList.clear();
		
		// Create the tiles out of the folder's saved files.
		File folder = new File(folderPath);
		
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++)
		    {
		      if (listOfFiles[i].isFile()) 
		      {
		    	  // Get the path of the file
		    	  String filePath = folderPath + listOfFiles[i].getName();
		    	  
		    	  // Create buffered image object to create a tile object
		    	  BufferedImage tileImage = null;
		    	  
		    	  try 
		    	  {
		    		  tileImage = ImageIO.read(new File(filePath));
		    	  } 
		    	  catch (IOException e) 
		    	  {
					e.printStackTrace();
		    	  }
		    	  
		    	  // Check if ratio is correct
		    	  if(tileImage.getWidth() == width && tileImage.getHeight() == height)
		    	  {
			    	  // Add tile object to list
		    		  Tile tile_template = new Tile(tileImage);
		    		  tileList.add(tile_template);
		    	  }
		    	  else
		    	  {
		    		  System.out.println("tile " + listOfFiles[i].getName() + " has wrong solution!");
		    	  }
		      } 
		    }
		    
			modelChanged();
	}
	
	
	/** This method generates tiles out of the target image.
	 *  Those tiles will be saved into a list of tiles
	 *  and will be used to compare them with the sample tiles of the other list.
	 *  The image which will be fragmented in tiles.
	 */
	public void generate_tile_list_targetImage()
	{
		// Clear data
		tileListTargetImage.clear();
		
		// Create tiles (sub images out of the example image)
		for(int y = 0; y < targetImage.getHeight(); y += tileHeightCurrentUsed)
		{
			for(int x = 0; x < targetImage.getWidth(); x += tileWidthCurrentUsed)
			{
				BufferedImage tmp = targetImage.getSubimage(x, y, tileWidthCurrentUsed, tileHeightCurrentUsed);
				tileListTargetImage.add(new Tile(tmp));
			}
		}
		
		modelChanged();
	}
	
	
	/** This method generates a list of tiles which will be used to generate the final mosaic image.
	 *  It will compare the lists of tiles of the source image and the template images.
	 */
	public void generate_tile_list_resultImage()
	{
		// Clear the data
		tileListResult.clear();
		
		// Compare the lists and fill the result tile list with the best fits.
		compare_two_tile_lists(tileListTargetImage, tileListSourceCurrentUsed);
		
		modelChanged();
	}
		
	
	
	
	/* USED PRIVATE FUNCTIONS */
	
	/**
	 * Compares a given list of tiles with a given list of templates
	 * adds the best match of them to the result tile list.
	 * @param source -
	 * The source tile list (generated of the source image)
	 * @param templates -
	 * The template tile list (generated of the template images)
	 */
	private void compare_two_tile_lists(ArrayList<Tile> source, ArrayList<Tile> templates)
	{
		// Compare two tile lists and create another one as result
		for(int i = 0; i < source.size(); ++i)
		{
			// Get the best result by mast, may results many repetitions!
			if(useBestTileMatches)
			{
				tileListResult.add(compare_tile_with_list_and_returns_best_match(source.get(i), templates));
			}
			// Use an algorithm with some random behavior inside instead of (default)
			else
			{
				tileListResult.add(compare_tile_with_list_and_returns_random_good_match(source.get(i), templates));
			}
		}
	}
		
	
	/**
	 * Compares a given tile with all tiles of a given template list.
	 * returns the best match tile.
	 * @param tileToCheck -
	 * The tile which will be compared to any tile in the list of template tiles.
	 * @param templates -
	 * The list of template tiles.
	 * @return
	 * Returns the best matched tile.
	 */
	private Tile compare_tile_with_list_and_returns_best_match(Tile tileToCheck, ArrayList<Tile> templates)
	{
		// Set first tile and calculate the difference
		Tile resultTile = templates.get(0);
		
		// Calculate the color differences of the tile to check with the first element
		int redDifference   = Math.abs(tileToCheck.getAverageColorValues()[0] - templates.get(0).getAverageColorValues()[0]);
		int greenDifference = Math.abs(tileToCheck.getAverageColorValues()[1] - templates.get(0).getAverageColorValues()[1]);
		int blueDifference  = Math.abs(tileToCheck.getAverageColorValues()[2] - templates.get(0).getAverageColorValues()[2]);

		int difference = redDifference + greenDifference + blueDifference;
		
		int newDifference;
		
		for(int i = 0; i < templates.size(); ++i)
		{
			// Calculate the color differences of the tile to check with the current element
			int newRedDifference   = Math.abs(tileToCheck.getAverageColorValues()[0] - templates.get(i).getAverageColorValues()[0]);
			int newGreenDifference = Math.abs(tileToCheck.getAverageColorValues()[1] - templates.get(i).getAverageColorValues()[1]);
			int newBlueDifference  = Math.abs(tileToCheck.getAverageColorValues()[2] - templates.get(i).getAverageColorValues()[2]);
			
			newDifference = newRedDifference + newGreenDifference + newBlueDifference;
			
			// If the new tile has a better match, replace the old one.
			if(newDifference < difference)
			{
				difference = newDifference;
				resultTile = templates.get(i);
			}
		}
		
		return resultTile;
	}
	
	
	/**
	 * 
	 */
	
	private Tile compare_tile_with_list_and_returns_random_good_match(Tile tileToCheck, ArrayList<Tile> templates)
	{
		ArrayList<Tile> tileList = new ArrayList<Tile>();
		Tile resultTile;
		int difference;
		
		for(int i = 0; i < templates.size(); ++i)
		{
			// Calculate the color differences of the tile to check with the current element
			int redDifference   = Math.abs(tileToCheck.getAverageColorValues()[0] - templates.get(i).getAverageColorValues()[0]);
			int greenDifference = Math.abs(tileToCheck.getAverageColorValues()[1] - templates.get(i).getAverageColorValues()[1]);
			int blueDifference  = Math.abs(tileToCheck.getAverageColorValues()[2] - templates.get(i).getAverageColorValues()[2]);
			
			difference = redDifference + greenDifference + blueDifference;
			
			// Add the tile to a pre selection list if it fits the requirements
			if(difference <= maxRGBDifference)
			{
				tileList.add(templates.get(i));
			}
		}
		
		// If nothing was a good match take the best one
		if(tileList.size() == 0)
		{
			resultTile = compare_tile_with_list_and_returns_best_match(tileToCheck, templates);
			return resultTile;
		}
		else
		{
			Random generator = new Random();
			int randomTile = generator.nextInt(tileList.size());
			
			resultTile = tileList.get(randomTile);
			
			return resultTile;
		}
	}
	
	
	
	
	/* GENERATE */
	
	/**
	 * This method builds a mosaic image out of the tiles within the result tile list
	 * and saves it to the folder "Result" as PNG image.
	 */
	public void generate_mosaic()
	{
		if(tileListSourceCurrentUsed.size() == 0)
		{
			Helper.infoBox("There are no tiles which could be used to generate the mosaic.\n Are you using own tiles and the folder is empty or is the tile size wrong?", "Error");
			return;
		}
		
		// Scale source image if needed
		adjustImageSize(targetImageCopy);
		
		// Update data
		calculateTileData();
		
		// Generate source and result tiles
		generate_tile_list_targetImage();
		generate_tile_list_resultImage();
			
        // Initializing the final image
        BufferedImage mosaicImage = new BufferedImage(tileWidthCurrentUsed * tilesNeededRow, tileHeightCurrentUsed * tilesNeededColumn, BufferedImage.TYPE_INT_RGB);

        int num = 0;
        
        // Draw the final image by drawing each tile image
        for (int j = 0; j < tilesNeededColumn; j++)
        {
            for (int i = 0; i < tilesNeededRow; i++) 
            {
                mosaicImage.createGraphics().drawImage(tileListResult.get(num).getImage(), tileWidthCurrentUsed * i, tileHeightCurrentUsed * j, null);
                num++;
            }
        }
        
        this.mosaicImage = mosaicImage;
        
        Helper.infoBox("Mosaic generated!", "Job's done!");
        
		modelChanged();
	}

	
	
	
	/* OTHER */

	/**
	 * Tries to save the result mosaic image into the given folder
	 * @param path -
	 * The path where to save the file (with file name).
	 */
	public void saveImage(String path)
	{
		try
        {
			if(mosaicImage != null)
			{
				ImageIO.write(mosaicImage, "jpeg", new File(path));
		        Helper.infoBox("Mosaic successfully saved!", "Job's done!");
	        }
			else
			{
		        Helper.infoBox("You first have to generate a mosaic image!", "ERROR");
			}
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This function loads own tiles out of the Tile folder.
	 * There will be only tiles loaded which fits with the given size and
	 * images with a wrong solution won't be re scaled!
	 * @param tileWidth -
	 * The width of the tiles
	 * @param tileHeight -
	 * The height of the tiles
	 */
	public void loadOwnTiles(int tileWidth, int tileHeight)
	{
		tileWidthSourceOwn  = tileWidth;
		tileHeightSourceOwn = tileHeight;
		
		generate_tile_list_source(tilePathSourceOwn, tileWidthSourceOwn, tileHeightSourceOwn, tileListSourceOwn);
		tileListSourceOwnCopy = tileListSourceOwn;
		
		if(tileListSourceOwn.size() == 0)
		{
			Helper.infoBox("Could not load any tile with the solution "
						   + tileWidthSourceOwn + "x" + tileHeightSourceOwn + " pixels.\n"
						   + "Make sure there are tiles with the solution you"
						   + "have chosen in the Tiles folder.", "Error");
		}
		
		else if(tileListSourceOwn.size() <= 100)
		{
			Helper.infoBox("Very few tiles loaded (" + tileListSourceOwn.size() + "). "
					   + "\nTo get a better result, you should use more tiles.",
					   "Warning");
		}
		
		else if(tileListSourceOwn.size() <= 500)
		{
			Helper.infoBox("Less tiles loaded (" + tileListSourceOwn.size() + "). "
					   + "Remember: The more tiles are available "
					   + "The better the result will look like.", "Warning");
		}
		
		else
			Helper.infoBox("Own tiles loaded.", "Info");
		
		modelChanged();
	}
		
	
	/**
	 * Opens the folder where the result mosaic images are saved inside.
	 */
	public void open_resultImage_folder()
	{
		try 
		{
			File file = new File(resultImagePath);
			Desktop.getDesktop().open(file);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/* OBSERVER */
	
	/**
	 * Informs the observer that the model has been changed.
	 */
	public void modelChanged()
	{
		// Notify observers of change
		setChanged();
		notifyObservers();
	}
	
	
	
	
	/* GETTER */
	
	public String getToolName()
	{
		return toolName;
	}
	
	public float getVersionNumber()
	{
		return versionNumber;
	}
	
	public BufferedImage getSourceImage()
	{
		return targetImage;
	}
			
	public BufferedImage getSourceImageCopy()
	{
		return targetImageCopy;
	}

	public String getSourceImageName()
	{
		return sourceImageName;
	}
	
	public BufferedImage getMosaicImage()
	{
		return mosaicImage;
	}
	
	public int getTileWitdhSourceExample()
	{
		return tileWidthSourceExample;
	}
	
	public int getTileHeightSourceExample()
	{
		return tileHeightSourceExample;
	}
			
	public int getTileWithSourceOwn()
	{
		return tileWidthSourceOwn;
	}

	public int getTileHeightSourceOwn()
	{
		return tileHeightSourceOwn;
	}

	public int getTileWidthSourceCurrentUsed()
	{
		return tileWidthCurrentUsed;
	}
	
	public int getTileHeightSourceCurrentUsed()
	{
		return tileHeightCurrentUsed;
	}
	
	public ArrayList<Tile> getTileListSourceOwn()
	{
		return tileListSourceOwn;
	}
	
	public ArrayList<Tile> getTileListSourceCurrentUsed()
	{
		return tileListSourceCurrentUsed;
	}
	
	public int getAmountOfsourceTiles()
	{
		return tileListTargetImage.size();
	}
	
	public int getAmountOfResultTiles()
	{
		return tileListResult.size();
	}
	
	public int getTilesNeededRow()
	{
		return tilesNeededRow;
	}
	
	public int getTilesNeededColumns()
	{
		return tilesNeededColumn;
	}
	
	public int getTilesNeededOverall()
	{
		return tilesNeededOverall;
	}
	
	public boolean isSquare()
	{
		return isSquare;
	}
	
	public boolean usingOwnTiles()
	{
		return useOwnTiles;
	}
	
	public String getUsedTiles()
	{
		return usedTiles;
	}
	
	public int getMaxRGBDifference() 
	{
		return maxRGBDifference;
	}	
	
	public boolean isMosaikShown()
	{
		return mosaikShown;
	}
	
	
	
	
	/* SETTER */
	
	public void setMosaicImage(BufferedImage newImage)
	{
		mosaicImage = newImage;
		modelChanged();
	}
	
	public void setTileWidth(int value)
	{
		tileWidthSourceOwn = value;
		
		// Update source image and tile data
		adjustImageSize(targetImageCopy);
		calculateTileData();
		tileListSourceExample = changeSizeOfTiles(tileListSourceExample, tileWidthCurrentUsed, tileHeightCurrentUsed);	
		
		modelChanged();
	}
	
	public void setTileHeight(int value)
	{
		tileHeightSourceOwn = value;
		
		// Update source image and tile data
		adjustImageSize(targetImageCopy);
		calculateTileData();
		tileListSourceExample = changeSizeOfTiles(tileListSourceExample, tileWidthCurrentUsed, tileHeightCurrentUsed);		
		
		modelChanged();
	}
	
	public void setIsSquare(boolean isSquare)
	{
		this.isSquare = isSquare;
	}
	
	public void setTileListOwn(ArrayList<Tile> newTileList)
	{
		tileListSourceOwn = newTileList;
	}
	
	public void setUseBestTiles(boolean useBestTiles)
	{
		this.useBestTileMatches = useBestTiles;
		
		modelChanged();
	}
	
	public void setUseOwnTiles(boolean useOwnTiles)
	{
		this.useOwnTiles = useOwnTiles;
	}
	
	public void setMaxRGBDifference(int tileToleranceDifference)
	{
		this.maxRGBDifference = tileToleranceDifference;
	}

	public void setMosaikShown(boolean mosaikShown)
	{
		this.mosaikShown = mosaikShown;
	}
}
