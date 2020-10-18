package typer.Tabs;

import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import typer.Listeners.MenuBarListener;

@SuppressWarnings("serial")
public class MenuBarPanel extends JPanel {

	private JMenuBar menuBar;
	//---
	private JMenu exampleMenu;
	private JMenuItem actionOne, actionTwo, actionThree, actionFour;
	//private JMenu subMenuExample;
	//private JMenuItem subMenuItemExampleTwo;


	public MenuBarPanel() {
		setLayout(new BorderLayout());
		menuBar = new JMenuBar();

		exampleMenu();

		add(menuBar, BorderLayout.CENTER);
	}

	private void exampleMenu() {
		exampleMenu = new JMenu("Config File");
		menuBar.add(exampleMenu);

		actionOne = new JMenuItem("New");
		actionOne.addActionListener(new MenuBarListener());
		exampleMenu.add(actionOne);

		actionTwo = new JMenuItem("Open");
		actionTwo.addActionListener(new MenuBarListener());
		exampleMenu.add(actionTwo);

		actionThree = new JMenuItem("Apply Changes");
		actionThree.addActionListener(new MenuBarListener());
		exampleMenu.add(actionThree);

		actionFour = new JMenuItem("Don't Click This");
		actionFour.addActionListener(new MenuBarListener());
		exampleMenu.add(actionFour);
		/*
		subMenuExample = new JMenu("See More Example");
		exampleMenu.add(subMenuExample);

		subMenuItemExampleTwo = new JMenuItem("Sub Menu Item");
		subMenuExample.add(subMenuItemExampleTwo);
		 */
	}
}
