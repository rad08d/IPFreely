import java.io.IOException;
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
	

	public static Set<Client> LocalHost(){
		Set<Client> local = new HashSet<Client>();
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
					if(i.getHostAddress().startsWith("192", 0) || i.getHostAddress().startsWith("10")){
						Client c = new Client(i.getHostName(), i.getHostAddress());
						c.setIPAddressByteArr(i.getAddress());
						c.setMacAddress(sMac);
						local.add(c);
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return local;
	}
	
	public static Client SearchClient(byte[] ip){
		Client machine = null;
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
					//System.out.println("Host Name: " + machine.getHostName() + " IP Address: " + machine.getIPAddress() + " Machine Address: " + machine.getMacAddress());
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