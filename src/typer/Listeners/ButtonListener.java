package typer.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import typer.Settings.Settings;
import typer.Tabs.CentralPanel;
import typer.Tools.Utils;

public class ButtonListener implements ActionListener {

	private static AltarTyperThread typer;

	@Override
	public void actionPerformed(ActionEvent e) {

		switch(e.getActionCommand()) {
		case "Start Typing":
			JOptionPane.showMessageDialog(Settings.frame, "Once you press okay, you have 5 seconds\nto click inside the OSRS/RS3 client");
			typer = new AltarTyperThread();
			typer.start();
			CentralPanel.startTyping.setEnabled(false);
			CentralPanel.pinToTop.setVisible(false);
			CentralPanel.useColours.setVisible(false);
			CentralPanel.colour.setVisible(false);
			CentralPanel.effect.setVisible(false);
			CentralPanel.onlyVerifiedHosts.setVisible(false);
			CentralPanel.onlyHostAdvertisements.setVisible(false);
			CentralPanel.onlyCommunityAdvertisements.setVisible(false);
			
			if (CentralPanel.onlyHostAdvertisements.isSelected() && CentralPanel.onlyCommunityAdvertisements.isSelected()) {
				Settings.frame.setSize(Settings.FRAME_WIDTH, 476);
				CentralPanel.hostLabel.setVisible(true);
				CentralPanel.scrollPane.setVisible(true);
				CentralPanel.communitySpamScroller.setVisible(true);
				CentralPanel.communitySpamScroller.setBounds(5, 200, 570, 150);
				CentralPanel.serverType.setBounds(5, 354, 180, 30);
				CentralPanel.startTyping.setBounds(190, 354, 140, 30);
				CentralPanel.stopTyping.setBounds(340, 354, 220, 30);
			}
			else if (CentralPanel.onlyHostAdvertisements.isSelected()) {
				Settings.frame.setSize(Settings.FRAME_WIDTH, 314);
				CentralPanel.hostLabel.setVisible(true);
				CentralPanel.scrollPane.setVisible(true);
				CentralPanel.communitySpamScroller.setVisible(false);
				CentralPanel.serverType.setBounds(5, 194, 180, 30);
				CentralPanel.startTyping.setBounds(190, 194, 140, 30);
				CentralPanel.stopTyping.setBounds(340, 194, 220, 30);
			}
			else if (CentralPanel.onlyCommunityAdvertisements.isSelected()){
				Settings.frame.setSize(Settings.FRAME_WIDTH, 314);
				CentralPanel.hostLabel.setVisible(false);
				CentralPanel.scrollPane.setVisible(false);
				CentralPanel.communitySpamScroller.setVisible(true);
				CentralPanel.communitySpamScroller.setBounds(5, 40, 570, 150);
				CentralPanel.serverType.setBounds(5, 194, 180, 30);
				CentralPanel.startTyping.setBounds(190, 194, 140, 30);
				CentralPanel.stopTyping.setBounds(340, 194, 220, 30);
			}
			break;
		case "Stop Typing":
			typer.stop();
			CentralPanel.serverType.setEnabled(true);
			CentralPanel.startTyping.setEnabled(true);
			CentralPanel.stopTyping.setEnabled(false);
			CentralPanel.communitySpamScroller.setBounds(5, 285, 570, 150);
			CentralPanel.serverType.setBounds(5, 440, 180, 30);
			CentralPanel.startTyping.setBounds(190, 440, 140, 30);
			CentralPanel.stopTyping.setBounds(340, 440, 220, 30);
			Settings.frame.setSize(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
			try {
				Thread.sleep(Settings.antibanSettings.getType_speed_delay_max() * 2);
			} catch (InterruptedException e1) {
				Utils.writeErrorReport(e1, 942);
			}
			CentralPanel.pinToTop.setVisible(true);
			CentralPanel.useColours.setVisible(true);
			CentralPanel.colour.setVisible(true);
			CentralPanel.effect.setVisible(true);
			CentralPanel.onlyVerifiedHosts.setVisible(true);
			CentralPanel.onlyHostAdvertisements.setVisible(true);
			CentralPanel.onlyCommunityAdvertisements.setVisible(true);
			break;
		case "Configs":
			String RandomBreaks = "Random Breaks: " + "Every " + Settings.antibanSettings.getBreaks_per_hour_min() + "h, up to " + Settings.antibanSettings.getBreaks_per_hour_max() + "h\n\n";
			String RandomBreakLengths = "Random Break lengths: " + Settings.antibanSettings.getBreak_length_min() / 1000 + " - " + Settings.antibanSettings.getBreak_length_max() / 1000 + "s\n" + "\n";
			String TypingSpeeds = "Typing Speeds: " + Settings.antibanSettings.getType_speed_delay_min() + " - " + Settings.antibanSettings.getType_speed_delay_max() + "ms\n" + "\n";
			String DelayLengths = "Delay lengths: " + Settings.antibanSettings.getDelay_time_delay_min() / 1000 + " - " + Settings.antibanSettings.getDelay_time_delay_max() / 1000 + "s\n" + "\n";
			String StopTimer = "Stop timer: " + Settings.antibanSettings.getStop_timer_time() / 3600 + "h\n" + "\n";
			String HostSpamCount = "Host Spam Count: " + Settings.antibanSettings.getHostCount() + "\n\n";
			if (Settings.antibanSettings.getBreaks_per_hour_min() == Settings.antibanSettings.getBreaks_per_hour_max()) {
				RandomBreaks = "Random Breaks: " + "Every " + Settings.antibanSettings.getBreaks_per_hour_min() + "h\n\n";
			}
			if (Settings.antibanSettings.getBreak_length_min() == Settings.antibanSettings.getBreak_length_max()) {
				RandomBreakLengths = "Random Break lengths: " + Settings.antibanSettings.getBreak_length_min() / 1000 + "s\n" + "\n";
			}
			if (Settings.antibanSettings.getType_speed_delay_min() == Settings.antibanSettings.getType_speed_delay_max()) {
				TypingSpeeds = "Typing Speeds: " + Settings.antibanSettings.getType_speed_delay_min() + "ms\n" + "\n";
			}
			if (Settings.antibanSettings.getDelay_time_delay_min() == Settings.antibanSettings.getDelay_time_delay_max()) {
				DelayLengths = "Delay lengths: " + Settings.antibanSettings.getDelay_time_delay_min() / 1000 + "s\n" + "\n";
			}
			JOptionPane.showMessageDialog(Settings.frame, RandomBreaks + RandomBreakLengths + TypingSpeeds + DelayLengths + StopTimer + HostSpamCount, "Configs", JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}
}