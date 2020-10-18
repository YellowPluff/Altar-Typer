package typer.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import typer.Settings.CustomFrame;
import typer.Settings.Settings;

public class Website {

		private String websiteData_Hosts;

		public Website() {
			String status = "Try again";
			int tryCount = 0;
			while (status.equals("Try again")) {
				try {
					URL url = new URL(Settings.ALTAR_TEXTFILE_LINK);
					BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
					String line;
					while ((line = reader.readLine()) != null) {
						websiteData_Hosts += " " + line;
						
					}
					reader.close();
					if (!websiteData_Hosts.contains("RS3 Hosts")) {
						websiteData_Hosts = "";
					}
					else {
						status = "Stop";
					}
				} catch (IOException e) {
					tryCount++;
					CustomFrame.updateStatus("Connection Failed... Attempting to reconnect... " + tryCount + " ");
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					//				if (tryCount >= 7)
					//					break;
				}
			}

			String websiteData_BlockedHosts = getWebsiteData(Settings.BLOCK_LIST_LINK, "#Start_Block");
			websiteData_BlockedHosts = websiteData_BlockedHosts.substring(websiteData_BlockedHosts.indexOf("#Start_Block"), websiteData_BlockedHosts.indexOf("#End_Block"));
			websiteData_BlockedHosts = websiteData_BlockedHosts.substring("#Start_Block".length() + 1, websiteData_BlockedHosts.length());
			websiteData_BlockedHosts = websiteData_BlockedHosts.trim();
			Settings.BLOCKED_LIST = new ArrayList<String>();
			String[] blocked_array = websiteData_BlockedHosts.split(", ");
			for (String s : blocked_array) {
				Settings.BLOCKED_LIST.add(s.toLowerCase());
			}
			String websiteData_OverrideList = getWebsiteData(Settings.HHT_OVERRIDE_LINK, "#Start_Override_Host_List");
			websiteData_OverrideList = websiteData_OverrideList.substring(websiteData_OverrideList.indexOf("#Start_Override_Host_List"), websiteData_OverrideList.indexOf("#End_Override_Host_List"));
			websiteData_OverrideList = websiteData_OverrideList.substring("#Start_Override_Host_List".length() + 1, websiteData_OverrideList.length());
			websiteData_OverrideList = websiteData_OverrideList.trim();
			Settings.OVERRIDE_LIST = new ArrayList<Host>();
			if (websiteData_OverrideList.toLowerCase().equals("null")) {
				Settings.OVERRIDE_LIST.add(new Host("na", "null", "na", "na"));
			}
			else {
				String[] override_array = websiteData_OverrideList.split(",, ");
				for (int i = 0; i < override_array.length; i++) {
					String hostString = override_array[i];
					String[] hostData = hostString.split(", ");
					Settings.OVERRIDE_LIST.add(new Host(hostData[0].toUpperCase(), hostData[1], hostData[2], hostData[3]));
				}
			}
		}

