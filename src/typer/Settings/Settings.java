package typer.Settings;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import typer.Tools.AntibanSettings;
import typer.Tools.Host;
import typer.Tools.HostUpdaterThread;
import typer.Tools.Utils;

/**
 * Copyright (C) © 2008 - 2020 Altar Community - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential Written by Dakota Nickel <loyalmodding@gmail.com>
 *
 * Function Advertising the clan and website to gain users Version: v1.4.7
 * [updated 13/10/2020 (MM/DD/YYYY)] Requirements: Latest version of Java
 * (Java.com) Author Dakota @ discord Im Dakota#5882
 */

public class Settings {

	public static final int FRAME_WIDTH = 610;
	public static final int FRAME_HEIGHT = 560;
	public static final String FRAME_TITLE = "Altar Tool";
	public static final String ALTAR_TEXTFILE_LINK = "http://altar.rs/altartracker.txt";
	public static final String BLOCK_LIST_LINK = "https://www.altar.rs/HHT/typerblacklist.txt";
	public static final String HHT_OVERRIDE_LINK = "https://www.altar.rs/HHT/typeroverridelist.txt";
	public static final String revision = "1.4.7";
	public static AntibanSettings antibanSettings;
	public static ArrayList<Character> shiftCases;
	public static Map<Character, Integer> mapStrokes;
	public static ArrayList<Host> allHosts;
	public static String communityMessages;
	public static ArrayList<String> BLOCKED_LIST = new ArrayList<String>();
	public static ArrayList<Host> OVERRIDE_LIST;

	public static JFrame frame;
	public static void main(String[] args) {

		try {	//This line is especially important on mac because it allows it to look like the windows version
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, "layout not supported");
			System.exit(0);
		}

		initializeShiftCases();
		if (!Utils.fakeConfigFile.exists()) {
			Utils.makeConfigFile();
			JOptionPane.showMessageDialog(null, "Config file created\n" + Utils.fakeConfigFile, "Config File", JOptionPane.INFORMATION_MESSAGE);
		}
		initializeAntibanSettings();
		allHosts = new ArrayList<Host>();
		frame = new CustomFrame();

