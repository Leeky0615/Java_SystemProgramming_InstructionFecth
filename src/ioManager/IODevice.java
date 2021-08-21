package ioManager;

import java.util.Timer;
import java.util.TimerTask;

import pc.MainFrame;
import processManager.Process;

public class IODevice {
	public enum EState {eIdle, eRunning, eTerminated, eError}

	private String deviceName;
	private EState eState;

	public IODevice(String deviceName) {
		this.eState = EState.eIdle;
		this.deviceName = deviceName;
	}

	public String getDeviceName() {return deviceName;}
	public EState geteState() {return eState;}
	public void seteState(EState eState) {this.eState = eState;}
	
	public void print(MainFrame mainFrame, Process cuurenProcess,Object parameter) {
		try {Thread.sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
		mainFrame.getMonitorPanel().append("======= " + cuurenProcess.getPcb().getId() + "(I/ODevice_"
				+ cuurenProcess.getIodevice().getDeviceName() + ") : " + parameter + "=======\n");
		this.eState = EState.eTerminated;
	}
	
}
