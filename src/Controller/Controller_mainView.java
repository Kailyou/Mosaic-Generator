package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Exceptions.IllegalCaseException;
import Model.Model;
import Others.Helper;
import Others.ImagePanelScroll;
import View.InfoView;
import View.LoadOwnTilesView;
import View.MainView;
import View.SettingView;

/**
 * A controller class which handles the input of the mainView.
 * @author Thielen M.
 */
public class Controller_mainView implements ActionListener, ItemListener, MouseListener
{
	// References
	private Model 		model;
	private MainView 	mainView;
	private InfoView	infoView;
	private SettingView	settingView;
	
	/**
	 * The constructor of the class.
	 * @param model -
	 * A reference to the model
	 * @param mainView -
	 * A reference to the mainView
	 * @param infoView -
	 * A reference to the infoView
	 * @param settingView -
	 * A reference to the settingView
	 */
	public Controller_mainView(Model model, MainView mainView, InfoView infoView, SettingView settingView)
	{
		this.model    	 = model;
		this.mainView 	 = mainView;
		this.infoView	 = infoView;
		this.settingView = settingView;
	}

	@Override
	public void itemStateChanged(ItemEvent event)
	{
		String s = Helper.getObjectName(event.getSource());
			
		switch(s)
		{
			case "enableGridCheckbox":
				
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					mainView.getImagePanel().setGridEnabled(true);
				}
				else
				{
					mainView.getImagePanel().setGridEnabled(false);
				}
				
				break;
			
			case "showMosaicImage":
				
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					if(model.getMosaicImage() != null)
					{
						model.setMosaikShown(true);
						mainView.setMosaicImageShown(true);
					}
					else
					{
						model.setMosaikShown(false);
						mainView.getCheckBoxMenuItemShowMosaicImage().setSelected(false);
						Helper.infoBox("There was no mosaic image geneareted yet!\n", "Warning");
					}
				}
				else
				{
					mainView.setMosaicImageShown(false);
				}
				
				mainView.updateImagePanel();
					
				break;
				
			default:
				
				try 
				{
					throw new IllegalCaseException("@ Controller");
				} 
				catch (IllegalCaseException e) 
				{
					e.printStackTrace();
				}
		}
		
		mainView.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		String s = event.getActionCommand();
		
		switch(s)
		{
			case "Open Image":
				mainView.loadImage();
			break;
			
			
			case "Exit":
				System.exit(1);
			
				
			case "Open Result Folder":
				model.open_resultImage_folder();
			break;
			
			
			case "Generate Mosaic!":
				model.generate_mosaic();
			break;
		
			
			case "Save Result":
				mainView.saveImage();
			break;
			
			
			case "Show Info Frame":
				
				if(!infoView.isVisible())
				{
					infoView.setVisible(true);
				}
				
				break;
				
			case "Load own Tiles":
				
				new LoadOwnTilesView(mainView, model);
				break;
				
			case "Tile Settings":
				
				if(!settingView.isVisible())
				{
					settingView.setVisible(true);
				}
				
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
	public void mouseClicked(MouseEvent event) 
	{
		if (event.getClickCount() == 2)
		{
			new ImagePanelScroll(model);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// Not needed
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// Not needed
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// Not needed
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// Not needed
	}
}
