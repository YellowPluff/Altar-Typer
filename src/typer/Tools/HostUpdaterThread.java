package typer.Tools;

import java.util.ArrayList;
import typer.Settings.CustomFrame;
import typer.Settings.Settings;
import typer.Tabs.CentralPanel;

public class HostUpdaterThread implements Runnable {

	private Thread worker;
	private boolean running;
	private int interval;

	public HostUpdaterThread() {
		interval = 30000;
		running = false;
	}

	public void start() {
		worker = new Thread(this);
		worker.start();
	}

	public void stop() {
		running = false;
	}

	public void run() {
		running = true;
		while (running) {
			Settings.allHosts = new ArrayList<Host>();	//Reset host list
			Website site = new Website();				//Grab new hosts for Rs3 and Osrs
			CustomFrame.updateStatus("Fetching Rs3 Hosts...");
			site.populateRs3Hosts();
			CustomFrame.updateStatus("Fetching OSRS Hosts...");
			site.populateOSRSHosts();
			if (CentralPanel.tableModel.getRowCount() > 0) {	//Remove old hosts from host table
				for (int i = CentralPanel.tableModel.getRowCount() - 1; i > -1; i--) {
					CentralPanel.tableModel.removeRow(i);
				}
			}
			for (Host h : Settings.allHosts) { //Add back in new hosts
				CentralPanel.tableModel.addRow(new Object[]{h.getServer(), h.getUsername(), h.getWorld(), h.getLocation()});
				if (CentralPanel.onlyVerifiedHosts.isSelected()) {
					if (h.getUsername().contains("*")) {
						CentralPanel.tableModel.removeRow(CentralPanel.tableModel.getRowCount() - 1);
					}
				}
			}
			CentralPanel.onlyVerifiedHosts.addActionListener(e -> {
				if (CentralPanel.tableModel.getRowCount() > 0) {	//Remove old hosts from host table
					for (int i = CentralPanel.tableModel.getRowCount() - 1; i > -1; i--) {
						CentralPanel.tableModel.removeRow(i);
					}
				}
				for (Host h : Settings.allHosts) {
					CentralPanel.tableModel.addRow(new Object[]{h.getServer(), h.getUsername(), h.getWorld(), h.getLocation()});
					if (CentralPanel.onlyVerifiedHosts.isSelected()) {
						if (h.getUsername().contains("*")) {
							CentralPanel.tableModel.removeRow(CentralPanel.tableModel.getRowCount() - 1);
						}
					}
				}
			});
			for (int i = interval; i > 0; i = i - 1000) {
				try { 
					if (i == 1000) {
						CustomFrame.updateStatus("Refreshing Hosts in " + i / 1000 + " second");
					}
					else {
						CustomFrame.updateStatus("Refreshing Hosts in " + i / 1000 + " seconds");
					}
					Thread.sleep(1000); 
				} catch (InterruptedException e){ 
					Thread.currentThread().interrupt();
					System.out.println("Thread was interrupted. Failed to complete operation");
				}
			}
		}
	}
}