import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeListener;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

public class ProgressBar{
	private JProgressBar jpb;
	
	public ProgressBar(){
		this.jpb = new JProgressBar(new IPProgressBar());
		this.jpb.setStringPainted(true);
		Border border = BorderFactory.createTitledBorder("Scanning Status");
		this.jpb.setBorder(border);
	}
	public JProgressBar DrawProgressBar(){
		return this.jpb;
	}
	class IPProgressBar implements BoundedRangeModel{
	
		private int max;
		private int min;
		private int curValue;
		private boolean isAdjusting;
		private int valueRemaining;
		
		public IPProgressBar(){
			this.max = 255;
			this.min = 0;
			this.curValue = 0;
			this.isAdjusting = false;
			this.valueRemaining = this.max - this.curValue;
		}
		
		
		@Override
		public void addChangeListener(ChangeListener x) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public int getExtent() {
			return this.valueRemaining;
		}
	
		@Override
		public int getMaximum() {
			return this.max;
		}
	
		@Override
		public int getMinimum() {
			return this.min;
		}
	
		@Override
		public int getValue() {
			return this.curValue;
		}
	
		@Override
		public boolean getValueIsAdjusting() {
			return this.isAdjusting;
		}
	
		@Override
		public void removeChangeListener(ChangeListener x) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void setExtent(int newExtent) {
			this.valueRemaining = newExtent;
		}
	
		@Override
		public void setMaximum(int newMaximum) {
			this.max = newMaximum;
		}
	
		@Override
		public void setMinimum(int newMinimum) {
			this.min = newMinimum;
		}
	
		@Override
		public void setRangeProperties(int value, int extent, int min, int max,boolean adjusting) {
			this.curValue = value;
			this.valueRemaining = extent;
			this.min = min;
			this.max = max;
			this.isAdjusting = adjusting;
		}
	
		@Override
		public void setValue(int newValue) {
			this.curValue = newValue;
			this.valueRemaining = this.max - this.curValue;
			setValueIsAdjusting(true);
		}
	
		@Override
		public void setValueIsAdjusting(boolean b) {
			this.isAdjusting = b;
		}

	}
}