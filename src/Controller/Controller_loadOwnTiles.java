package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Exceptions.IllegalCaseException;
import Model.Model;
import View.LoadOwnTilesView;

/**
 * A controller class which handles the input of the loadOwnTilesView.
 * @author Thielen M.
 */
public class Controller_loadOwnTiles implements ActionListener, ChangeListener
{
	// References
	private Model model;
	private LoadOwnTilesView loadOwnTilesView;
	
	/**
	 * The constructor of the class.
	 * @param model -
	 * A reference to the model
	 * @param loadOwnTilesView -
	 * A reference to the loadOwnTilesView
	 */
	public Controller_loadOwnTiles(Model model, LoadOwnTilesView loadOwnTilesView)
	{
		this.model 		      = model;
		this.loadOwnTilesView = loadOwnTilesView;
	}

	@Override
	public void stateChanged(ChangeEvent event)
	{
		JSlider source = (JSlider) event.getSource();
		String s = source.getName();
		
		// Check which slider
 		switch(s)
 		{
 			case "tileWidthSlider":
     			
 				// Update slider value text
 				int valueWidth = (int)source.getValue();
 				loadOwnTilesView.getSliderTilesWidthValue().setText(String.valueOf(valueWidth));
     	     			
 			break;
     			
     			
 			case "tileHeightSlider":
     			
 				// Update slider value text
 				int valueHeight = (int)source.getValue();
 				loadOwnTilesView.getSliderTileHeightValue().setText(String.valueOf(valueHeight));
     				
 			break;
 		}
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		String s = event.getActionCommand();
		
		switch(s)
		{
			case "Ok":
				model.loadOwnTiles(loadOwnTilesView.getSliderTilesWidth().getValue(), loadOwnTilesView.getSliderTilesHeight().getValue());
				loadOwnTilesView.dispose();
				break;
				
				
			case "Cancel":
				
				loadOwnTilesView.dispose();
				
			break;
	
				
			default:
				try
				{
					throw new IllegalCaseException("Unexpected case");
				}
				catch (IllegalCaseException e)
				{
				e.printStackTrace();
				}
		}
	}
}
