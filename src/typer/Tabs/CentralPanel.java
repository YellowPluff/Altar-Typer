package typer.Tabs;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import typer.Listeners.ButtonListener;
import typer.Settings.Settings;
import typer.Tools.Utils;

@SuppressWarnings("serial")
public class CentralPanel extends JPanel {

	public static DefaultTableModel tableModel;
	public static JTable hostTable;
	public static JLabel hostLabel;
	public static JButton startTyping, stopTyping;
	public static JComboBox<String> serverType;
	public static JComboBox<String> colour;
	public static JComboBox<String> effect;
	public static JTextArea communitySpamTexts;
	public static JRadioButton randomBreaks, staticBreaks;
	public static JRadioButton randomTypeSpeed, staticTypeSpeed;
	public static JRadioButton randomDelay, staticDelay;
	public static JCheckBox onlyVerifiedHosts;
	public static JCheckBox useColours;
	public static JCheckBox pinToTop;
	public static JCheckBox onlyHostAdvertisements, onlyCommunityAdvertisements;
	public static JScrollPane communitySpamScroller, scrollPane;
	boolean selection = false;

	public CentralPanel() {
		setLayout(null);
		RevisionLabel();
		HostLabel();
		BlockList();
		SeeConfigs();
		HostDisplayPanel();
		AlwaysOnTop();
		OnlyVerifiedHosts();
		UseColours();
		Colours();
		Effects();
		ShowSpams();
		ShowHostAdverts();
		ShowCommunityAdverts();
		StartButton();
	}

	private void UseColours() {
		useColours = new JCheckBox("Use Random Colours");
		useColours.setToolTipText("Tick this checkbox to use random colours when advertising the hosts listed above");
		useColours.setBounds(5, 240, 150, 15);
		useColours.setSelected(true);
		useColours.addActionListener(e -> {
			if (useColours.isSelected()) {
				colour.setEnabled(false);
				effect.setEnabled(false);
			}
			else {
				colour.setEnabled(true);
				effect.setEnabled(true);
			}
		});
		add(useColours);
	}

	private void Colours() {
		String[] colours = {"Yellow", "Purple", "Red", "Green", "White", "Cyan", "Flash1", "Flash2", "Flash3", "Glow1", "Glow2", "Glow3"};
		colour = new JComboBox<String>(colours);
		colour.setBounds(5, 260, 180, 20);
		if (useColours.isSelected()) {
			colour.setEnabled(false);
		}
		add(colour);
	}

	private void Effects() {
		String[] effects = {"No Effect", "Wave", "Wave2", "Scroll", "Slide", "Shake"};
		effect = new JComboBox<String>(effects);
		effect.setBounds(190, 260, 180, 20);
		if (useColours.isSelected()) {
			effect.setEnabled(false);
		}
		add(effect);

	}

	private void OnlyVerifiedHosts() {
		onlyVerifiedHosts = new JCheckBox("Only Verified Hosts");
		onlyVerifiedHosts.setToolTipText("Tick this checkbox to advertise verified hosts only");
		onlyVerifiedHosts.setBounds(5, 220, 180, 15);
		//onlyVerifiedHosts.setSelected(true);
		add(onlyVerifiedHosts);
	}

	private void AlwaysOnTop() {
		pinToTop = new JCheckBox("Pin To Top");
		pinToTop.setToolTipText("Tick this checkbox to keep this program on top of other programs");
		pinToTop.setBounds(5, 200, 100, 15);
		pinToTop.addActionListener(e -> {
			if (pinToTop.isSelected()) {
				Settings.frame.setAlwaysOnTop(true);
			}
			else {
				Settings.frame.setAlwaysOnTop(false);
			}
		});
		add(pinToTop);
	}

