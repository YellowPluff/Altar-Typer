package typer.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import javax.swing.Timer;
import typer.Settings.Settings;
import typer.Tabs.CentralPanel;
import typer.Tools.Host;
import typer.Tools.RobotClass;
import typer.Tools.Utils;

public class AltarTyperThread implements Runnable {

	private Thread worker;
	private boolean running;
	private int timerCount;
	private int timerCountHours;
	private int timerCountMinutes;
	private int timerCountSeconds;

	public void start() {
		worker = new Thread(this);
		worker.start();
		startTimer(Settings.antibanSettings.getStop_timer_time());
	}

	private void startTimer(int stop_timer_time) {
		timerCount = stop_timer_time;
		Timer stopTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timerCountHours = (timerCount / 3600);
				timerCountMinutes = (timerCount / 60) % 60;
				timerCountSeconds = timerCount % 60;
				Settings.frame.setTitle(Settings.FRAME_TITLE + " (" + timerCountHours + "h " + timerCountMinutes + "m " + timerCountSeconds + "s" + ")");
				timerCount--;
				if (!running) {
					((Timer)e.getSource()).stop();
					Settings.frame.setTitle(Settings.FRAME_TITLE);
				}
				if (timerCount <= 0) {
					((Timer)e.getSource()).stop();
					CentralPanel.stopTyping.doClick();
					Settings.frame.setTitle(Settings.FRAME_TITLE);
				}
			}
		});
		stopTimer.start();
	}

	public void stop() {
		running = false;
	}

	public void run() {
		running = true;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			Utils.writeErrorReport(e, 142);
		}
		CentralPanel.stopTyping.setEnabled(true);
		String[] texts = CentralPanel.communitySpamTexts.getText().split("\n");
		int arraySize = 5;
		String[] currentLineString = new String[arraySize];
		int typeHostCount = Settings.antibanSettings.getHostCount();
		while (running) {
			if (CentralPanel.onlyHostAdvertisements.isSelected() && CentralPanel.onlyCommunityAdvertisements.isSelected()) {
				currentLineString = getHostSpam(arraySize);
				typeHostCount--;

				if (typeHostCount < 0) {
					typeHostCount = Settings.antibanSettings.getHostCount();
					int nextSpamIndex = getRandom(0, texts.length - 1);
					int previousValue = Integer.parseInt(texts[nextSpamIndex].substring(texts[nextSpamIndex].length() - 3, texts[nextSpamIndex].length() - 1));
					int errorCatch = 0;
					while (previousValue == 0) {
						nextSpamIndex = getRandom(0, texts.length - 1);
						previousValue = Integer.parseInt(texts[nextSpamIndex].substring(texts[nextSpamIndex].length() - 3, texts[nextSpamIndex].length() - 1));
						errorCatch++;
						if (errorCatch > 1000) {
							texts = CentralPanel.communitySpamTexts.getText().split("\n");
						}
					}
					errorCatch = 0;
					currentLineString[0] = texts[nextSpamIndex].substring(0, texts[nextSpamIndex].length() - 4);
					for (int i = 1; i < arraySize; i++) {
						currentLineString[i] = "";
					}
					previousValue--;
					texts[nextSpamIndex] = texts[nextSpamIndex].substring(0, texts[nextSpamIndex].length() - 4) + "%" + formatInt(previousValue) + "%";
				}
			}
			else if (CentralPanel.onlyHostAdvertisements.isSelected()) {

				currentLineString = getHostSpam(arraySize);
				typeHostCount--;
				if (typeHostCount < 0) {
					typeHostCount = Settings.antibanSettings.getHostCount();
				}
			}
			else if (CentralPanel.onlyCommunityAdvertisements.isSelected()) {
				int nextSpamIndex = getRandom(0, texts.length - 1);
				int previousValue = Integer.parseInt(texts[nextSpamIndex].substring(texts[nextSpamIndex].length() - 3, texts[nextSpamIndex].length() - 1));
				int errorCatch = 0;
				while (previousValue == 0) {
					nextSpamIndex = getRandom(0, texts.length - 1);
					previousValue = Integer.parseInt(texts[nextSpamIndex].substring(texts[nextSpamIndex].length() - 3, texts[nextSpamIndex].length() - 1));
					errorCatch++;
					if (errorCatch > 1000) {
						texts = CentralPanel.communitySpamTexts.getText().split("\n");
					}

				}
				errorCatch = 0;
				currentLineString[0] = texts[nextSpamIndex].substring(0, texts[nextSpamIndex].length() - 4);
				for (int i = 1; i < arraySize; i++)
					currentLineString[i] = "";
				previousValue--;
				texts[nextSpamIndex] = texts[nextSpamIndex].substring(0, texts[nextSpamIndex].length() - 4) + "%" + formatInt(previousValue) + "%";
			}
			//Now that we have gotten the current line, we can type it
			/** TODO
			 * Implement anti-bans
			 * 1. Random delay times			done
			 * 2. Random typing speed			done
			 * 3. Random breaking				not started
			 * 4. Stop timer					done
			 * 5. Probability on spam			done
			 */	//TODO
			for (int i = 0; i < arraySize; i++) {
				if (currentLineString[i].equals("")) {
					break;
				}
				for (char c : currentLineString[i].toCharArray()) {
					if (!running) {
						return;
					}
					RobotClass.typeCharacter(c);
					sleep(getRandom(Settings.antibanSettings.getType_speed_delay_min(), Settings.antibanSettings.getType_speed_delay_max()));
				}
				if (!currentLineString[i].equals("")) {
					RobotClass.pressEnter();
				}
				sleep(getRandom(Settings.antibanSettings.getDelay_time_delay_min(), Settings.antibanSettings.getDelay_time_delay_max()));
			}

		}
	}

	private String formatInt(int previousValue) {
		if (previousValue <= 9) {
			return "0" + previousValue;
		}
		return previousValue + "";
	}

	private String[] getHostSpam(int arraySize) {
		String[] hostSpam = new String[arraySize];
		String tempLine = "";
		String locationAndServer = CentralPanel.serverType.getSelectedItem().toString().replace(" - ", " ");
		String[] subData = locationAndServer.split(" ");
		ArrayList<Host> hostList = Settings.OVERRIDE_LIST;
		for (Host h : Settings.OVERRIDE_LIST) {
			if (h.getUsername().equals("null")) {
				hostList = Settings.allHosts;
			}
		}
		System.out.println(hostList);
		for (Host h : hostList) {
			String username = h.getUsername().toLowerCase().replace("*", "");
			if (Settings.BLOCKED_LIST.contains(username)) {
				continue;
			}
			if (CentralPanel.onlyVerifiedHosts.isSelected()) {
				if (h.getServer().equals(subData[0]) && h.getWorld().equals(subData[1]) && h.getLocation().equals(subData[2]) 
						&& !h.getUsername().contains("*")) {
					tempLine += "\"" + h.getUsername() + "\" ";
				}
			} 
			else {
				if (h.getServer().equals(subData[0]) && h.getWorld().equals(subData[1]) && h.getLocation().equals(subData[2])) {
					if (h.getUsername().contains("*")) {
						tempLine += "\"" + h.getUsername() + "\" ";
					}
					else {
						tempLine = "\"" + h.getUsername() + "\" " + tempLine;
					}
				}
			}
		}

		if (tempLine.equals("")) {
			for (int i = 0; i < arraySize; i++) {
				hostSpam[i] = "";
			}
			return hostSpam;
		}
		else {
			tempLine = tempLine.substring(0, tempLine.length() - 2);
			tempLine = tempLine + " ]";
			tempLine = tempLine.replace("*", "");
			tempLine = tempLine.replaceAll("\" \"", " ] [ ");
			tempLine = tempLine.replaceAll("\"", "[ ");
		}

		/**
		 * All this shit is a failsafe, it can support up to 5 lines of host names just incase
		 */

		String[] hosts = tempLine.split(Pattern.quote("] ["));
		for (int i = 0; i < hosts.length; i++) {
			hosts[i] = hosts[i].replaceAll("\\[ ", "");
			hosts[i] = hosts[i].replaceAll(" \\]", "");
			hosts[i] = hosts[i].trim();
		}
		for (int i = 0; i < hosts.length; i++) {
			hosts[i] = "[ " + hosts[i] + " ]";
		}

		String line1, line2, line3, line4, line5;

		// random colours is selected & verified hosts is selected
		if (CentralPanel.useColours.isSelected()) {
			if (CentralPanel.onlyVerifiedHosts.isSelected()) {
				line1 = getRandomColour() + "Verified Hosts: ";
				line2 = getRandomColour() + "Verified Hosts: ";
				line3 = getRandomColour() + "Verified Hosts: ";
				line4 = getRandomColour() + "Verified Hosts: ";
				line5 = getRandomColour() + "Verified Hosts: ";
				for (String s : hosts) {
					if (line1.length() + s.length() <= 80) {
						line1 += s;
					}
					else if (line2.length() + s.length() <= 80) {
						line2 += s;
					}
					else if (line3.length() + s.length() <= 80) {
						line3 += s;
					}
					else if (line4.length() + s.length() <= 80) { 
						line4 += s;
					}
					else if (line5.length() + s.length() <= 80) {
						line5 += s;
					}
				}
			}
			// random colours is selected & verified hosts isn't selected
			else {
				line1 = getRandomColour() + "House Hosts: ";
				line2 = getRandomColour() + "House Hosts: ";
				line3 = getRandomColour() + "House Hosts: ";
				line4 = getRandomColour() + "House Hosts: ";
				line5 = getRandomColour() + "House Hosts: ";
				for (String s : hosts) {
					if (line1.length() + s.length() <= 80) {
						line1 += s;
					}
					else if (line2.length() + s.length() <= 80) {
						line2 += s;
					}
					else if (line3.length() + s.length() <= 80) {
						line3 += s;
					}
					else if (line4.length() + s.length() <= 80) { 
						line4 += s;
					}
					else if (line5.length() + s.length() <= 80) {
						line5 += s;
					}
				}
			}
		}
		// random colours isn't selected & verified hosts is selected
		else {
			if (CentralPanel.onlyVerifiedHosts.isSelected()) {
				line1 = useSelectedColour() + useSelectedEffect() + "Verified Hosts: ";
				line2 = useSelectedColour() + useSelectedEffect() + "Verified Hosts: ";
				line3 = useSelectedColour() + useSelectedEffect() + "Verified Hosts: ";
				line4 = useSelectedColour() + useSelectedEffect() + "Verified Hosts: ";
				line5 = useSelectedColour() + useSelectedEffect() + "Verified Hosts: ";
				for (String s : hosts) {
					if (line1.length() + s.length() <= 80) {
						line1 += s;
					}
					else if (line2.length() + s.length() <= 80) {
						line2 += s;
					}
					else if (line3.length() + s.length() <= 80) {
						line3 += s;
					}
					else if (line4.length() + s.length() <= 80) { 
						line4 += s;
					}
					else if (line5.length() + s.length() <= 80) {
						line5 += s;
					}
				}
			}
			// random colours isn't selected & verified hosts isn't selected
			else {
				line1 = useSelectedColour() + useSelectedEffect() + "House Hosts: ";
				line2 = useSelectedColour() + useSelectedEffect() + "House Hosts: ";
				line3 = useSelectedColour() + useSelectedEffect() + "House Hosts: ";
				line4 = useSelectedColour() + useSelectedEffect() + "House Hosts: ";
				line5 = useSelectedColour() + useSelectedEffect() + "House Hosts: ";
				for (String s : hosts) {
					if (line1.length() + s.length() <= 80) {
						line1 += s;
					}
					else if (line2.length() + s.length() <= 80) {
						line2 += s;
					}
					else if (line3.length() + s.length() <= 80) {
						line3 += s;
					}
					else if (line4.length() + s.length() <= 80) { 
						line4 += s;
					}
					else if (line5.length() + s.length() <= 80) {
						line5 += s;
					}
				}
			}
		}
		if (!line1.contains("[")) {
			line1 = "";
			hostSpam[0] = line1;
		}
		else {
			hostSpam[0] = line1;
		}
		if (!line2.contains("[")) {
			line2 = "";
			hostSpam[1] = line2;
		}
		else {
			hostSpam[1] = line2;
		}
		if (!line3.contains("[")) {
			line3 = "";
			hostSpam[2] = line3;
		}
		else {
			hostSpam[2] = line3;
		}
		if (!line4.contains("[")) {
			line4 = "";
			hostSpam[3] = line4;
		}
		else {
			hostSpam[3] = line4;
		}
		if (!line5.contains("[")) {
			line5 = "";
			hostSpam[4] = line5;
		}
		else {
			hostSpam[4] = line5;
		}
		return hostSpam;
	}

	private String getRandomColour() {
		String[] colours = {"white:", "cyan:", "green:", "glow1:", "glow3:", ""};
		int rnd = new Random().nextInt(colours.length);
		return colours[rnd];
	}

	private String useSelectedColour() {
		//"Yellow", "Purple", "Red", "Green", "White", "Cyan", "Flash1", "Flash2", "Flash3", "Glow1", "Glow2", "Glow3"
		if (CentralPanel.colour.getSelectedItem().toString().equals("Yellow")) {
			return "";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Purple")) {
			return "purple:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Red")) {
			return "red:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Green")) {
			return "green:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("White")) {
			return "white:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Cyan")) {
			return "cyan:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Flash1")) {
			return "flash1:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Flash2")) {
			return "flash2:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Flash3")) {
			return "flash3:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Glow1")) {
			return "glow1:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Glow2")) {
			return "glow2:";
		}
		else if (CentralPanel.colour.getSelectedItem().toString().equals("Glow3")) {
			return "glow3:";
		}
		return null;

	}

	private String useSelectedEffect() {
		//"None", "Wave", "Wave2", "Scroll", "Slide", "Shake"
		if (CentralPanel.effect.getSelectedItem().toString().equals("No Effect")) {
			return "";
		}
		else if (CentralPanel.effect.getSelectedItem().toString().equals("Wave")) {
			return "wave:";
		}
		else if (CentralPanel.effect.getSelectedItem().toString().equals("Wave2")) {
			return "wave2:";
		}
		else if (CentralPanel.effect.getSelectedItem().toString().equals("Scroll")) {
			return "scroll:";
		}
		else if (CentralPanel.effect.getSelectedItem().toString().equals("Slide")) {
			return "slide:";
		}
		else if (CentralPanel.effect.getSelectedItem().toString().equals("Shake")) {
			return "shake:";
		}
		return null;
	}

	private int getRandom(int smallestWait, int biggestWait) {
		return new Random().nextInt(biggestWait - smallestWait + 1) + smallestWait;
	}

	private void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			Utils.writeErrorReport(e, 142);
		}
	}
}