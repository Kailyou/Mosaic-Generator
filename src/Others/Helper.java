package Others;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * A small helper class with some useful methods and functions.
 * @author Thielen M.
 */
public class Helper
{
	/**
	 * Shows an information box as message dialog.
	 * @param infoMessage -
	 * The message
	 * @param titleBar -
	 * The title of the information box
	 */
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

	
	/**
	 * Function which changes size of an BufferedImage.
     * Creates a new scaled Image and converts it to a bufferedImage and returns it.
	 * @param image -
	 * The image to scale
	 * @param newWidth -
	 * The new width
	 * @param newHeight -
	 * The new height
	 * @return
	 * A BufferedImage object with the new re scaled BufferedImage.
	 */
    public static BufferedImage resizeImage(final Image image, int newWidth, int newHeight)
    {
    	Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    	BufferedImage resizedBufferedImage = convertImageToBufferedImage(resizedImage);
    	
		return resizedBufferedImage;
    }
    
    
    /**
     * Converts a given Image into a BufferedImage.
     * @param img -
     * The Image to be converted
     * @return 
     * The converted BufferedImage
     */
    public static BufferedImage convertImageToBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    
    /**
	 * This function was taken from examples.javacodegeeks.com (@see).
	 * Returns the name of an object.
	 * @param o
	 * - An object where the name will be returned from.
	 * @return
	 * A String with the name of the object.
	 * @see <a href="https://examples.javacodegeeks.com/desktop-java/awt/event/itemlistener-example/">Itemlistener Example</a>
	 */
	public static String getObjectName(Object o)
	{
		if (o instanceof JComponent)
		{
			JComponent jComponent = (JComponent) o;
			return jComponent.getName();
	    }
		else
		{
		  return o.toString();
		}
	}
}
