import java.util.HashSet;
import java.util.Set;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingWorker;

public class OneTimeSearch extends SwingWorker<Void, Integer>{
	private Set<IClient> machines;
	private final JProgressBar jpb;
	private JTable jt;
	private byte[] ipNet;
	public OneTimeSearch(JProgressBar jpb, JTable jt, byte[] ipNet){
		this.jpb = jpb;
		this.jt = jt;
		this.machines = new HashSet<IClient>();
		this.ipNet = ipNet;
	}
	@Override
	protected Void doInBackground() throws Exception {
		
		int searchSeg = 255 / 15;				//hard code 5 threads to speed up local search
		
		Set<Thread> t = new HashSet<Thread>();
		for(int i = 0; i < 15; i++){				//partial hard code
			t.add(new Thread(new ThreadSearch(this.ipNet,((i*searchSeg) + 1),((((i + 1)*searchSeg) )), this.machines)));
		}
		
		for(Thread th : t){
			th.start();
		}
		for(Thread thread : t){
			while(thread.isAlive()){
				process(ThreadSearch.progress);
			}
		}
		return null;
	}
	
	protected void process(int progress) {
		// TODO Auto-generated method stub
		jpb.getModel().setValue(progress);
		jpb.repaint();
	}
	
	@Override
       protected void done() {
			try{
				IPTable.IPTableModel ip = (IPTable.IPTableModel)this.jt.getModel();
	        	ip.newElements(this.machines);
			}
			catch(Exception e){
				System.out.println("Error in table data binding: " + e.toString());
			}
       }
	
}