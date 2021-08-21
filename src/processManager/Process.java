package processManager;

import ioManager.IODevice;

public class Process {
	// global constants
	public enum EState {nnew, running, wait, ready, terminated};
	
	public enum ERegister{
		ePC("pc"), eMAR("mar"), eMBR("mbr"), eIR("ir"), eStatus("status"), eAC("ac");
		private String register;
		private ERegister(String register) {this.register = register;}
		public String getText() {return this.register;}
	}
	
	// components
	private PCB pcb;
	private Segment codeSegment;
	private Segment stackSegment;
	private IODevice iodevice;
	
	// get&set
	public PCB getPcb() {return pcb;}
	public void setPcb(PCB pcb) {this.pcb = pcb;} // PCB는 노출되어 있어야 한다.
	public Segment getCodeSegment() {return codeSegment;}
	public void setCodeSegment(Segment codeSegment) {this.codeSegment = codeSegment;}
	public IODevice getIodevice() {return this.iodevice;}
	public void setIodevice(IODevice iodevice) {this.iodevice = iodevice;}
	public Segment getStackSegment() {return stackSegment;}
	public void setStackSegment(Segment stackSegment) {this.stackSegment = stackSegment;}
	
	// constructor
	public Process(int stackSegmentSize, int[] codes) {
		this.pcb = new PCB();
		this.codeSegment = new Segment(codes);
		this.stackSegment = new Segment(stackSegmentSize);
		this.iodevice = new IODevice("monitor");
	}

	//////////////////////////////SEGMENT//////////////////////////////
	public class Segment {
		private int[] memory;
		
		public Segment(int stackSegmentsize){this.memory = new int[stackSegmentsize];}
		public Segment(int[] codes){this.memory = codes;}
		
		public int[] getMemory() {return memory;}
		public void store(int address, int data) {this.memory[address] = data;}
		public int fetch(int address) {return this.memory[address];}
		
	}
	////////////////////////////////////////////////////////////////////
}
