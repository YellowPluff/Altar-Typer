package typer.Tools;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import typer.Settings.Settings;

public class RobotClass {

	//This class should never crash, but the instance it does... Throw AWTException
	public static void typeCharacter(char c) {
		try {
			Robot bot = new Robot();
			//bot.setAutoDelay(10); //TODO
			if (Settings.shiftCases.contains(c)) {
				bot.keyPress(KeyEvent.VK_SHIFT);
				bot.delay(35);
			}
			int keyStroke = Settings.mapStrokes.get(Character.toLowerCase(c));
			try {
				bot.keyPress(keyStroke);
				bot.keyRelease(keyStroke);
			} catch (IllegalArgumentException e1) {
				System.out.println("Something went wrong with the key press/release functionality" + keyStroke);
				Utils.writeErrorReport(e1, 050);
			}
			if (Settings.shiftCases.contains(c)) {
				bot.delay(35);
				bot.keyRelease(KeyEvent.VK_SHIFT);
			}
		} catch (AWTException e) {
			Utils.writeErrorReport(e, 046);
		}
	}

	public static void pressEnter() {
		try {
			Robot bot = new Robot();
			bot.delay(200);
			bot.keyPress(KeyEvent.VK_ENTER);
			bot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			Utils.writeErrorReport(e, 10);
		}
	}

	public static void releaseShift() {
		try {
			Robot releaseBot = new Robot();
			releaseBot.keyRelease(KeyEvent.VK_SHIFT);
		} catch (AWTException e) {
			Utils.writeErrorReport(e, 10);
		}
	}
}
