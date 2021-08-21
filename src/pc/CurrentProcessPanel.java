package pc;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class CurrentProcessPanel extends JPanel{
	private JTextArea currentProcess;
	public CurrentProcessPanel() {
		this.setBorder(new TitledBorder(new LineBorder(Color.lightGray,1),"Current Process"));
		
		this.currentProcess = new JTextArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scroll.setViewportView(this.currentProcess);
		scroll.setPreferredSize(new Dimension(780,30));
		this.add(scroll);
	}
	public void append(String string) {
		this.currentProcess.append(string);
		try {Thread.sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	public void remove() {
		String split[] = this.currentProcess.getText().split("\\n");
		this.currentProcess.selectAll();
		this.currentProcess.replaceSelection("");
		for (int i = 1; i < split.length; i++) {
			this.currentProcess.append(split[i]+"\n");
		}
		try {Thread.sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
}
