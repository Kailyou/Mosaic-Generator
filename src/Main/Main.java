package Main;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Model.Model;
import View.InfoView;
import View.MainView;
import View.SettingView;

/**
 * Mosaic-Generator is a tool which generates a mosaic image out of a loaded source image.
 * @author Thielen M.
 * @version 1.0, Aug 2016
 */
public class Main
{
	// References
	public static Model model;
	public static MainView mainView;
	public static InfoView infoView;
	public static SettingView settingView;
	
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		// Load all swing widgets with a nativ-ish look
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		// Model
		model	= new Model();
		
		// Views
		infoView 	= new InfoView("INFO", model);
		settingView	= new SettingView(mainView, model);
		mainView 	= new MainView(model.getToolName() + model.getVersionNumber(), model, infoView, settingView);
		
		// Add observers to model
		model.addObserver(mainView);
		model.addObserver(infoView);
	}
}
