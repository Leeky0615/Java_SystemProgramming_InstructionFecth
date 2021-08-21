package pc;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class MonitorPanel extends JPanel {
	private JTextArea monitor;
	public MonitorPanel() {
		this.setBorder(new TitledBorder(new LineBorder(Color.lightGray,1),"Monitor"));
		
		this.monitor = new JTextArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setViewportView(this.monitor);
		scroll.setPreferredSize(new Dimension(780,200));
		this.add(scroll);
	}
	public void append(String string) {
		this.monitor.append(string);
		this.monitor.setCaretPosition(this.monitor.getDocument().getLength());
		try {Thread.sleep(500);}
		catch (InterruptedException e) {e.printStackTrace();}
	}

}
