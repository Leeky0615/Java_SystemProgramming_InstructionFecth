package processManager;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import fileManager.FileManager;
import ioManager.IODevice;
import ioManager.IODevice.EState;
import ioManager.IOManager;
import loader.Loader;
import memoryManager.MemoryManager;
import pc.MainFrame;
import processManager.Interrupt.EInterrupt;


public class ProcessManager {

	private boolean timecheck;
	private Loader loader; 
	private InterruptHandler interruptHandler;
	
	// 두 큐가 구조가 동일해서 같은 클래스로 만듦
	private Process currentProcess;
	private Queue<Process> readyQueue;
	private Vector<Process> waitQueue;
	private Queue<Interrupt> interruptQueue;
	
	
	// associate
	private MainFrame mainFrame;
	private FileManager fileManager;
	private MemoryManager memoryManager;
	private IOManager ioManager;

	public ProcessManager() {
		this.loader = new Loader();
		this.interruptHandler = new InterruptHandler();
		this.interruptQueue = new LinkedList<Interrupt>();
		this.readyQueue = new LinkedList<Process>();
		this.waitQueue = new Vector<Process>();
	}
	
	public void associate(MainFrame mainFrame,MemoryManager memoryManager,FileManager fileManager, IOManager ioManager) {
		this.memoryManager = memoryManager;
		this.fileManager = fileManager;
		this.ioManager = ioManager;
		this.mainFrame = mainFrame;
	}
	public Queue<Interrupt> getInterruptQueue() {return interruptQueue;}
	public Queue<Process> getReadyQueue() {return readyQueue;}
	public void setReadyQueue(Queue<Process> readyQueue) {this.readyQueue = readyQueue;}
	public Process getCurrentProcess() {return this.currentProcess;}
	public void setCurrentProcess(Process process) {
		mainFrame.getQueuePanel().getCurrentProcess().remove();
		this.currentProcess = process;
	}

	public void execute(File file) {
		Process process = this.loader.load(file,this.ioManager,this.memoryManager);
		this.readyQueue.add(process);
		this.mainFrame.getMonitorPanel().append("OS : put process into readyQueue\n");
		this.mainFrame.getQueuePanel().getOsQueue().getReadyQueue().append(file.getName()+"\n");
	}
	
	public void checkInterrupt(Interrupt interrupt) {
		try {Thread.sleep(500);}
		catch (InterruptedException e) {e.printStackTrace();}
		if (interrupt != null) {
			this.interruptQueue.add(interrupt);
			mainFrame.getQueuePanel().getOsQueue().getInterruptQueue()
					.append(getCurrentProcess().getPcb().getId() + "(" + interrupt.geteType().name() + ")\n");
		}
		while(!(interruptQueue.isEmpty())) {
			for(Process process : waitQueue) {
				if (process.getIodevice().geteState() == IODevice.EState.eTerminated) {
					this.interruptQueue.add(new Interrupt(EInterrupt.eIOFinished,process));
					mainFrame.getQueuePanel().getOsQueue().getInterruptQueue().remove();
					mainFrame.getQueuePanel().getOsQueue().getInterruptQueue().append(process.getPcb().getId()+"("+EInterrupt.eIOFinished.name()+")\n");
					process.getIodevice().seteState(EState.eIdle);
				}
			}
			this.interruptHandler.process(interruptQueue.peek().geteType());
		}
	}
	
	private class InterruptHandler {
		
		public InterruptHandler() {}
		public void process(EInterrupt eInterrupt) {
			try {Thread.sleep(500);}
			catch (InterruptedException e) {e.printStackTrace();}
			switch (eInterrupt) {
			case eProcessStart:
				mainFrame.getQueuePanel().getCurrentProcess().append(getCurrentProcess().getPcb().getId()+"\n");
				readyQueue.remove();
				mainFrame.getQueuePanel().getOsQueue().getReadyQueue().remove();
				interruptQueue.remove();
				mainFrame.getQueuePanel().getOsQueue().getInterruptQueue().remove();
				break;
			case eProcessTerminated:
				interruptQueue.remove();
				mainFrame.getQueuePanel().getOsQueue().getInterruptQueue().remove();
				break;
			case eTimerStart:
				break;
			case eTimerFinished:
				mainFrame.getCpuPanel().append("========== Timer Finished ========\n");
				readyQueue.add(currentProcess);
				mainFrame.getQueuePanel().getOsQueue().getReadyQueue().append(currentProcess.getPcb().getId()+"\n");
				interruptQueue.remove();
				mainFrame.getQueuePanel().getOsQueue().getInterruptQueue().remove();
				break;
			case eIOStart:
				waitQueue.add(currentProcess);
				mainFrame.getQueuePanel().getOsQueue().getWaitQueue().append(currentProcess.getPcb().getId()+"\n");
				currentProcess.getIodevice().seteState(EState.eRunning);
				currentProcess.getIodevice().print(mainFrame,currentProcess,interruptQueue.remove().getParameter());
				break;
			case eIOFinished:
				mainFrame.getQueuePanel().getOsQueue().getInterruptQueue().remove();
				Process process = (Process)interruptQueue.remove().getParameter();
				waitQueue.remove(process);
				mainFrame.getQueuePanel().getOsQueue().getWaitQueue().remove();
				readyQueue.add(process);
				mainFrame.getQueuePanel().getOsQueue().getReadyQueue().append(process.getPcb().getId()+"\n");
				process.getIodevice().seteState(EState.eIdle);
				break;
			default:
				break;
			}
		}
	}

	
	
	

}

