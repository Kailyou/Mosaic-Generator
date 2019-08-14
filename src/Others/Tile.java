package Others;
import java.awt.image.BufferedImage;

/**
 * Tile are objects with a BufferedImage inside.
 * Calculate the average RGB values of the tile by summarizing the color values of each pixel
 * and divide it by the max pixel count then.
 * @author Thielen M.
 */
public class Tile
{
	// Tile counter
	private static int idCounter = 0;
	private int id;
	
	// Image
	private BufferedImage image;
	private int maxPixel;
	
	// Average color values
	private int red_average 	= 0;
	private int green_average	= 0;
	private int blue_average 	= 0;
	
	/**
	 * Constructor of the class.
	 * @param tileImage -
	 * The BufferedImage of the tile.
	 */
	public Tile(BufferedImage tileImage)
	{
		id = idCounter;
		idCounter++;
		
		image 		= tileImage;
		maxPixel	= image.getWidth() * image.getHeight();
		
		/* calculate the average RGB value of the tile */
		calculateAverageRGB();
	}
	
	/**
	 * This function calculates the average RGB value of the tile and saves it.
	 */
	private void calculateAverageRGB()
	{
		int red   = 0;
		int green = 0;
		int blue  = 0;
		
		//Iterate over each pixel of the tile
		for (int y = 0; y < image.getHeight(); y++)
		{
		    for (int x = 0; x < image.getWidth(); x++)
		    {
		    	// Get the RGB color values of each pixel
		        int  clr   = image.getRGB(x, y); 
		        red   += (clr & 0x00ff0000) >> 16;
		        green += (clr & 0x0000ff00) >> 8;
		        blue  +=  clr & 0x000000ff;
		    }
		}

		// Get the average RGB value of the tile
		red_average 	= red / maxPixel;
		green_average	= green / maxPixel;
		blue_average    = blue / maxPixel;
	}
	
	
	/**
	 * Prints some data of the tile.
	 */
	public void printInfo()
	{
		System.out.printf("Tile Number: %s, RED: %s, GREEN: %s, BLUE: %s, WIDTH: %s, HEIGHT: %s\n",
					  id, red_average, green_average, blue_average, image.getWidth(), image.getHeight());
	}
	
	
	
	
	/* GETTER */
	
	public BufferedImage getImage()
	{
		return image;
	}

	public int[] getAverageColorValues()
	{
		return new int[] {red_average, green_average, blue_average};
	}
}
