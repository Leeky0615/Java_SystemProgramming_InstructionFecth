package cpu;

import memoryManager.Memory;
import os.OperatingSystem;
import pc.MainFrame;

public class CentralProcessingUnit {
	//Components
	private ControlUnit controlUnit;
	private ArithmaticLogicUnit arithmaticLogicUnit;
	
	private Register pc, sp, mar, mbr, ir, ac, status;
	
	//association
	private Memory memory;
	private OperatingSystem os;
	public CentralProcessingUnit(MainFrame mainFrame) {
		//Components
		this.controlUnit = new ControlUnit(mainFrame);
		this.arithmaticLogicUnit = new ArithmaticLogicUnit();
		
		this.pc = new Register();
		this.ir = new Register();
		this.sp = new Register();
		this.mar = new Register();
		this.mbr = new Register();
		this.ac = new Register();
		this.status = new Register();
		
		this.controlUnit.connect(this.ir,this.pc,this.sp,this.mar,this.status,this.mbr);
		this.arithmaticLogicUnit.connect(this.mbr,this.ac,this.status);
		
		this.controlUnit.connect(this.arithmaticLogicUnit);
	}
	
	public void connect(Memory memory) {
		this.memory = memory;
		this.memory.connect(this.mar, this.mbr);
		this.controlUnit.connect(this.memory);
	}
	
	public void connect(OperatingSystem os) {
		this.os = os;
		this.controlUnit.connect(this.os);
	}
	
	public void run() {
		this.controlUnit.setRegister();
	}
}
