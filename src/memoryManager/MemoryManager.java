package memoryManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import cpu.ControlUnit.EInstruction;
import ioManager.IODevice;
import ioManager.IOManager;
import pc.MainFrame;
import processManager.Process;
import processManager.Process.ERegister;


public class MemoryManager {
	private MainFrame mainFrame;
	private Memory memory;
	private int processAddress;
	public MemoryManager() {}
	public void associate(MainFrame mainFrame, Memory memory) {
		this.memory = memory;
		this.mainFrame = mainFrame;
	}
	
	public Process allocate(File file,IOManager ioManager) {
		try {
			String fileName = file.getName();
			this.mainFrame.getMonitorPanel().append("OS : allocate program("+file.getName()+")\n");
			int num = fileName.length()-1;
			this.processAddress = (fileName.charAt(num)-48)*64;
			
			int stackSegmentSize = 0;
			int[] codes = null;
			
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.equals(".stack")) {
					stackSegmentSize = this.parseStack(scanner);
				} else if (line.equals(".code")) {
					codes = (int[]) this.parseCode(scanner,stackSegmentSize);
				} else if (line.substring(0, 2).equals("//")) {
				} else if (line.isEmpty()) {}
			}
			Process process = new Process(stackSegmentSize, codes);
			this.setPCB(process ,ioManager.getMonitor(),file.getName());
			this.memory.loadProcess(processAddress, process);
			scanner.close();
			return process;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setPCB(Process process, IODevice ioDevice,String processId) {
		process.getPcb().setId(processId);
		process.getPcb().seteState(Process.EState.ready);
		process.getPcb().getRegisters().get(ERegister.ePC.ordinal()).set(this.processAddress);;
		process.getPcb().setIoDevice(ioDevice);
		process.getPcb().setAddress(this.processAddress);
		process.getPcb().setSize(100);
	}
	
	private int parseStack(Scanner scanner) {
		int stackSize = 0;
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.equals(".stack")) {
			}else if(line.isEmpty()) {break;
			}else if(line.substring(0,2).equals("//")) {
			}else {
				stackSize = Integer.parseInt(line.substring(line.lastIndexOf(" ")+1));
			}
		}
		return stackSize;
	}

	private int[] parseCode(Scanner scanner, int stackSegmentSize) {
		ArrayList<Integer> codes =new ArrayList<Integer>();
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.equals(".code")) {
			}else if(line.isEmpty()) {
			}else if(line.substring(0,2).equals("//")) {
			}else if(line.equals("halt")){
				int opcode = EInstruction.HALT.ordinal();
				int value = opcode<<24;
				codes.add(value);
				break;
			}else if(line.equals("loop:")){
				int opcode = EInstruction.Loop.ordinal();
				int value = opcode<<24;
				codes.add(value);
			}else {
				String[] lines = line.split("\\s",2);
				switch (lines[0]) {
				case "sti":
					int opcode = EInstruction.STI.ordinal();
					int operand1 = Integer.parseInt(lines[1].substring(0,lines[1].lastIndexOf(",")));
					int operand2 = Integer.parseInt(lines[1].substring(lines[1].lastIndexOf(" ")+1));
					int value = (opcode<<24)+(operand1<<16)+operand2;
					codes.add(value);
					break;
				case "sta":
					opcode = EInstruction.STA.ordinal();
					operand1 = Integer.parseInt(lines[1]);
					value = (opcode << 24) + (operand1 << 16);
					codes.add(value);
					break;
				case "intr":
					opcode = EInstruction.INTR.ordinal();
					operand1 = Integer.parseInt(lines[1].substring(0,lines[1].lastIndexOf(",")));
					operand2 = Integer.parseInt(lines[1].substring(lines[1].lastIndexOf(" ")+1));
					value = (opcode<<24)+(operand1<<16)+operand2;
					codes.add(value);
					break;
				case "lda":
					opcode = EInstruction.LDA.ordinal();
					operand1 = Integer.parseInt(lines[1]);
					value = (opcode<<24)+(operand1<<16);
					codes.add(value);
					break;
				case "addi":
					opcode = EInstruction.ADDI.ordinal();
					operand2 = Integer.parseInt(lines[1]);
					value = (opcode<<24)+operand2;
					codes.add(value);
					break;
				case "cmp":
					opcode = EInstruction.CMP.ordinal();
					operand1 = Integer.parseInt(lines[1]);
					value = (opcode<<24)+(operand1<<16);
					codes.add(value);
					break;
				case "jgz":
					opcode = EInstruction.JGZ.ordinal();
					value = opcode<<24;
					codes.add(value);
				default:
					break;
				}
			}
		}
		Integer[] codeArray = codes.toArray(new Integer[codes.size()]);
		return Arrays.stream(codeArray).mapToInt(Integer::intValue).toArray(); 
	}
	public void deleteprocess(Process process) {
		this.memory.deleteProcess(process);
	}
}
