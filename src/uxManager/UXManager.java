package uxManager;

import java.io.File;

import fileManager.FileManager;
import pc.MainFrame;
import processManager.ProcessManager;


public class UXManager {
	private FileManager fileManager;
	private ProcessManager processManager;
	
	public UXManager() {}
	
	public void associate(MainFrame mainFrame, FileManager fileManager, ProcessManager processManager) {
		this.fileManager = fileManager;
		this.processManager = processManager;
	}
	
	public void run() {
		// login
		// desktop display
		// wait for event : 이벤트가 들어오면 thread를 만들어서 일을 시키고 자신은 다시 waiting.
		// 화면에 더블클릭하는 부분
		File path = new File("exe/");
		File[] fileList = path.listFiles();
		
		if(fileList.length > 0){
		    for(int i=0; i < fileList.length; i++){
		    	this.processManager.execute(this.fileManager.getFile("exe/"+fileList[i].getName())); // 일단 single tasking으로 만들기
		    }
		}
	}
}
