package cpu;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import memoryManager.Memory;
import os.OperatingSystem;
import pc.MainFrame;
import processManager.Interrupt;
import processManager.Interrupt.EInterrupt;
import processManager.Process;
import processManager.Process.ERegister;

public class ControlUnit {
	public enum EInstruction {STI, STA, LDA, ADDI, Loop, CMP, JGZ, HALT,INTR};
	
	//association
	private MainFrame mainFrame;
	private Register pc, sp, mar, mbr,ir, ac, status;
	private ArithmaticLogicUnit arithmaticLogicUnit;
	private OperatingSystem os;
	private Memory memory;
	private Process process;
	private Timer timer;

	public ControlUnit(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.timer = new Timer();
	}
	
	public void connect(ArithmaticLogicUnit arithmaticLogicUnit) {this.arithmaticLogicUnit = arithmaticLogicUnit;}
	public void connect(OperatingSystem os) {this.os = os;}
	public void connect(Memory memory) {this.memory = memory;}
	public void connect(Register ir,Register pc,Register sp,Register mar,Register status,Register mbr) {
		this.ir = ir;
		this.pc = pc;
		this.sp = sp;
		this.mar = mar;
		this.status = status;
		this.mbr = mbr;
	}
	public void timer() {
		TimerTask task = new TimerTask() {
			public void run() {
				status.set(EInterrupt.eTimerFinished.ordinal());
			}
		};
		this.timer.schedule(task,20000);
	}
	
	public void setRegister() {
		try {
			this.process = os.getProcessManager().getReadyQueue().peek();
			Vector<Register> registers = this.process.getPcb().getRegisters();
			this.mainFrame.getCpuPanel().append("CPU : peek firstProcess in ReadyQ\n");
			this.mainFrame.getCpuPanel().append("CPU : Set Register(Context Switching)\n");
			os.getProcessManager().setCurrentProcess(this.process);
			for (ERegister eRegister : ERegister.values()) {
				switch (eRegister.getText()) {
				case "pc":
					this.pc = registers.get(eRegister.ordinal());
					break;
				case "ir":
					this.ir = registers.get(eRegister.ordinal());
					break;
				case "mar":
					this.mar = registers.get(eRegister.ordinal());
					break;
				case "mbr":
					this.mbr = registers.get(eRegister.ordinal());
					break;
				case "status":
					this.status = registers.get(eRegister.ordinal());
					break;
				default:
					break;
				}
			}
			this.status.set(EInterrupt.eProcessStart.ordinal() + 1);
			this.os.getProcessManager().checkInterrupt(new Interrupt(EInterrupt.eProcessStart, null));
			this.status.set(0);
			bT = true;
			run();
		} catch (NullPointerException e) {
			this.mainFrame.getQueuePanel().getCurrentProcess().remove();
			this.os.getProcessManager().getReadyQueue().remove();
			this.os.getProcessManager().getInterruptQueue().remove();
			this.mainFrame.getCpuPanel().append("ALL PROCESS TERMINATED\n");
			this.timer.cancel();
		}
	}

	private boolean bT = true;
	public void run() {
		timer();
		while (bT) {
			this.fetch();
			this.checkStatus();
			
		}
	}
	
