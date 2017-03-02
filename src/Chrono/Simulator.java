package Chrono;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Scanner;

public class Simulator {

	public static void main(String[] args) throws InterruptedException {
		boolean isFile = false;
		String fileFile = "testOutOfRun.txt";
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
		
		Controller controller = new Controller();

		// Main event loop
		while (scan.hasNextLine()) {
			ActionEvent cmdAction = null;
			int id = ActionEvent.ACTION_FIRST;
			String cmd = scan.nextLine().toUpperCase();
			
			if(isFile){
				String parsedTime = cmd.substring(0, 10).trim();
				String parsedCmd = cmd.substring(10).trim();
			
				System.out.println(parsedCmd);
				
				cmdAction = new ActionEvent(scan, id++, parsedCmd);
				controller.actionPerformed(new ActionEvent(scan, id++, "TIME " + parsedTime));
			}
			else {
				controller.actionPerformed(new ActionEvent(scan, id++, "TIME "
						+ LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + ":" + LocalTime.now().getSecond() + ".0"));
				cmdAction = new ActionEvent(scan, id++, cmd);
			}

			//To make sure id doesn't go out of acceptable bounds
			if(id > ActionEvent.ACTION_LAST) id = ActionEvent.ACTION_FIRST;
			controller.actionPerformed(cmdAction);
			
		}

	}

}
