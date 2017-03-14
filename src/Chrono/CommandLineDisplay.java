package Chrono;

public class CommandLineDisplay implements Display {

	public CommandLineDisplay(){

	}

	public void display(String string){
		System.out.println(string);
	}

	@Override
	public void displayError(String string) {
		System.err.println(string);
		
	}

}