	private void fetch() {
		this.mainFrame.getCpuPanel().append("\n----------- CPU : Fetch ----------\n");
		this.mar.set(this.pc.get());
		this.mbr.set(this.memory.fetch(this.mar.get()));
		this.ir.set(this.mbr.get());
		this.mainFrame.getCpuPanel().append("pc : "+this.pc.get()+"\n");
		this.mainFrame.getCpuPanel().append("ir : "+this.ir.get()+"\n");
		this.decode();
		try {Thread.sleep(500);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	private void checkStatus() {
		switch (this.status.get() & 0x3F) {
		case 1:
			this.os.getProcessManager().checkInterrupt(new Interrupt(EInterrupt.eProcessStart,null));
			this.status.set(0);
			break;
		case 2:
			this.os.getProcessManager().checkInterrupt(new Interrupt(EInterrupt.eProcessTerminated,null));
			this.memory.deleteProcess(process);
			this.status.set(0);
			bT = false;
			this.setRegister();
			break;
		case 4:
			this.status.set(0);
			Vector<Register> registers = new Vector<Register>();
			registers.add(this.pc);
			registers.add(this.mar);
			registers.add(this.mbr);
			registers.add(this.ir);
			registers.add(this.status);
			registers.add(this.arithmaticLogicUnit.getAc());
			this.os.getProcessManager().getCurrentProcess().getPcb().setRegister(registers);
			this.os.getProcessManager().checkInterrupt(new Interrupt(EInterrupt.eTimerFinished,null));
			bT = false;
			this.setRegister();
			break;
		case 5:
			registers = new Vector<Register>();
			registers.add(this.pc);
			registers.add(this.mar);
			registers.add(this.mbr);
			registers.add(this.ir);
			registers.add(this.status);
			registers.add(this.arithmaticLogicUnit.getAc());
			this.os.getProcessManager().getCurrentProcess().getPcb().setRegister(registers);
			int value = this.memory.store(this.process, (int)interruptvalue);
			this.os.getProcessManager().checkInterrupt(new Interrupt(EInterrupt.eIOStart,value));
			this.status.set(0);
			bT = false;
			this.setRegister();
			break;
		default:
			break;
		}
		
	}
	private void decode() {
		int instruction = this.ir.get() >>> 24;
		this.mainFrame.getCpuPanel().append("--------- CPU : Decode ---------\n");
		this.mainFrame.getCpuPanel().append("opcode : "+EInstruction.values()[instruction]+"\n");
		switch(EInstruction.values()[instruction]) {
		case STI: STI();break;
		case STA: STA();break;
		case LDA: LDA();break;
		case ADDI: ADDI();break;
		case CMP: CMP();break;
		case JGZ: JGZ();break;
		case HALT: 
			HALT();
			break;
		case INTR: 
			INTR();
			break; 
		case Loop: Loop();break; 
		default: break;
		}
	}
	
	private void STI() {
		int address = (this.ir.get()>>>16) & 0xff;
		int value = this.ir.get() & 0xffff;
		this.mainFrame.getCpuPanel().append("operand1(address) : "+address+"\n");
		this.mainFrame.getCpuPanel().append("operand2(value) : "+value+"\n");
		this.mar.set(address);
		this.memory.store(process, this.mar.get(), value);
		this.pc.set(this.pc.get()+1);
	}
	
	private void STA() {
		int address = (this.ir.get()>>>16) & 0xff;
		this.mainFrame.getCpuPanel().append("operand1(address) : "+address+"\n");
		this.mar.set(address);
		this.memory.fetch(this.mar.get());
		this.pc.set(this.pc.get()+1);
		this.memory.store(process,this.mar.get(),this.arithmaticLogicUnit.getAc().get());
	}
	
	private void LDA() {
		int address = (this.ir.get()>>>16) & 0xff;
		this.mainFrame.getCpuPanel().append("operand1(address) : "+address+"\n");
		this.mar.set(address);
		this.mbr.set(this.memory.store(this.process, this.mar.get()));
		this.arithmaticLogicUnit.getAc().set(this.mbr.get());
		this.pc.set(this.pc.get()+1);
	}
	
	private void ADDI() {
		int value = (this.ir.get()) & 0xffff;
		this.mainFrame.getCpuPanel().append("operand2(value) : "+value+"\n");
		this.arithmaticLogicUnit.getAc().set(value+this.arithmaticLogicUnit.getAc().get());
		this.pc.set(this.pc.get()+1);
	}
	
	private void CMP() {
		int address = (this.ir.get()>>>16) & 0xff;
		this.mainFrame.getCpuPanel().append("operand1(address) : "+address+"\n");
		this.mar.set(address);
		this.mbr.set(this.memory.store(this.process, this.mar.get()));
		int x = this.arithmaticLogicUnit.getAc().get();
		this.arithmaticLogicUnit.getAc().set(this.mbr.get()-x);
		this.pc.set(this.pc.get()+1);
		this.mainFrame.getCpuPanel().append("COUNT : "+x);
	}
	private int loop;
	private void Loop() {
		loop = this.pc.get()+1;
		this.mainFrame.getCpuPanel().append("pc(loop point) : "+loop+"\n");
		this.pc.set(this.pc.get()+1);
	}
	private Object interruptvalue;
	private void INTR() {
		int address = (this.ir.get()>>>16) & 0xff;
		this.mainFrame.getCpuPanel().append("interrupt Number : "+address+"\n");
		interruptvalue = this.ir.get() & 0xffff;
		this.mainFrame.getCpuPanel().append("interruptValue address : "+interruptvalue+"\n");
		this.pc.set(this.pc.get()+1);
		this.status.set(address);
	}

	private void HALT() {
		this.status.set(EInterrupt.eProcessTerminated.ordinal());
	}

	private void JGZ() {
		if (this.arithmaticLogicUnit.getAc().get()>0) {
			this.pc.set(loop);
			this.mainFrame.getCpuPanel().append("JUMP TO LOOP\n");
		}else {
			this.pc.set(this.pc.get()+1);
			this.mainFrame.getCpuPanel().append("FINISH LOOP\n");
		}
	}
}
