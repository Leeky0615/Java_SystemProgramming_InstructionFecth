package pc;

import board.BIOS;

public class Main {

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.initialize();
		BIOS bios = new BIOS(mainFrame);
		bios.run();
	}

}
