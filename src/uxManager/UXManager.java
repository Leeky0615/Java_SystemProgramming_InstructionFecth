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
		// wait for event : �̺�Ʈ�� ������ thread�� ���� ���� ��Ű�� �ڽ��� �ٽ� waiting.
		// ȭ�鿡 ����Ŭ���ϴ� �κ�
		File path = new File("exe/");
		File[] fileList = path.listFiles();
		
		if(fileList.length > 0){
		    for(int i=0; i < fileList.length; i++){
		    	this.processManager.execute(this.fileManager.getFile("exe/"+fileList[i].getName())); // �ϴ� single tasking���� �����
		    }
		}
	}
}
