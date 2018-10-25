import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Messenger_Client implements Runnable {

  private static Socket clientSocket = null;
  private static PrintStream os = null;
  private static BufferedReader is = null;
  private static BufferedReader inputLine = null;
  private static boolean closed = false;
  
  public static void main(String[] args) {

	//Select port and ip
    int portNumber = 0;
    String host = " ";

    if (args.length < 2) {
    	System.out.println("Usage: java Messenger_Client <host> <portNumber>");
    } else {
      host = args[0];
      portNumber = Integer.valueOf(args[1]).intValue();
    }

    //attempt to open client stream & input/output
    try {
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new PrintStream(clientSocket.getOutputStream());
      is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    } catch (UnknownHostException e) {
      System.err.println("Unknown host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't connect to host "+ host);
    }
    
    //if everything worked up to here
    if (clientSocket != null && os != null && is != null) {
      try {
        //create a client thread
        new Thread(new Messenger_Client()).start();
        //read lines from server
        while (!closed) {
          os.println(inputLine.readLine().trim());
        }
        //close up everything
        os.close();
        is.close();
        clientSocket.close();
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }

  public void run() {
    String responseLine;
    try {
      while ((responseLine = is.readLine()) != null) {
    	//continually print lines from server
        System.out.println(responseLine);
        //if we receive a bye, then break
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }
      closed = true;
    } catch (IOException e) {
      System.err.println("IOException: " + e);
    }
  }
}