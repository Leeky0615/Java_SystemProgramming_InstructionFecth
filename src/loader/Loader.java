package loader;

import java.io.File;

import ioManager.IOManager;
import memoryManager.MemoryManager;
import processManager.Process;

public class Loader {
	
	public Process load(File file,IOManager ioManager,MemoryManager memoryManager) {
		return memoryManager.allocate(file,ioManager);
	}

}