		System.setProperty("http.agent", "Chrome");
		HostUpdaterThread grabHosts = new HostUpdaterThread();
		grabHosts.start();

	}

	public static void initializeAntibanSettings() {
		/*if (fakeConfigFile.exists()) {
			fakeConfigFile.delete();
		  }
		 */
		/**
		 * Right here I have to check if that file exists, and if it doesn't then I have to make it
		 */
		antibanSettings = new AntibanSettings();
		String[] settings = Utils.readFromConfigFile();
		try {
			antibanSettings.setBreaks_per_hour_min(Integer.parseInt(settings[0]));
			antibanSettings.setBreaks_per_hour_max(Integer.parseInt(settings[1]));
			antibanSettings.setBreak_length_min(Integer.parseInt(settings[2]) * 1000);
			antibanSettings.setBreak_length_max(Integer.parseInt(settings[3]) * 1000);
			antibanSettings.setType_speed_delay_min(Integer.parseInt(settings[4]));
			antibanSettings.setType_speed_delay_max(Integer.parseInt(settings[5]));
			antibanSettings.setDelay_time_delay_min(Integer.parseInt(settings[6]) * 1000);
			antibanSettings.setDelay_time_delay_max(Integer.parseInt(settings[7]) * 1000);
			antibanSettings.setStop_timer_time(Integer.parseInt(settings[8]) * 3600);
			antibanSettings.setHostCount(Integer.parseInt(settings[9]));
			Settings.communityMessages = settings[10]; //stops the first line saying 'null' when opening the program
			for (int i = 11; i < settings.length; i++) {
				Settings.communityMessages += "\n" + settings[i];
			}
			if (Integer.parseInt(settings[0]) < 0 || Integer.parseInt(settings[1]) < 0) {
				JOptionPane.showMessageDialog(null, "BREAKS_HOURS_MIN and BREAKS_HOURS_MAX must be greater than or equal to 0 in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			else if (Integer.parseInt(settings[0]) > Integer.parseInt(settings[1])) {
				JOptionPane.showMessageDialog(null, "BREAKS_HOURS_MIN must be lower than or equal to BREAKS_HOURS_MAX in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			if (Integer.parseInt(settings[2]) < 0 || Integer.parseInt(settings[3]) < 0) {
				JOptionPane.showMessageDialog(null, "BREAKS_LENGTH_MIN and BREAKS_LENGTH_MAX must be greater than or equal to 0 in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			else if (Integer.parseInt(settings[2]) > Integer.parseInt(settings[3])) {
				JOptionPane.showMessageDialog(null, "BREAKS_LENGTH_MIN must be lower than or equal to BREAKS_LENGTH_MAX in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			if (Integer.parseInt(settings[4]) < 0 || Integer.parseInt(settings[5]) < 0) {
				JOptionPane.showMessageDialog(null, "TYPING_SPEED_MIN and TYPING_SPEED_MAX must be greater than or equal to 0 in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			else if (Integer.parseInt(settings[4]) > Integer.parseInt(settings[5])) {
				JOptionPane.showMessageDialog(null, "TYPING_SPEED_MIN must be lower than or equal to TYPING_SPEED_MAX in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			if (Integer.parseInt(settings[6]) < 0 || Integer.parseInt(settings[7]) < 0) {
				JOptionPane.showMessageDialog(null, "LINES_DELAY_MIN and LINES_DELAY_MAX must be greater than or equal to 0 in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			else if (Integer.parseInt(settings[6]) > Integer.parseInt(settings[7])) {
				JOptionPane.showMessageDialog(null, "LINES_DELAY_MIN must be lower than or equal to LINES_DELAY_MAX in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			if (Integer.parseInt(settings[8]) == 0) {
				JOptionPane.showMessageDialog(null, "STOP_TIME must be greater than 0 in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			if (Integer.parseInt(settings[9]) == 0) {
				JOptionPane.showMessageDialog(null, "HOST_COUNT must be greater than 0 in the config file.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "There was a problem reading the config file. Delete it and restart the program.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private static void initializeShiftCases() {
		char[] shiftC = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '{', '}', '|', ':', '<', '>', '?', '+', '"'};
		shiftCases = new ArrayList<Character>();
		for (char c : shiftC) {
			shiftCases.add(c);
		}
		mapStrokes = new HashMap<>();
		mapStrokes.put('a', KeyEvent.VK_A);
		mapStrokes.put('b',  KeyEvent.VK_B);
		mapStrokes.put('c',  KeyEvent.VK_C);
		mapStrokes.put('d',  KeyEvent.VK_D);
		mapStrokes.put('e',  KeyEvent.VK_E);
		mapStrokes.put('f',  KeyEvent.VK_F);
		mapStrokes.put('g',  KeyEvent.VK_G);
		mapStrokes.put('h',  KeyEvent.VK_H);
		mapStrokes.put('i',  KeyEvent.VK_I);
		mapStrokes.put('j',  KeyEvent.VK_J);
		mapStrokes.put('k',  KeyEvent.VK_K);
		mapStrokes.put('l',  KeyEvent.VK_L);
		mapStrokes.put('m',  KeyEvent.VK_M);
		mapStrokes.put('n',  KeyEvent.VK_N);
		mapStrokes.put('o',  KeyEvent.VK_O);
		mapStrokes.put('p',  KeyEvent.VK_P);
		mapStrokes.put('q',  KeyEvent.VK_Q);
		mapStrokes.put('r',  KeyEvent.VK_R);
		mapStrokes.put('s',  KeyEvent.VK_S);
		mapStrokes.put('t',  KeyEvent.VK_T);
		mapStrokes.put('u',  KeyEvent.VK_U);
		mapStrokes.put('v',  KeyEvent.VK_V);
		mapStrokes.put('w',  KeyEvent.VK_W);
		mapStrokes.put('x',  KeyEvent.VK_X);
		mapStrokes.put('y',  KeyEvent.VK_Y);
		mapStrokes.put('z',  KeyEvent.VK_Z);
		mapStrokes.put('`', KeyEvent.VK_BACK_QUOTE);
		mapStrokes.put('0', KeyEvent.VK_0);
		mapStrokes.put('1', KeyEvent.VK_1);
		mapStrokes.put('2', KeyEvent.VK_2);
		mapStrokes.put('3', KeyEvent.VK_3);
		mapStrokes.put('4', KeyEvent.VK_4);
		mapStrokes.put('5', KeyEvent.VK_5);
		mapStrokes.put('6', KeyEvent.VK_6);
		mapStrokes.put('7', KeyEvent.VK_7);
		mapStrokes.put('8', KeyEvent.VK_8);
		mapStrokes.put('9', KeyEvent.VK_9);
		mapStrokes.put('-', KeyEvent.VK_MINUS);
		mapStrokes.put('=', KeyEvent.VK_EQUALS);
		mapStrokes.put('\t', KeyEvent.VK_TAB);
		mapStrokes.put('\n', KeyEvent.VK_ENTER);
		mapStrokes.put('[', KeyEvent.VK_OPEN_BRACKET);
		mapStrokes.put(']', KeyEvent.VK_CLOSE_BRACKET);
		mapStrokes.put('\\', KeyEvent.VK_BACK_SLASH);
		mapStrokes.put(';', KeyEvent.VK_SEMICOLON);
		mapStrokes.put('\'', KeyEvent.VK_QUOTE);
		mapStrokes.put('\"', KeyEvent.VK_QUOTEDBL);
		mapStrokes.put(',', KeyEvent.VK_COMMA);
		mapStrokes.put('.', KeyEvent.VK_PERIOD);
		mapStrokes.put('/', KeyEvent.VK_SLASH);
		mapStrokes.put(' ', KeyEvent.VK_SPACE);
		//All the items that require shift are below
		mapStrokes.put('~', KeyEvent.VK_BACK_QUOTE);
		mapStrokes.put('!', KeyEvent.VK_1);
		mapStrokes.put('@', KeyEvent.VK_2);
		mapStrokes.put('#', KeyEvent.VK_3);
		mapStrokes.put('$', KeyEvent.VK_4);
		mapStrokes.put('%', KeyEvent.VK_5);
		mapStrokes.put('^', KeyEvent.VK_6);
		mapStrokes.put('&', KeyEvent.VK_7);
		mapStrokes.put('*', KeyEvent.VK_8);
		mapStrokes.put('(', KeyEvent.VK_9);
		mapStrokes.put(')', KeyEvent.VK_0);
		mapStrokes.put('_', KeyEvent.VK_MINUS);
		mapStrokes.put('{', KeyEvent.VK_OPEN_BRACKET);
		mapStrokes.put('}', KeyEvent.VK_CLOSE_BRACKET);
		mapStrokes.put('|', KeyEvent.VK_BACK_SLASH);
		mapStrokes.put(':', KeyEvent.VK_SEMICOLON);
		mapStrokes.put('<', KeyEvent.VK_COMMA);
		mapStrokes.put('>', KeyEvent.VK_PERIOD);
		mapStrokes.put('?', KeyEvent.VK_SLASH);
		mapStrokes.put('+', KeyEvent.VK_EQUALS);
		mapStrokes.put('"', KeyEvent.VK_QUOTE);
		//-----------------
	}
}
