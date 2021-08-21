package hardDisk;

import memoryManager.Memory;
import os.OperatingSystem;

public class HardDisk {
	private Object[] hardDisk;
	private OperatingSystem os;
	
	public HardDisk() {
		this.hardDisk = new Object[100];
	
		this.hardDisk[0] = this.os;
	}
	
	public OperatingSystem bootLoader(Memory memory) {
		this.os = new OperatingSystem();
		return this.os;
	}
}
