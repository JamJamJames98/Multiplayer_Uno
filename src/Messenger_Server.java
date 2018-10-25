import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;

public class Messenger_Server {

  private static ServerSocket serverSocket = null;
  private static Socket clientSocket = null;
  private static final int maxClientsCount = 10;
  private static final clientThread_Msg[] threads = new clientThread_Msg[maxClientsCount];

  public static void main(String args[]) {
	  
	//selected port number
    int portNumber = 0;
    if (args.length < 1) {
      System.out.println("Usage: java Messenger_Server <portNumber>");
    } else {
      portNumber = Integer.valueOf(args[0]).intValue();
    }
    
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println("IOException: " + e);
    }

    while (true) {
      try {
        clientSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxClientsCount; i++) {
          if (threads[i] == null) {
            (threads[i] = new clientThread_Msg(clientSocket, threads)).start();
            break;
          }
        }
        if (i == maxClientsCount) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}

class clientThread_Msg extends Thread {

  private String clientName = null;
  private BufferedReader is = null;
  private PrintStream os = null;
  private Socket clientSocket = null;
  private final clientThread_Msg[] threads;
  private int maxClientsCount;
  public static String purple = "\u001B[1;35m";
  public static String default_setting = "\u001B[0m";
  

  public clientThread_Msg(Socket clientSocket, clientThread_Msg[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
  }

  public void run() {
    int maxClientsCount = this.maxClientsCount;
    clientThread_Msg[] threads = this.threads;

    try {
      is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      os = new PrintStream(clientSocket.getOutputStream());
      String name = " ";
      while (true) {
        os.println("Enter your name.");
        name = is.readLine().trim();
        if (name.indexOf('@') == -1) {
        	  System.out.println("User " + name + " has joined");
          break;
        } else {
          os.println("The name should not contain '@' character.");
        }
      }

      os.println("Welcome " + name + " to our chat room.\nTo leave enter /quit in a new line.");
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] == this) {
            clientName = "@" + name;
            break;
          }
        }
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] != this) {
            threads[i].os.println("*** A new user " + name + " entered the chat room !!! ***");
          }
        }
      }
      while (true) {
        String line = is.readLine();
        if (line.startsWith("/quit")) {
        	  System.out.println("User " + name + " has quit");
          break;
        }
        //handle private messages
        if (line.startsWith("@")) {
          String[] words = line.split("\\s", 2);
          if (words.length > 1 && words[1] != null) {
            words[1] = words[1].trim();
            if (!words[1].isEmpty()) {
              synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                  if (threads[i] != null && threads[i] != this
                      && threads[i].clientName != null
                      && threads[i].clientName.equals(words[0])) {
                	    threads[i].os.println(purple + "Private Message From " + name + " Recieved:" + default_setting);
                    threads[i].os.println(purple + "<" + name + "> " + words[1] + default_setting);
                    //confirm message was sent
                    this.os.println(purple + "Private Message Sent To " + threads[i].clientName + default_setting);
                    this.os.println(purple + "<" + name + "> " + words[1] + default_setting);
                    break;
                  }
                }
              }
            }
          }
        } else {
          //not a private message
          synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
              if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println("<" + name + "> " + line);
              }
            }
          }
        }
      }
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] != null && threads[i] != this
              && threads[i].clientName != null) {
            threads[i].os.println("*** The user " + name
                + " is leaving the chat room !!! ***");
          }
        }
      }
      os.println("*** Bye " + name + " ***");

      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
    }
  }
}