		private String getWebsiteData(String link, String text) {
			String status = "Try again";
			String websiteData = "";
			while (status.equals("Try again")) {
				try {
					URL url = new URL(link);
					BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
					String line;
					while ((line = reader.readLine()) != null) {
						websiteData += " " + line;
					}
					reader.close();
					if (!websiteData.contains(text)) {
						websiteData = "";
					}
					else {
						status = "stop";
					}
				} catch (IOException e) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			return websiteData;
		}

		//	private void iniSecHo(String website_data) {
		//		
		//		website_data = website_data.substring(website_data.indexOf("#Start_Override_Host_List"), website_data.indexOf("#End_Override_Host_List"));
		//		website_data = website_data.substring("#Start_Override_Host_List".length() + 7, website_data.length());
		//		website_data = website_data.replaceAll("<br />", "");
		//		website_data = website_data.trim();
		//		
		//		Settings.SECRET_HOST_LIST = new ArrayList<Host>();
		//		if (website_data.equals("null"))
		//			Settings.SECRET_HOST_LIST.add(new Host("na", "null", "na", "na"));
		//		else {
		//			String[] array = website_data.split(",, ");
		//			
		//			for (int i = 0; i < array.length; i++) {
		//				String hostString = array[i];
		//				String[] hostData = hostString.split(", ");
		//				Settings.SECRET_HOST_LIST.add(new Host(hostData[0].toUpperCase(), hostData[1], hostData[2], hostData[3]));
		//			}
		//		}
		//		
		//	}

		public void populateRs3Hosts() {
			String webString = this.websiteData_Hosts;
			if (webString != null) {
				try {
					webString = webString.substring(webString.indexOf("RS3"), webString.length());
					webString = webString.substring(webString.indexOf('[') + 1, webString.indexOf(']'));
					if (webString.contains("}, {")) {
						webString = webString.replace("}, {", "\n");
					}
					webString = webString.substring(1, webString.length() - 1);
					String[] datas = webString.split("\n");
					for (int i = 0; i < datas.length; i++) {	//This will loop through every single rs3 host
						String currentLine = datas[i];
						currentLine = currentLine.replace(", ", " ");
						currentLine = currentLine.replace("\"loc\": ", "\"loc\":");
						currentLine = currentLine.replace("\"World\": ", "\"World\":");
						currentLine = currentLine.replace("\"Username\": ", "\"Username\":");
						currentLine = currentLine.replace("\"Username\":\"", "\"Username:");
						currentLine = currentLine.replace("\"loc\":\"", "\"loc:");
						currentLine = currentLine.replace("\"World\":", "\"World:");
						currentLine = currentLine.replaceAll("\" \"", "\"\"");
						currentLine = currentLine.replace(" \"", "\"\"");
						if (!currentLine.substring(currentLine.length() - 1, currentLine.length()).equals("\"")) {
							currentLine = currentLine + "\"";
						}
						currentLine = currentLine.replaceAll(" ", "_");
						currentLine = currentLine.replace("\"\"", "\n");
						currentLine = currentLine.substring(1, currentLine.length() - 1);
						currentLine = currentLine.replaceAll("_", " ");
						String[] rs3SubData = currentLine.split("\n");
						String[] rs3HostData = new String[3];
						for (int j = 0; j < rs3SubData.length; j++) {
							if (rs3SubData[j].contains("Username:")) {
								rs3HostData[0] = rs3SubData[j].replace("Username:", "");
							}
							if (rs3SubData[j].contains("World:")) {
								rs3HostData[1] = rs3SubData[j].replace("World:", "");
							}
							if (rs3SubData[j].contains("loc:")) {
								rs3HostData[2] = rs3SubData[j].replace("loc:", "");
							}
						}
						Settings.allHosts.add(new Host("RS3", rs3HostData[0], rs3HostData[1], rs3HostData[2]));
					}
				} catch (Exception e) {
					//			e.printStackTrace();
					System.err.println("There are currently no open RS3 Hosts");
				}
			}

		}

		public void populateOSRSHosts() {
			String webString = this.websiteData_Hosts;
			if (webString != null) {
				try {
					webString = webString.substring(webString.indexOf("OSRS"), webString.length());
					webString = webString.substring(webString.indexOf('[') + 1, webString.indexOf(']'));
					if (webString.contains("}, {")) { 
						webString = webString.replace("}, {", "\n");
					}
					webString = webString.substring(1, webString.length() - 1);
					String[] datas = webString.split("\n");
					for (int i = 0; i < datas.length; i++) {	//This will loop through every single osrs host
						String currentLine = datas[i];
						currentLine = currentLine.replace(", ", " ");
						currentLine = currentLine.replace("\"loc\": ", "\"loc\":");
						currentLine = currentLine.replace("\"World\": ", "\"World\":");
						currentLine = currentLine.replace("\"Username\": ", "\"Username\":");
						currentLine = currentLine.replace("\"Username\":\"", "\"Username:");
						currentLine = currentLine.replace("\"loc\":\"", "\"loc:");
						currentLine = currentLine.replace("\"World\":", "\"World:");
						currentLine = currentLine.replaceAll("\" \"", "\"\"");
						currentLine = currentLine.replace(" \"", "\"\"");
						if (!currentLine.substring(currentLine.length() - 1, currentLine.length()).equals("\"")) {
							currentLine = currentLine + "\"";
						}
						currentLine = currentLine.replaceAll(" ", "_");
						currentLine = currentLine.replace("\"\"", "\n");
						currentLine = currentLine.substring(1, currentLine.length() - 1);
						currentLine = currentLine.replaceAll("_", " ");
						String[] osrsSubData = currentLine.split("\n");
						String[] osrsHostData = new String[3];
						for (int j = 0; j < osrsSubData.length; j++) {
							if (osrsSubData[j].contains("Username:")) {
								osrsHostData[0] = osrsSubData[j].replace("Username:", "");
							}
							if (osrsSubData[j].contains("World:")) {
								osrsHostData[1] = osrsSubData[j].replace("World:", "");
							}
							if (osrsSubData[j].contains("loc:")) {
								osrsHostData[2] = osrsSubData[j].replace("loc:", "");
							}
						}
						Settings.allHosts.add(new Host("OSRS", osrsHostData[0], osrsHostData[1], osrsHostData[2]));
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("There are currently no open OSRS Hosts");
				}
			}
		}
	}