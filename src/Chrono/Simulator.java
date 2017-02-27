package Chrono;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simulator {

	public static void main(String[] args) throws InterruptedException {
		String fileFile = "transactions.txt";
		Scanner scan = new Scanner (System.in);
		System.out.print("Are you entering a file for this simulation? (Y/N)");
		String resp = scan.nextLine();

		if(resp.toLowerCase().charAt(0) == 'y'){
			try {
				scan = new Scanner(new File(fileFile));
			} catch (FileNotFoundException e) {
				System.err.println("File Not Found");
				scan = new Scanner(System.in);
			}
		}
		
		Controller controller = new Controller();

		// Main event loop
		while (controller.isRunning() && scan.hasNextLine()) {
			int id = 0;
			String cmd = scan.nextLine();
			System.out.println(cmd);
			System.out.println();
			ActionEvent cmdAction = new ActionEvent(scan, id, cmd);
			id++;
			controller.actionPerformed(cmdAction);
		}

	}

}
