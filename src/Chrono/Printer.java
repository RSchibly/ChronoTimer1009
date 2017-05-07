package Chrono;

public class Printer {
	Display display;
	boolean enabled;

	public Printer(Display display){
    	this.display = display;
    	this.enabled = true;
	}
	
   public boolean isEnabled() {
		return enabled;
   }

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void printLine(String line) {
		//We could output this to a file so this class is not so useless and pretend the text file is the receipt...
		if(enabled) display.printLine(line);	
	}
}