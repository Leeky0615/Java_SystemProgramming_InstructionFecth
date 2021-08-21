package pc;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InterruptQueuePanel extends JPanel{
	private JTextArea interruptQueue;
	
	public InterruptQueuePanel() {
		this.setBorder(new TitledBorder(new LineBorder(Color.lightGray,1),"Interrupt Queue"));
		
		this.interruptQueue = new JTextArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setViewportView(this.interruptQueue);
		scroll.setPreferredSize(new Dimension(230,150));
		this.add(scroll);
	}
	public void append(String string) {
		this.interruptQueue.append(string);
		try {Thread.sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	public void remove() {
		String split[] = this.interruptQueue.getText().split("\\n");
		this.interruptQueue.selectAll();
		this.interruptQueue.replaceSelection("");
		for (int i = 1; i < split.length; i++) {
			this.interruptQueue.append(split[i]+"\n");
		}
		try {Thread.sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
}
