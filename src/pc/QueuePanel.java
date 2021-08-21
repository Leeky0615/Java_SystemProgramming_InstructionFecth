package pc;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

public class QueuePanel extends JPanel {
	private CurrentProcessPanel currentProcess;
	private OSQueuePanel osQueue;
	
	
	public QueuePanel() {
		this.setBorder(new TitledBorder(new LineBorder(Color.lightGray,1),"Queue Status"));
		this.setLayout(new LinearLayout(Orientation.VERTICAL, 0));
		
		this.currentProcess = new CurrentProcessPanel();
		this.add(currentProcess,new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));
		
		this.osQueue = new OSQueuePanel();
		this.add(osQueue,new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));
	}
	
	public CurrentProcessPanel getCurrentProcess() {return currentProcess;}
	public OSQueuePanel getOsQueue() {return osQueue;}

}
