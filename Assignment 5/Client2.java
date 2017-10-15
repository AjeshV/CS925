package echoclient;
import java.io.*;
import java.net.*;
public class EchoClient{
  static final String APP_NAME="EchoClient";
  static final String FILE_NAME="Input.txt";
  static final int PORT=7001;
  static ServerSocket serverSocket;
  static int port=PORT;
  Socket clientSocket;
  String host = "fd50:4abe:b885:3::2";
  BufferedReader is=null;
  BufferedWriter os=null;
 public static void main(String[] args) throws IOException {
    if (usageOnly(args)) {
          System.exit(0);
     }processCommandLine(args);
    printMsg("Client running on port." + port + ".");
    //Start time
    long start = System.currentTimeMillis();
    System.out.println("Start: " + start);
   Socket echoSocket=null;
   PrintWriter out=null;
   BufferedReader in=null;
   try {
   FileInputStream file=new FileInputStream(FILE_NAME);
   DataInputStream fileinput=new DataInputStream(file);
   BufferedReader br=new BufferedReader(new InputStreamReader(fileinput));
   String strLine;
   while ((strLine = br.readLine())!= null) {
  System.out.println(strLine);
  try {
    echoSocket=new Socket(host, port);
	out = new PrintWriter(echoSocket.getOutputStream(), true);
	in = new BufferedReader(new InputStreamReader(
	echoSocket.getInputStream()));
	}catch(UnknownHostException e){
	System.err.println("Don't know about host: localhost.");
	System.exit(1);
	}catch(IOException e){
     	System.err.println("No Input/Output"
		+ "the connection to:" + host);
		System.exit(1);
		}
    out.println(strLine);
	System.out.println("Sent: " + strLine);
	System.out.println("Received: " + in.readLine());
  }//Close the input stream
	in.close();
	out.close();
	echoSocket.close();
	}catch (Exception e) {//Catch exception if any
	System.err.println("Error: " + e.getMessage());
  }//End Time
   long end = System.currentTimeMillis();
   System.out.println("End : " + end);
   long elapsedTime=end-start;
  //Total Time
  System.out.println("The process took approximately:" + elapsedTime + " milliseconds");
 }private static boolean usageOnly(String args[]) {
  if (args.length>1||(args.length==1
     && (args[0].equalsIgnoreCase("-usage")
     || args[0].equalsIgnoreCase("-help")
     || args[0].equalsIgnoreCase("-h")))) {
   System.out.println("Usage:java " + APP_NAME + " [<port>]");
   System.out.println("  The default port is " + port + ".");
    return true;
    }else{
    return false;
   }
 }private static void processCommandLine(String args[]) {
   if (args.length!=1){
     return;
 }port=Integer.parseInt(args[0]);
 if (port<1||port>65535){
       port=PORT;
     printMsg("Using port " + port + " instead.");
     }
 }private static void printMsg(String msg){
    System.out.println(APP_NAME + ":  " + msg);
    }
}