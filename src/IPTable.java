import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class IPTable{
	JTable jt;
	JScrollPane js;
	
	
	public JTable DrawIPTable(){
		this.jt = new JTable(new IPTableModel());
		jt.setPreferredScrollableViewportSize(new Dimension(500,70));
		jt.setFillsViewportHeight(true);
		return this.jt;
	}
	
	class IPTableModel extends AbstractTableModel {
		
		
		private static final long serialVersionUID = 1L;
		private String[] columnNames = {"Local Host Name",
								"IP Address",
								"MAC Address"};
		//private Object[][] data;
		private Object[][] data;
		private Set<Client> machines = new HashSet<Client>();
		public IPTableModel(){
			
		}
		
		public IPTableModel(Set<Client> machines){
			this.machines = machines;
			Client[] arrLocal = machines.toArray(new Client[machines.size()]); 
			Object[][] data = new Object[machines.size()][3];
			for(int x = 0; x < machines.size(); x++){
				data[x][0] = arrLocal[x].getHostName();
				data[x][1] = arrLocal[x].getIPAddress();
				data[x][2] = arrLocal[x].getMacAddress();
			}
			this.data = data;
		}
		
		public void newElements(Set<Client> machines){
			this.machines = machines;
			Client[] arrLocal = machines.toArray(new Client[machines.size()]); 
			Object[][] data = new Object[machines.size()][3];
			for(int x = 0; x < machines.size(); x++){
				data[x][0] = arrLocal[x].getHostName();
				data[x][1] = arrLocal[x].getIPAddress();
				data[x][2] = arrLocal[x].getMacAddress();
			}
			this.data = data;
			fireTableDataChanged();
		}

	    public boolean isCellEditable(int row, int column) {                
	            return false;               
	    };
		
	    
		@Override
		public int getColumnCount() {
			return this.columnNames.length;
		}

		@Override
		public int getRowCount() {
			return this.machines.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
		
		public String getColumnName(int col){
			return columnNames[col];
		}
		
	}
}