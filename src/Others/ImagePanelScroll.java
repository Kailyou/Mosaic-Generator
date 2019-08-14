package Others;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import Model.Model;

/**
 * Frame which will show an image in full size inside a scroll pane.
 * @author Thielen M.
 */
public class ImagePanelScroll extends JFrame 
{
	private static final long serialVersionUID = 4185821669124345797L;
	
	// Dimension of the current screen size
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();
	
	/**
	 * The constructor of the class.
	 * @param model -
	 * A reference to the model
	 */
	public ImagePanelScroll(Model model) 
	{
		super("Fullscreen image");
		
		ImageIcon ii;
		
		if(model.isMosaikShown())
		{
			ii = new ImageIcon(model.getMosaicImage());
		}
		else
		{
			ii = new ImageIcon(model.getSourceImage());
		}
		
	    JScrollPane jsp = new JScrollPane(new JLabel(ii));
	    this.getContentPane().add(jsp);
	    this.setSize(screenSize);
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setVisible(true);
	}
}
