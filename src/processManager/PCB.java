package processManager;

import java.util.Vector;

import cpu.Register;
import ioManager.IODevice;
import processManager.Process.ERegister;
import processManager.Process.EState;

public class PCB {
	private int address;
	private String id;
	private int processSize;
	private EState eState;
	private Vector<Register> registers;
	private Vector<IODevice> ioDevices; 
	// 내가 현재 오픈하고 있는 디바이스들의 포인터가 있어야한다 파일도 io디바이스의 일종으로 넣어라
	
	public PCB() {
		this.registers = new Vector<Register>();
		for (ERegister eRegister: ERegister.values()) {this.registers.add(new Register());}
		this.ioDevices = new Vector<IODevice>();
	}
	
	public int getAddress() {return address;}
	public void setAddress(int address) {this.address = address;}
	public Vector<IODevice> getIoDevices() {return ioDevices;}
	public void setIoDevice(IODevice ioDevice) {ioDevices.add(ioDevice);}
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	public EState geteState() {return eState;}
	public void seteState(EState eState) {this.eState = eState;}
	public Vector<Register> getRegisters() {return registers;}
	public void setRegister(Vector<Register> register) {this.registers = register;}
	public void setSize(int processSize) {this.processSize = processSize;}
	
	
}	