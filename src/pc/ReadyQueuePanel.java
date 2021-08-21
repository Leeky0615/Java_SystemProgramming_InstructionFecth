package pc;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ReadyQueuePanel extends JPanel{
	private JTextArea readyQueue;

	public ReadyQueuePanel() {
		this.setBorder(new TitledBorder(new LineBorder(Color.lightGray,1),"Ready Queue"));
		
		this.readyQueue = new JTextArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setViewportView(this.readyQueue);
		scroll.setPreferredSize(new Dimension(230,150));
		this.add(scroll);
	}

	public void append(String string) {
		this.readyQueue.append(string);
		this.readyQueue.setCaretPosition(this.readyQueue.getDocument().getLength());
		try {Thread.sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	public void remove() {
		String split[] = this.readyQueue.getText().split("\\n");
		this.readyQueue.selectAll();
		this.readyQueue.replaceSelection("");
		for (int i = 1; i < split.length; i++) {
			this.readyQueue.append(split[i]+"\n");
		}
		try {Thread.sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
}
