package pc;

import javax.swing.JPanel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

public class OSQueuePanel extends JPanel {
	public ReadyQueuePanel getReadyQueue() {
		return readyQueue;
	}

	public WaitQueuePanel getWaitQueue() {
		return waitQueue;
	}

	public InterruptQueuePanel getInterruptQueue() {
		return interruptQueue;
	}

	private ReadyQueuePanel readyQueue;
	private WaitQueuePanel waitQueue;
	private InterruptQueuePanel interruptQueue;

	public OSQueuePanel() {
		this.setLayout(new LinearLayout(Orientation.HORIZONTAL, 25));

		
		this.readyQueue = new ReadyQueuePanel();
		this.add(readyQueue,new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));
		this.waitQueue = new WaitQueuePanel();
		this.add(waitQueue,new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));
		this.interruptQueue = new InterruptQueuePanel();
		this.add(interruptQueue,new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));

	}

}
