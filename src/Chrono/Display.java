package Chrono;

import java.awt.EventQueue;

public class Display {

	boolean runGUI;
	Viewer gui;
	
	public Display() {
		this(true);
	}
	
	public Display(boolean runGUI){
		this.runGUI = runGUI;
		gui = null;
	}

	public void displayStr(String string){
		if(gui != null) gui.setStatus(string);
		System.out.println(string);
	}

	public void displayErr(String string) {
		if(gui != null) gui.setStatus(string);
		System.out.println("ERROR: " + string);
	}
	
	public void printLine(String line) {
		if(gui != null) gui.printLine(line);
		System.out.println("PRINTER: "+ line);
	}
	
	public void powerOff() {
		if(runGUI && gui != null) {
			gui.dispose();
			gui = null;
		}
	}
	
	public void powerOn(Controller controller) {
		if(runGUI && gui == null) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						gui = new Viewer(controller);
						gui.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}