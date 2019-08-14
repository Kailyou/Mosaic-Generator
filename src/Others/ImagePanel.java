package Others;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Bordered panel with an image drawn inside chosen by a user
 * or a sample image, if there was no image loaded so far.
 * @author Thielen M.
 */
public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = 5579096266467300254L;
	
	// Padding
	private final int paddingWidth  = 100;
	private final int paddingHeight = 50;
	
	private BufferedImage image;
	
	// Scale factor of the image
	private float image_scale_width;
	private float image_scale_height;
	
	// Width & height of the image
	private int image_currentWidth;
	private int image_currentHeight;
		
	// Width & height of the tiles
	private int tilesWidth;
	private int tilesHeight;
	
	// Configuration
	private boolean gridEnabled   = false;
	


	
	/**
	 * The constructor of the class.
	 * @param image -
	 * The image to drawn
	 * @param tilesWidth -
	 * The current tile width
	 * @param tilesHeight
	 * The current tile height
	 */
    public ImagePanel(BufferedImage image, int tilesWidth, int tilesHeight)
    {
    	this.tilesWidth  = tilesWidth;
    	this.tilesHeight = tilesHeight;
    	
    	this.image = image;
    }
 
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    	
    	drawImageWithBorder(g);   
            
        if(gridEnabled)
        {
        	drawGrid(g);
        }
        
        // Dispose the Graphics
        g.dispose();
    }    

    
    /**
     * Draws a border for the image.
     * @param g
     * - The graphic object to draw with
     */
    private void drawImageWithBorder(Graphics g)
    {
    	// Black border
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        
        // Draw the rectangle
        g2.drawRect(paddingWidth, paddingHeight,
        			this.getWidth()  - paddingWidth * 2,
        			this.getHeight() - paddingHeight * 2
        		    );
                      
        // Get new size
        image_currentWidth  = this.getWidth()  - (paddingWidth * 2)  - 3;
        image_currentHeight = this.getHeight() - (paddingHeight * 2) - 3;
        
        // Calculate scale factor	
    	image_scale_width  = (float) image_currentWidth  / image.getWidth();
    	image_scale_height = (float) image_currentHeight / image.getHeight();
        
        // Draw image
        g.drawImage(image,
        			paddingWidth+2, paddingHeight+2,
        			image_currentWidth,
        			image_currentHeight,
        			null);
    }
            
    
    /**
     * Draws the grid which shows the allocation of the tiles.
     * @param g
     * - The graphic object to draw with
     */
    private void drawGrid(Graphics g)
    {
    	g.setColor(Color.RED);
    	
    	// Vertical lines
    	for(int i = 0; i <= this.getWidth()- paddingWidth * 2; i+= tilesWidth * image_scale_width )
    	{
    		g.drawLine(i + paddingWidth , paddingHeight, i + paddingWidth, this.getHeight()-paddingHeight);
    	}
    	
    	// Horizontal lines
    	for(int i = 0; i <= this.getHeight() - paddingHeight * 2; i+= tilesHeight * image_scale_height)
    	{    
    		g.drawLine(paddingWidth, i + paddingHeight, this.getWidth()-paddingWidth, i + paddingHeight);
    	}
    }
        
    
    
       
    /* GETTER */
    
    public BufferedImage getImage()
    {
    	return image;
    }
    
    
    
    
    /* SETTER */
    
    public void setImage(BufferedImage newImage)
    {
 	   image = newImage;
    }
    
    public void setGridEnabled(boolean isEnabled)
    {
    	gridEnabled = isEnabled;
    }
    
    public void setTileWidth(int tileWidth)
    {
    	this.tilesWidth = tileWidth;
    }
    
    public void setTileHeight(int tileHeight)
    {
    	this.tilesHeight = tileHeight;
    }
}