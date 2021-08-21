package fileManager;

import java.io.File;


public class FileManager {

	public File getFile(String fileName) {
		File file = new File(fileName);
		return file;
	}	
}
