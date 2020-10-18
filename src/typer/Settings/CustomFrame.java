package typer.Settings;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import typer.Tabs.*;

@SuppressWarnings("serial")
public class CustomFrame extends JFrame {

	public static JLabel status;

	public CustomFrame() {
		super(Settings.FRAME_TITLE);
		setSize(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setAlwaysOnTop(true);
		StatusText();
		MenuPanel();
		CentralPanel();
		revalidate();
		repaint();

	}

	private void MenuPanel() {
		MenuBarPanel menuBar = new MenuBarPanel();
		add(menuBar, BorderLayout.NORTH);
	}

	private void CentralPanel() {
		CentralPanel window = new CentralPanel();
		add(window, BorderLayout.CENTER);
	}

	private void StatusText() {
		status = new JLabel("Loading...");
		status.setHorizontalAlignment(SwingConstants.RIGHT);
		add(status, BorderLayout.SOUTH);
	}

	public static void updateStatus(String text) {
		status.setText(text);
	}
}
