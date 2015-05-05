import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NSClient {
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = 
			new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedReader query = 
			new BufferedReader(new InputStreamReader(System.in));

                String hostName = args[2];		
                if(hostName != null) //Resolve only once
                {
		    out.println(hostName);
		    String response = in.readLine();
			
	  	    if (response == null) {
			System.out.println("No responese from NS Server");
  		    }
	            System.out.println(response);
                } else { //Resolve multi hostnames
		    String queryString, response;
		    System.out.println("Input hostname or `exit` to quit.");
		    System.out.print("> ");
		    while ((queryString = query.readLine()) != null) {
			out.println(queryString);
			response = in.readLine();
			
			if (response == null) {
				break;
			}
			
			System.out.println(response);
			System.out.print("> ");
		    }
		}
		in.close();
		out.close();
		socket.close();
	}
}
