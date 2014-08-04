import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.Set;




public class IPFreely implements ActionListener {

	JProgressBar jpb;
	JFrame window = new JFrame();
	JButton src;
    JTable jt;
    static IClient local;
	public static void main(String[] args) {
		local = Client.LocalHost();
		IPFreely program = new IPFreely("IPFreely", local);

	}
	public IPFreely(String name, IClient local){
		
		window.setTitle(name);
		window.setLayout(new BorderLayout(20,20));			//set window layout to border layout
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// set default window close action
		window.setSize(1000, 700);							//set window size
		Image im = Toolkit.getDefaultToolkit().getImage("~/images/anonymous.jpg");
		window.setIconImage(im);
		
		
		//Progress Bar/ Middle panel
		JPanel pPanel = new JPanel();
		pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.Y_AXIS));

	    
	    ProgressBar pb = new ProgressBar();
	    IPTable ipTable = new IPTable();
	    
	    pPanel.add(this.jpb = pb.DrawProgressBar());
	    pPanel.add(this.jt = ipTable.DrawIPTable());
		
		
		
		//Controls panel/ West panel
		JPanel bPanel = new JPanel();						//create button panel
		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.Y_AXIS)); // set panel layout to a vertical box layout
		JLabel lbl = new JLabel("Searh for active clients on the network.");
		src = new JButton("Search");	//create button
		src.setSize(50,50);									//set size of button
		src.addActionListener(this);						
		bPanel.add(lbl);										//add label to panel
		bPanel.add(Box.createRigidArea(new Dimension(0,25)));
		bPanel.add(src);										//add button to panel
		
		//LocalInfo Panel/North
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		JLabel locLbl = new JLabel("Your Local Info");
		infoPanel.add(locLbl);
		
		JTable nicTable = new IPTable().DrawIPTable();
		if(local != null){
			IPTable.IPTableModel model = (IPTable.IPTableModel)nicTable.getModel();
			model.hostIP(local);
			infoPanel.add(nicTable);
		}
		
		
		
		
		window.add(infoPanel, BorderLayout.NORTH);
		window.add(bPanel, BorderLayout.WEST);				//add panel to window with the panel in the west part of the window
		window.add(pPanel, BorderLayout.CENTER);
		window.setVisible(true);							//draw total window 
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		byte[] ip = null;
		ip = this.local.getIPAddressByteArr();
		OneTimeSearch t = new OneTimeSearch(this.jpb, this.jt, ip);
		for(int i = 1; i < 255; i++){
			t.execute();
		}
	}
	
}
