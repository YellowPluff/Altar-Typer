package typer.Tools;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

import typer.Settings.Settings;
import typer.Tabs.CentralPanel;

public class Utils {

	public static File fakeConfigFile = new File(System.getProperty("user.home") + "/Desktop/AltarTyperConfigFile147.txt");

	public static void makeConfigFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fakeConfigFile));
			writer.write("#RANDOM_BREAKS" + "\n");
			writer.write("[Length times must be entered in seconds]" + "\n");
			writer.write("BREAKS_HOURS_MIN=2" + "\n");
			writer.write("BREAKS_HOURS_MAX=23" + "\n");
			writer.write("BREAKS_LENGTH_MIN=3" + "\n");
			writer.write("BREAKS_LENGTH_MAX=5" + "\n");
			writer.write("\n");
			writer.write("#TYPING_SPEED" + "\n");
			writer.write("[Values must be entered in milliseconds]" + "\n");
			writer.write("TYPING_SPEED_MIN=15" + "\n");
			writer.write("TYPING_SPEED_MAX=30" + "\n");
			writer.write("\n");
			writer.write("#ADVERTISING_LINES_DELAY" + "\n");
			writer.write("[Values must be entered in seconds]" + "\n");
			writer.write("LINES_DELAY_MIN=1" + "\n");
			writer.write("LINES_DELAY_MAX=4" + "\n");
			writer.write("\n");
			writer.write("#STOP_TIMER" + "\n");
			writer.write("[Values must be entered in hours (1+)]" + "\n");
			writer.write("STOP_TIME=23" + "\n");
			writer.write("\n");
			writer.write("#HOST_COUNT" + "\n");
			writer.write("[This is how many host spams occur for every 1 general spam (1+)]" + "\n");
			writer.write("HOST_COUNT=3" + "\n");
			writer.write("\n");
			writer.write("#ADVERTISING_LINES" + "\n");
			writer.write("~ [ Altar ] ~ Serving the prayer community since 2009! ~ [ Altar ] ~%25%" + "\n");
			writer.write("~ [ Altar ] ~ Friends Chat or Discord.gg/gsxynse for optimal xp/hr in prayer!%25%" + "\n");
			writer.write("Anyone can learn to host! Join ~ [ 07 Altar ] ~ cc or Discord.gg/gsxynse%25%" + "\n");
			writer.write("Have an in-game question? Ask in ~ [ 07 Altar ] ~ Clan Chat!%25%" + "\n");
			writer.write("Need a G Altar? Join ~ [ 07 Altar ] ~ Clan Chat! :D%25%" + "\n");
			writer.write("Want a fun place to hang out? Join ~ [ 07 Altar ] ~ cc or Discord.gg/gsxynse%25%" + "\n");
			writer.write("We advertise verified hosts for free! Verify as a host at Discord.gg/gsxynse%25%" + "\n");
			writer.write("Want more xp/hr in prayer? Find bone runners at Discord.gg/gsxynse%25%" + "\n");
			writer.write("Unsure on how to host/bone run/train prayer efficiently? Join Discord.gg/gsxynse%25%" + "\n");
			writer.write("Want free host advertisement? Become a verified host at Discord.gg/gsxynse%25%");
			writer.close();
		} catch (IOException e) {
			Utils.writeErrorReport(e, 030);
		}
	}

	public static void updateConfigFile() {

		String[] linesToWriteToConfigFile = {"#RANDOM_BREAKS" + "\n",
				"[Length times must be entered in seconds]" + "\n",
				"BREAKS_HOURS_MIN=" + Settings.antibanSettings.getBreaks_per_hour_min() + "\n",
				"BREAKS_HOURS_MAX=" + Settings.antibanSettings.getBreaks_per_hour_max() + "\n",
				"BREAKS_LENGTH_MIN=" + Settings.antibanSettings.getBreak_length_min() / 1000 + "\n",
				"BREAKS_LENGTH_MAX=" + Settings.antibanSettings.getBreak_length_max() / 1000 + "\n",
				"\n",
				"#TYPING_SPEED" + "\n",
				"[Values must be entered in milliseconds]" + "\n",
				"TYPING_SPEED_MIN=" + Settings.antibanSettings.getType_speed_delay_min() + "\n",
				"TYPING_SPEED_MAX=" + Settings.antibanSettings.getType_speed_delay_max() + "\n",
				"\n",
				"#ADVERTISING_LINES_DELAY" + "\n",
				"[Values must be entered in seconds]" + "\n",
				"LINES_DELAY_MIN=" + Settings.antibanSettings.getDelay_time_delay_min() / 1000 + "\n",
				"LINES_DELAY_MAX=" + Settings.antibanSettings.getDelay_time_delay_max() / 1000 + "\n",
				"\n",
				"#STOP_TIMER" + "\n",
				"[Values must be entered in hours (1+)]" + "\n",
				"STOP_TIME=" + Settings.antibanSettings.getStop_timer_time() / 3600 + "\n",
				"\n",
				"#HOST_COUNT" + "\n",
				"[This is how many host spams occur for every 1 general spam (1+)]" + "\n",
				"HOST_COUNT=" + Settings.antibanSettings.getHostCount() + "\n",
				"\n",
				"#ADVERTISING_LINES" + "\n"};

		String[] communitySpamLinesToWriteToConfigFile = CentralPanel.communitySpamTexts.getText().split("\n");

		Utils.writeToConfigFile(linesToWriteToConfigFile, communitySpamLinesToWriteToConfigFile);
	}

	public static void writeToConfigFile(String[] lines, String[] communitySpamLines) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fakeConfigFile));
			for (String s : lines) {
				writer.write(s);
			}
			for (String s : communitySpamLines) {
				writer.write(s + "\n");
			}
			writer.close();
		} catch (IOException e) {
			Utils.writeErrorReport(e, 030);
		}
	}

	public static String[] readFromConfigFile() {
		String message = "";
		Scanner sc = null;
		try {
			sc = new Scanner(Utils.fakeConfigFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (!line.startsWith("[") && !line.startsWith("#") && !line.equals("")) {
				message += line;
				message += "\n";
			}
		}
		sc.close();

		String[] settings = message.split("\n");
		for (int i = 0; i < settings.length; i++) {
			settings[i] = settings[i].substring(settings[i].indexOf("=") + 1, settings[i].length());
		}

		return settings;
	}

	public static void openWebpage(String url) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(new URL(url).toURI());
			} catch (Exception e) {
				Utils.writeErrorReport(e, 982);
			}
		}
	}

	public static void writeErrorReport(Exception e, int errorCode) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString(); // stack trace as a string

		File desktopFile = new File(System.getProperty("user.home") + "/Desktop/Altar Typer Error " + System.currentTimeMillis() + ".txt");
		try {
			PrintWriter writer = new PrintWriter(desktopFile, "UTF-8");
			writer.println("Error Code: " + errorCode);
			writer.println(sStackTrace);
			writer.close();
			JOptionPane.showMessageDialog(null, "An error file has been generated on your desktop.\nPlease send it to the developer ASAP", "Altar Typer Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Writing to file error" + desktopFile, "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
