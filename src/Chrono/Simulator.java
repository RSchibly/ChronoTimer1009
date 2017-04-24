package Chrono;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Simulator {

	public static void main(String[] args) {			
		boolean isFile = false;
		String fileFile = "GRP/grpTest.txt";
		Scanner scan = new Scanner (System.in);
		System.out.print("Are you entering a file for this simulation? (Y/N)");
		String resp = scan.nextLine();

		if(resp.toLowerCase().charAt(0) == 'y'){
			try {
				scan = new Scanner(new File(fileFile));
				isFile = true;
			} catch (FileNotFoundException e) {
				System.err.println("File Not Found");
				scan = new Scanner(System.in);
			}
		}
		
		Display display = new Display(!isFile);
		Printer printer = new Printer(display);
		Controller controller = new Controller(display, printer, isFile);	

		// Main event loop
		while (scan.hasNextLine()) {
			ActionEvent cmdAction = null;
			String cmd = scan.nextLine().toUpperCase();
			
			if(isFile){
				String parsedTime = cmd.substring(0, 10).trim();
				String parsedCmd = cmd.substring(10).trim();
			
				System.out.println(parsedCmd);
				
				cmdAction = new ActionEvent(scan, ActionEvent.ACTION_PERFORMED, parsedCmd);
				controller.actionPerformed(new ActionEvent(scan, ActionEvent.ACTION_PERFORMED, "TIME " + parsedTime));
			}
			else {
				
				cmdAction = new ActionEvent(scan, ActionEvent.ACTION_PERFORMED, cmd);
			}

			controller.actionPerformed(cmdAction);
			
			//Fixes java buffer issue with std streams
			System.out.flush();
			System.err.flush();
			
		}
				
		
		

	}

}
