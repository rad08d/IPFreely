import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.net.NetworkInterface;

public class Client implements IClient{
	
	private String hostName;
	private String ipAddress;
	private String macAddress;
	private byte[] ipAddressByteArr;
	//Constructors
	public Client(){
		this.hostName = "";
		this.ipAddress = "";
		this.macAddress = null;
	}
	
	public Client(String hostName, String ipAddress){
		this.hostName = hostName;
		this.ipAddress = ipAddress;
	}
	

	public static Client LocalHost(){
		Client local;
		try {
			Enumeration<NetworkInterface> nI = NetworkInterface.getNetworkInterfaces();
			while(nI.hasMoreElements()){
				NetworkInterface n = (NetworkInterface) nI.nextElement();
				NetworkInterface parentNic = n.getParent();
				StringBuilder sb = new StringBuilder();
				if(parentNic != null){
					byte[] mac = parentNic.getHardwareAddress();
					for(int i = 0; i< mac.length; i++){
						sb.append(String.format("%02x%s", mac[i], (i < mac.length -1) ? "-" : ""));
					}
					String sMac = sb.toString();
					System.out.println("Parent Mac: " + sMac);
				}
				byte[] mac = n.getHardwareAddress();
				if(mac != null){
					for(int i = 0; i< mac.length; i++){
						sb.append(String.format("%02x%s", mac[i], (i < mac.length -1) ? "-" : ""));
					}
				}
				String sMac = sb.toString();
				Enumeration e = n.getInetAddresses();
				while(e.hasMoreElements()){
					InetAddress i = (InetAddress) e.nextElement();
					if((i.getHostAddress().startsWith("192") || i.getHostAddress().startsWith("10") || i.getHostAddress().startsWith("172"))){
						local = new Client(i.getHostName(), i.getHostAddress());
						local.setIPAddressByteArr(i.getAddress());
						local.setMacAddress(sMac);
						return local;
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in Client.LocalHost(): " + e.toString());
		}
		return null;
	}
	
	public static Client SearchClient(byte[] ip){
		Client machine = null;
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < 4; i++){
				if(i == 3){
					sb.append(ip[i] & 0xFF);
				}
				else{
					sb.append((ip[i] & 0xFF) + ".");
				}
			}
			String sIP = sb.toString();
			System.out.println("Searching IP Address: " + sIP);
			Runtime r = Runtime.getRuntime();
			try {
				Process p = r.exec("ping -n 1 -w 300 " + sIP);
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				StringBuilder sBuild = new StringBuilder();
				String resp;
				while((resp = reader.readLine()) != null){
					sBuild.append(resp);
				}
				String reply = sBuild.toString();
				System.out.println(System.getProperty("os.name") + " " + reply);
				if(reply.toLowerCase().contains("reply")){
					System.out.println(sIP + " reply!");
					machine = new Client(sIP, sIP);
					return machine;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(System.getProperty("os.name") + " Error in ping process!");
			}
		}
			try {
				InetAddress inet = InetAddress.getByAddress(ip);
				NetworkInterface nI = NetworkInterface.getByInetAddress(inet);
				if(inet.isReachable(300)){
					machine = new Client(inet.getHostName(), inet.getHostAddress());
					if(nI != null){
						NetworkInterface parentNic = nI.getParent();
						StringBuilder sb = new StringBuilder();
						if(parentNic != null){
							byte[] mac = parentNic.getHardwareAddress();
							for(int i = 0; i< mac.length; i++){
								sb.append(String.format("%02x%s", mac[i], (i < mac.length -1) ? "-" : ""));
							}
							String sMac = sb.toString();
							System.out.println("Parent Mac: " + sMac);
						}
						byte[] mac = nI.getHardwareAddress();
						for(int i = 0; i< mac.length; i++){
							sb.append(String.format("%02x%s", mac[i], (i < mac.length -1) ? "-" : ""));
						}
						String sMac = sb.toString();
						machine.setMacAddress(sMac);
					}
					System.out.println("Host Name: " + machine.getHostName() + " IP Address: " + machine.getIPAddress() + " Machine Address: " + machine.getMacAddress());
				}
				return machine;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.out.println("Client Search Mehod Exception--UnKnownHostException: " + e.toString());
				return machine;
			}catch (IOException ioe){
				System.out.println("Client Search Mehod Exception--IOException: " + ioe.toString());
				return machine;
			}
		}
	
	//Setters
	public void setIPAddress(String ip){
		this.ipAddress = ip;
	}
	
	public void setIPAddressByteArr(byte[] ip){
		this.ipAddressByteArr = ip;
	}
	
	public void setHostName(String hostName){
		this.hostName = hostName;
	}
	public void setMacAddress(String mac){
		this.macAddress = mac;
	}
	
	//Getters
	public String getHostName(){
		return this.hostName;
	}
	
	public String getIPAddress(){
		return this.ipAddress;
	}
	
	public byte[] getIPAddressByteArr(){
		return this.ipAddressByteArr;
	}
	
	public String getMacAddress(){
		return this.macAddress;
	}
	
	

}