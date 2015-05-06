import java.io.*;
import java.net.*;

public class NSServerThread extends Thread {

	Socket client;
	BufferedReader in;
	PrintWriter out;

	public NSServerThread(Socket accept) throws IOException {
		this.client = accept;
		System.out.println("Accept connection from client");
		
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Could not get hostname from client");
			try {
				client.close();
			} catch (IOException e1) {
				System.out.println("Could not close client connection");
			}
		}
		
		String input, ip = "";
		InetAddress host;
		while ((input = in.readLine()) != null) {
			if (input.toLowerCase().substring(0, 4).equals("exit")||
                            input.toLowerCase().substring(0, 4).equals("quit"))
				break;
			
			System.out.println("Resolve host with: " + input);
			
			try {
				host = InetAddress.getByName(input.trim());
				ip = host.getHostAddress();
			} catch (UnknownHostException e) {
				ip = "Unable to resolve host " + input;
			}
			
			out.println(ip);
		}
		
		System.out.println("Close connection");
		in.close();
		out.close();
		client.close();
	}

	
	public static void main(String[] args) throws IOException {

	        int port = Integer.parseInt(args[0]);
		ServerSocket socket = new ServerSocket(port);
		boolean listening = true;

		System.out.println("Listening on port " + port);
		while (listening) {
			new NSServerThread(socket.accept()).start();
		}
	}
	
}
