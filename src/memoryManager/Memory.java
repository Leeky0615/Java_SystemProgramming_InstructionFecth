package memoryManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cpu.Register;
import pc.MainFrame;
import processManager.Process;
public class Memory {
	private int[] buffer; 
	
	private Register mar, mbr;
	
	public Memory() {
		this.buffer = new int[1024];
	}
	
	public int[] getBuffer() {return this.buffer;}

	public void loadProcess(int address, Process process) {
		for (int i = address; i < address+process.getCodeSegment().getMemory().length; i++) {
			this.buffer[i] = process.getCodeSegment().fetch(i-address);
		}
		
	}
	
	public void loadOS(MainFrame mainFrame, int os) {
		mainFrame.getMonitorPanel().append("Boot Loader : Operating System into memory\n");
		this.buffer[0] = os;
	}
	
	public void connect(Register mar,Register mbr) {
		this.mar = mar;
		this.mbr = mbr;
	}
	
	
	public int fetch(int address) {
		return this.buffer[address];
	}
	
	public void store(Process process,int address, int data) {
		process.getStackSegment().store(address, data);
	}
	
	public int store(Process process, int address) {
		return process.getStackSegment().getMemory()[address];
	}

	@SuppressWarnings("null")
	public void deleteProcess(Process process) {
		for (int i = process.getPcb().getAddress(); i < process.getPcb().getAddress()+process.getCodeSegment().getMemory().length; i++) {
			this.buffer[i] = 0;
		}
	}
	


}
