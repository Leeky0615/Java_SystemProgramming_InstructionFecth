package pc;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class WaitQueuePanel extends JPanel{
	private JTextArea waitQueue;
	
	public WaitQueuePanel() {
		this.setBorder(new TitledBorder(new LineBorder(Color.lightGray,1),"Wait Queue"));
		
		this.waitQueue = new JTextArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setViewportView(this.waitQueue);
		scroll.setPreferredSize(new Dimension(230,150));
		this.add(scroll);
	}
	public void append(String string) {
		this.waitQueue.append(string);
		try {Thread.sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	public void remove() {
		String split[] = this.waitQueue.getText().split("\\n");
		this.waitQueue.selectAll();
		this.waitQueue.replaceSelection("");
		for (int i = 1; i < split.length; i++) {
			this.waitQueue.append(split[i]+"\n");
		}
		try {Thread.sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
}
