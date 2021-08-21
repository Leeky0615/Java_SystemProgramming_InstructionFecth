package pc;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class CPUPanel extends JPanel {
	private JTextArea cpu;
	public CPUPanel() {
		this.setBorder(new TitledBorder(new LineBorder(Color.lightGray,1),"CPU Status"));
		
		this.cpu = new JTextArea();
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setViewportView(this.cpu);
		scroll.setPreferredSize(new Dimension(780,240));
		this.add(scroll);
	}
	public void append(String string) {
		this.cpu.append(string);
		this.cpu.setCaretPosition(this.cpu.getDocument().getLength());
		try {Thread.sleep(500);}
		catch (InterruptedException e) {e.printStackTrace();}
	}

}
