import java.util.Set;

public class ThreadSearch implements Runnable{
		private Set<IClient> machines;
		private int ipStartRange;
		private int ipStopRange;
		private byte[] ipNet;
		static int progress;
		
		public ThreadSearch(){}
		public ThreadSearch(byte[] ipNet, int ipStartRange, int ipStopRange, Set<IClient> machines){
			this.ipStartRange = ipStartRange;
			this.ipStopRange = ipStopRange;
			this.ipNet = ipNet;
			this.machines = machines;
			ThreadSearch.progress = 0;
		}
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int i = ipStartRange; i <= ipStopRange; i++){
				byte[] fullIP = new byte[4];						//hard coded to an ipv4
				for(int x = 0; x < ipNet.length - 1; x++){
					fullIP[x] = ipNet[x];
					System.out.print(fullIP[x] & 0xFF);
				}
				fullIP[3] = (byte)i;
				int last = fullIP[3] & 0xFF;
				synchronized(this){
					ThreadSearch.progress++;
				}
				System.out.println(last + ": IPAddress searched from ThreadSearch : " + ThreadSearch.progress + " From Thread Name:  "+Thread.currentThread().getName());
				System.out.println("StartIP: " + this.ipStartRange + "  IPStopRange: " + this.ipStopRange + "  Thread Name: " + Thread.currentThread().getName());
				Client c = Client.SearchClient(fullIP);
				if(c != null){
					synchronized(this.machines){
						this.machines.add(c);
					}
				}
			}
		}
		
	}