	private void ShowSpams() {
		communitySpamTexts = new JTextArea(Settings.communityMessages);
		//		spamTexts.setEditable(true);
		communitySpamTexts.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				Utils.updateConfigFile();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				Utils.updateConfigFile();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				Utils.updateConfigFile();
			}
		});

		communitySpamScroller = new JScrollPane(communitySpamTexts);
		communitySpamScroller.setBounds(5, 285, 570, 150);
		add(communitySpamScroller);
	}

	private void ShowHostAdverts() {
		onlyHostAdvertisements = new JCheckBox("Advertise Tracker Hosts");
		onlyHostAdvertisements.setToolTipText("Tick this checkbox to advertise the hosts listed above");
		onlyHostAdvertisements.setBounds(186, 200, 165, 15);
		onlyHostAdvertisements.setSelected(true);
		onlyHostAdvertisements.addActionListener(e -> {
			if (onlyHostAdvertisements.isSelected()) {
				hostLabel.setVisible(true);
				scrollPane.setVisible(true);
			}
			else {
				hostLabel.setVisible(false);
				scrollPane.setVisible(false);
			}
		});
		add(onlyHostAdvertisements);
	}

	private void ShowCommunityAdverts() {
		onlyCommunityAdvertisements = new JCheckBox("Advertise Community Lines");
		onlyCommunityAdvertisements.setToolTipText("Tick this checkbox to advertise the community advertisements");
		onlyCommunityAdvertisements.setBounds(186, 220, 180, 15);
		onlyCommunityAdvertisements.setSelected(true);
		onlyCommunityAdvertisements.addActionListener(e -> {
			if (onlyCommunityAdvertisements.isSelected()) {
				communitySpamScroller.setVisible(true);
			}
			else {
				communitySpamScroller.setVisible(false);
			}
		});
		add(onlyCommunityAdvertisements);
	}

	private void StartButton() {
		String[] servers = {"RS3 - 31 - Taverley", "RS3 - 31 - Yanille", "OSRS - 330 - Rimmington", "OSRS - 331 - Rimmington", "OSRS - 465 - Rimmington", "OSRS - 512 - Rimmington", "OSRS - 330 - Yanille", "OSRS - 331 - Yanille", "OSRS - 465 - Yanille", "OSRS - 512 - Yanille"};
		serverType = new JComboBox<String>(servers);
		serverType.setBounds(5, 440, 180, 30);
		if (serverType.getSelectedItem().toString().contains("RS3") && selection == false) {
			communitySpamTexts.setText(communitySpamTexts.getText().replace("[ 07 Altar ]", "[ Altar ]"));
			communitySpamTexts.setText(communitySpamTexts.getText().replace("Clan", "Friends"));
			communitySpamTexts.setText(communitySpamTexts.getText().replace("cc", "fc"));
		}
		if (serverType.getSelectedItem().toString().contains("OSRS") && selection == false) {
			communitySpamTexts.setText(communitySpamTexts.getText().replace("[ Altar ]", "[ 07 Altar ]"));
			communitySpamTexts.setText(communitySpamTexts.getText().replace("Friends", "Clan"));
			communitySpamTexts.setText(communitySpamTexts.getText().replace("fc", "cc"));
		}
		serverType.addActionListener(e -> {
			if (serverType.getSelectedItem().toString().contains("RS3")) {
				selection = true;
				communitySpamTexts.setText(communitySpamTexts.getText().replace("[ 07 Altar ]", "[ Altar ]"));
				communitySpamTexts.setText(communitySpamTexts.getText().replace("Clan", "Friends"));
				communitySpamTexts.setText(communitySpamTexts.getText().replace("cc", "fc"));
			}
			if (serverType.getSelectedItem().toString().contains("OSRS")) {
				selection = true;
				communitySpamTexts.setText(communitySpamTexts.getText().replace("[ Altar ]", "[ 07 Altar ]"));
				communitySpamTexts.setText(communitySpamTexts.getText().replace("Friends", "Clan"));
				communitySpamTexts.setText(communitySpamTexts.getText().replace("fc", "cc"));
			}
		});
		add(serverType);
		startTyping = new JButton("Start Typing");
		startTyping.setBounds(190, 440, 140, 30);
		startTyping.addActionListener(new ButtonListener());
		add(startTyping);

		stopTyping = new JButton("Stop Typing");
		stopTyping.setEnabled(false);
		stopTyping.setBounds(340, 440, 220, 30);
		stopTyping.addActionListener(new ButtonListener());
		add(stopTyping);

	}

	private void RevisionLabel() {
		JLabel revisionLabel = new JLabel("Version: " + Settings.revision);
		revisionLabel.setBounds(200, 5, 200, 30);
		add(revisionLabel);
	}

	private void HostLabel() {
		hostLabel = new JLabel("Currently Active Hosts");
		hostLabel.setBounds(5, 5, 300, 30);
		add(hostLabel);
	}

	private void BlockList() {
		JButton blockList = new JButton("Black List");
		blockList.setBounds(360, 5, 100, 20);
		blockList.addActionListener(e -> {
			String message = "The Black List is loading. Try again later";
			int listSize = 1;
			for (String name : Settings.BLOCKED_LIST) {
				if (name.equals("null")) {
					message = "The Black List is empty";
				}
				else {
					if (listSize == 1) {
						message = "Names:" + "\n";
					}
					message += listSize++ + ". " + name + "\n";
				}
			}
			JOptionPane.showMessageDialog(Settings.frame, message, "Black List", JOptionPane.INFORMATION_MESSAGE);
		});
		add(blockList);
	}

	private void SeeConfigs() {
		JButton seeConfigs = new JButton("Configs");
		seeConfigs.setBounds(465, 5, 100, 20);
		seeConfigs.addActionListener(new ButtonListener());
		add(seeConfigs);
	}

	private void HostDisplayPanel() {
		tableModel = new DefaultTableModel();
		String[] headers = {"Server", "Username", "World", "Location"};
		for (String s : headers) {
			tableModel.addColumn(s);
		}
		hostTable = new JTable(tableModel);
		hostTable.setEnabled(false);
		hostTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane = new JScrollPane(hostTable);
		scrollPane.setBounds(5, 40, 570, 150);
		System.out.println();
		add(scrollPane);
	}
}