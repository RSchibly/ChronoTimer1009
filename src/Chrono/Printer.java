package Chrono;

public class Printer {

    public Printer(){

	}

    public void print(String string){
        //Does nothing, we don't have a physical printer. This was and still is a stupid idea.
        //We could output this to a file so it's not so useless and pretend the text file is the receipt...
        System.out.println("PRINTER: "+string);
    }
}