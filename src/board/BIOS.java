package board;
import cpu.CentralProcessingUnit;
import hardDisk.HardDisk;
import memoryManager.Memory;
import os.OperatingSystem;
import pc.MainFrame;

public class BIOS {
	private MainFrame mainFrame;
	private CentralProcessingUnit centralProcessingUnit;
	private Memory memory;
	private HardDisk hardDisk;
	
	public BIOS(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.mainFrame.setVisible(true);
		this.mainFrame.getMonitorPanel().append("BIOS : RUN\n");

		// �ϵ��ũ ����
		this.hardDisk = new HardDisk();
		this.mainFrame.getMonitorPanel().append("BIOS : Connect HardDisk\n");

		// CPU ����
		this.centralProcessingUnit = new CentralProcessingUnit(this.mainFrame);
		this.mainFrame.getMonitorPanel().append("BIOS : Connect CentralProcessingUnit\n");

		// �޸� ����
		this.memory = new Memory();
		this.mainFrame.getMonitorPanel().append("BIOS : Connect Memory\n");

		// CPU�� �޸𸮿� ����
		this.centralProcessingUnit.connect(this.memory);
		this.mainFrame.getMonitorPanel().append("BIOS : CentralProcessingUnit Connect with Memory\n");
	}

	public void run() {
		OperatingSystem os = this.hardDisk.bootLoader(this.memory);
		this.memory.loadOS(this.mainFrame, os.hashCode());
		this.centralProcessingUnit.connect(os);
		os.initialize(this.memory);
		os.associate(this.mainFrame, this.memory);
		this.mainFrame.getMonitorPanel().append("CPU : Operating System Run\n");
		os.run(this.mainFrame);
		this.centralProcessingUnit.run();
	}
	
}
