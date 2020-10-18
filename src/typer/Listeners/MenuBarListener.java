package typer.Listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import javax.swing.JOptionPane;
import typer.Settings.Settings;
import typer.Tools.Utils;

public class MenuBarListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		switch(e.getActionCommand()) {
		case "Apply Changes":
			Settings.initializeAntibanSettings();
			break;
		case "New":
			try {
				if (!Utils.fakeConfigFile.exists()) {
					Utils.makeConfigFile();
					Settings.initializeAntibanSettings();
					JOptionPane.showMessageDialog(null, "Config file created\n" + Utils.fakeConfigFile, "Config File", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					Utils.fakeConfigFile.delete();
					Utils.makeConfigFile();
					Settings.initializeAntibanSettings();
					JOptionPane.showMessageDialog(null, "Config file created\n" + Utils.fakeConfigFile, "Config File", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case "Open":
			try {
				Desktop desktop = null;
				if (Desktop.isDesktopSupported()) {
					desktop = Desktop.getDesktop();
				}

				desktop.open(new File(System.getProperty("user.home") + "/Desktop/AltarTyperConfigFile147.txt"));
			} catch (Exception e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(null, "AltarTyperConfigFile147.txt could not be located on your Desktop", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "Don't Click This":
			try {
				Desktop desktop = java.awt.Desktop.getDesktop();
				URI oURL = new URI("https://www.youtube.com/watch?v=hIputTTexwA&ab_channel=ilcondor");
				desktop.browse(oURL);
			} catch (Exception e3) {
				e3.printStackTrace();
			}
			break;
		}
	}
}
