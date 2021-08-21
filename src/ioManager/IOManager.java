package ioManager;

import memoryManager.Memory;

public class IOManager {
	private IODevice monitor;
	private Memory memory;
	
	public IOManager(Memory memory) {
		this.memory = memory;
		this.monitor = new IODevice("monitor");
	}
	public IODevice getMonitor() {return monitor;}
	public void setMonitor(IODevice monitor) {this.monitor = monitor;}
	
}
