package os;

import fileManager.FileManager;
import ioManager.IOManager;
import memoryManager.Memory;
import memoryManager.MemoryManager;
import pc.MainFrame;
import processManager.ProcessManager;
import uxManager.UXManager;

public class OperatingSystem{
	private ProcessManager processManager;
	private MemoryManager memoryManager;
	private FileManager fileManager;
	private IOManager ioManager;
	private UXManager uxManager;
	
	public OperatingSystem() {}
	public void initialize(Memory memory) {
		this.processManager = new ProcessManager();
		this.memoryManager = new MemoryManager();
		this.fileManager = new FileManager();
		this.ioManager = new IOManager(memory);
		this.uxManager = new UXManager();
	}
	
	public void associate(MainFrame mainFrame, Memory memory) {
		this.memoryManager.associate(mainFrame, memory);
		this.processManager.associate(mainFrame,this.memoryManager,this.fileManager,this.ioManager);
		this.uxManager.associate(mainFrame, this.fileManager,this.processManager);
		mainFrame.getMonitorPanel().append("OS connect Managers\n");
	}
	
	public ProcessManager getProcessManager() {
		return processManager;
	}
	public MemoryManager getMemoryManager() {
		return memoryManager;
	}
	public void setProcessManager(ProcessManager processManager) {
		this.processManager = processManager;
	}
	public void run(MainFrame mainFrame) {
		mainFrame.getMonitorPanel().append("Operating System : UXManager run\n");
		this.uxManager.run();
	}
}
