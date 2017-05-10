/**
 * Simple HTTP handler for testing ChronoTimer
 */
package Server;
import Chrono.RunData;
import Chrono.Racer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ChronoServer {

	// a shared area where we get the POST data and then use it in the other
	// handler
	static String sharedResponse = "";
	static String localResponse = "";
	static boolean gotMessageFlag = false;

	static ArrayList<RunData> rList = new ArrayList<RunData>();
	static String fileName = "server.txt";
	static Scanner scan;
	static NameMap nameMap = new NameMap();
	
	public static void main(String[] args) throws Exception {

		try{
		scan = new Scanner(new File(fileName));
		}catch(Exception e){
			System.err.println("No Bib-Name pairs found.");
		}
		
		//Create NameMap database
		while(scan.hasNextLine()){
			String line = scan.nextLine();
			
			String[] parts = line.split(":");
			//System.out.println(parts[0]);
			int bibNum = Integer.parseInt(parts[0]);
			String name = parts[1];
			
			nameMap.put(bibNum, name);
			
			
			
			
		}
		
		// How to grab css
		// server.createContext("/style.css", new StaticFileServer());

		// set up a simple HTTP server on our local host, sets up socket
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		// create a context to get the request to display the results
		server.createContext("/displayresults", new DisplayHandler());

		server.createContext("/displayresults/css", new CSSHandler());

		// create a context to get the request for the POST
		server.createContext("/sendresults", new PostHandler());
		server.setExecutor(null); // creates a default executor

		// get it going
		System.out.println("Starting Server...");
		server.start();
	}

	static class CSSHandler implements HttpHandler {

		public void handle(HttpExchange t) throws IOException {

			// This will have to be used for reading, uncomment when we start
			// implementing - WP
			String response = "";
//			t.getResponseHeaders().set("Content-Type", "text/plain; charset=" + response);

			//You should probably use a file reader...
			response += "table{width: 800px; text-align:center;}";
			response += "tr:header{color:blue;}";
			response += "tr.Eemployee, tr.Oemployee{font-size: 14px;}";
			response += "tr.Eemployee{ background-color:#efefef;}";
			response += "th{font-size: 22px;border-bottom: 2px solid #111111;border-top: 1px solid #999;}";

			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	// call will be executed when you type route in web browser/or from client.
	// Executed every time someone goes to site
	static class DisplayHandler implements HttpHandler {

		public void handle(HttpExchange t) throws IOException {

			// This will have to be used for reading, uncomment when we start
			// implementing - WP
			String response = "UTF-8";
			t.getResponseHeaders().set("Content-Type", "text/html; charset=" + response);
			response = // TODO: Response+= previously, now changed
					"<!DOCTYPE html>  " + "<html>" + "<head>"
							+ "<link href=\"/displayresults/css\" rel=\"stylesheet\" type=\"text/css\">" + "</head>" + "<style>"
							+ "</style> <body>";
			
			//Now that we have begun the body, lets add our tables:
			for (RunData run : rList) {
				response += "<h2>ChronoTimer Race #" + run.getId() + " Results</h2>" + "	<table>"
						+ "   <tr class=\"header\">" + "       <th>Time</th>" + "       <th>Number</th>"
						+ "       <th>Name</th>" +  "</tr>";
				
				boolean oddRacer = true;
				for (Racer racer : run.getRacers()) {
					if (oddRacer) {
						response += "<tr class=\"Oemployee\">";
						oddRacer = false;
					} else {
						response += "<tr class=\"Eemployee\">";
						oddRacer = true;
					}
					response += "<td>" + racer.getTimer() + "</td><td>"
							+ racer.getNumber() + "</td><td>" 
							+ nameMap.get(racer.getNumber()) + "</td>" + "</tr>";
				}
				response += "	</table><br><br>";
			}
			
			response += "</body>" + "</html>";

			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	// start with post and grab information correctly to store information
	// correctly
	static class PostHandler implements HttpHandler {
		public void handle(HttpExchange transmission) throws IOException {

			// shared data that is used with other handlers
			sharedResponse = "";

			// set up a stream to read the body of the request, what we get from
			// client
			InputStream inputStr = transmission.getRequestBody();

			// set up a stream to write out the body of the response
			OutputStream outputStream = transmission.getResponseBody();

			// string to hold the result of reading in the request
			StringBuilder sb = new StringBuilder();

			// read the characters from the request byte by byte and build up
			// the sharedResponse
			int nextChar = inputStr.read();
			while (nextChar > -1) {
				// should be whatever the client sends to us
				sharedResponse += ((char) nextChar);
				nextChar = inputStr.read();
			}
			Gson g = new Gson();
			// parse command TODO, need better way to write to file possibly
			if (sharedResponse.substring(0, 3).equals("ADD")) { // ADD response
				sharedResponse = sharedResponse.substring(4);
				
//				ServerRacer r = g.fromJson(sharedResponse, new TypeToken<ServerRacer>(){
//				}.getType());
				RunData r = g.fromJson(sharedResponse, new TypeToken<RunData>(){
					}.getType());
				
				rList.add(r);

			}

			else {
				System.err.println("Unrecognized command.");
			}

			// create our response String to use in other handler
			sharedResponse = sharedResponse + sb.toString();

			// respond to the POST with ROGER
			String postResponse = "POST REQUEST RECEIVED";

			// System.out.println("" + fromJson.get(0).HTMLEmployee());
			System.out.println("response: " + sharedResponse);

			// assume that stuff works all the time
			transmission.sendResponseHeaders(300, postResponse.length());

			// write it and return it
			outputStream.write(postResponse.getBytes());

			outputStream.close();
		}
	}

}