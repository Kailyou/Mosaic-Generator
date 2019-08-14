package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Exceptions.IllegalCaseException;
import Model.Model;
import Others.Helper;
import View.SettingView;

/**
 * A controller class which handles the input of the tileSettingView.
 * @author Thielen M.
 */
public class Controller_tileSettings implements ActionListener, ItemListener, ChangeListener
{
	// References
	private Model model;
	private SettingView settingView;
	
	/**
	 * The constructor of the class.
	 * @param model -
	 * A reference to the model
	 * @param settingView -
	 * A reference to the settingView
	 */
	public Controller_tileSettings(Model model, SettingView settingView)
	{
		this.model 			= model;
		this.settingView	= settingView;
	}

	@Override
	public void itemStateChanged(ItemEvent event)
	{
		String s = Helper.getObjectName(event.getSource());
		
		switch(s)
		{
			case "checkboxIsSquare":
				
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					model.setIsSquare(true);
					
					// Adjust slider values 
					settingView.getSliderTilesHeight().setValue(settingView.getSliderTilesWidth().getValue());
				}
				else
				{
					model.setIsSquare(false);
				}
				
				break;
				
			case "useBestMatches":
				
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					model.setUseBestTiles(true);
					
					// Disable max RGB difference slider
					settingView.getSliderMaxRGBDifference().setEnabled(false);
				}
				else
				{
					model.setUseBestTiles(false);
					
					// Enable max RGB difference slider
					settingView.getSliderMaxRGBDifference().setEnabled(true);
				}
				
				break;
				
			case "useOwnTiles":
				
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					// Enable Sliders and check box
					settingView.getSliderTilesWidth().setEnabled(true);
					settingView.getSliderTilesHeight().setEnabled(true);
					
					// Enable isSquare check box
					settingView.getCheckboxIsSquare().setEnabled(true);
					
					// Update slider
					settingView.getSliderTilesWidth().setValue(model.getTileWithSourceOwn());
					settingView.getSliderTilesWidthValue().setText(String.valueOf(model.getTileWithSourceOwn()));
					settingView.getSliderTilesHeight().setValue(model.getTileHeightSourceOwn());
					settingView.getSliderTileHeightValue().setText(String.valueOf(model.getTileHeightSourceOwn()));
					
					model.setUseOwnTiles(true);
				}
				else
				{
					// Disable Sliders and check box
					settingView.getSliderTilesWidth().setEnabled(false);
					settingView.getSliderTilesHeight().setEnabled(false);
					
					// Disable isSquare check box
					settingView.getCheckboxIsSquare().setSelected(false);
					settingView.getCheckboxIsSquare().setEnabled(false);
					
					// Update values
					settingView.getSliderTilesWidth().setValue(model.getTileWitdhSourceExample());
					settingView.getSliderTilesHeight().setValue(model.getTileHeightSourceExample());
					
					model.setUseOwnTiles(false);
				}

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
	
				// Update which tiles are used
				if(model.usingOwnTiles())
				{
					if(model.getTileListSourceOwn().size() == 0)
					{
						settingView.getCheckboxUseOwnTiles().setSelected(false);
						Helper.infoBox("You first have to load own tiles, click on Setting and then load own tiles.", "Warning");
					}
					else
					{
						int newWidth  = settingView.getSliderTilesWidth().getValue();
						int newHeight = settingView.getSliderTilesHeight().getValue();
						
						boolean widthChange  = model.getTileWithSourceOwn()  != newWidth;
						boolean heightChange = model.getTileHeightSourceOwn() != newHeight;
				
						// If size changed
						if(widthChange || heightChange)
						{
							model.updateOwnTiles(newWidth, newHeight);
						}
						
						model.useOwnTamplates();
					}
				}
				else
				{
					model.useSampleTemplates();
				}
				
				model.setMaxRGBDifference(settingView.getSliderMaxRGBDifference().getValue());

				settingView.setVisible(false);
				
				break;
				
				
			case "Cancel":
				
				settingView.setVisible(false);
				
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

	@Override
	public void stateChanged(ChangeEvent event)
	{
		JSlider source = (JSlider) event.getSource();
		String s = source.getName();

		int value;
		
		// Check which slider
 		switch(s)
 		{
 			case "tileWidthSlider":
     				
 				value = (int)source.getValue();
 				settingView.getSliderTilesWidthValue().setText(String.valueOf(value));
     				
 				// Adjust the width slider
 				if(model.isSquare())
 				{
 					settingView.getSliderTilesHeight().setValue(value);
 				}
     			
     			break;
     			
     			
 			case "tileHeightSlider":
     				
 				value = (int)source.getValue();
     				
 				settingView.getSliderTileHeightValue().setText(String.valueOf(value));
     				
 				// Adjust the height slider
 				if(model.isSquare())
 				{
 					settingView.getSliderTilesWidth().setValue(value);
 				}
     				
 				break;
 				
 			case "maxRGBDifferenceSlider":
 				
 				value = (int)source.getValue();
 				
 				settingView.getSliderMaxRGBValue().setText(String.valueOf(value));
 				
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