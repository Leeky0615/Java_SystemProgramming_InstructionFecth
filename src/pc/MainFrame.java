package pc;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MonitorPanel monitorPanel;
    private CPUPanel cpuPanel;
    private QueuePanel queuePanel;

    public MainFrame() {
        this.setTitle("명령어 인출 프로그램");
        this.setLocation(500, 200);
        this.setSize(800, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new LinearLayout(Orientation.VERTICAL, 0));
    }

    public void initialize() {
        this.monitorPanel = new MonitorPanel();
        this.add(this.monitorPanel, new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));
        this.cpuPanel = new CPUPanel();
        this.add(this.cpuPanel, new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));
        this.queuePanel = new QueuePanel();
        this.add(this.queuePanel, new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));
        this.pack();
    }

    public MonitorPanel getMonitorPanel() {
        return monitorPanel;
    }

    public CPUPanel getCpuPanel() {
        return cpuPanel;
    }

    public QueuePanel getQueuePanel() {
        return queuePanel;
    }

}
