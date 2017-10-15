package EchoServer;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class EchoServer extends Thread {
   static final String APP_NAME="EchoServer";
   static final int PORT=7001;
   static ServerSocket serverSocket;
   static int port=PORT;
   Socket clientSocket;
   BufferedReader is=null;
   BufferedWriter os=null;
   public EchoServer(Socket cs) {
     clientSocket=cs;
   }public static void main(String args[]){
    if (usageOnly(args)){
      System.exit(0);
    }initialize(args);
    printMsg("Server running on port: " + port + "...Ready to accept connections...");
    while (true) {
	//Start time
    long start = System.currentTimeMillis();
    System.out.println("Start: " + start);
    try {
    Socket clientSocket=serverSocket.accept();
     EchoServer es=new EchoServer(clientSocket);
      es.start();
	//Catch exception if any
    }catch (IOException e){ 
      printMsg("Cannot accept client connection.");
     }
   }}public void run(){
     processClientRequest();
  }private static boolean usageOnly(String args[]){
    if (args.length>1||(args.length==1
       && (args[0].equalsIgnoreCase("-usage")
       || args[0].equalsIgnoreCase("-help")
       || args[0].equalsIgnoreCase("-h")))) {
    System.out.println("Usage:  java " + APP_NAME + " [<port>]");
    System.out.println("Default port is " + port + ".");
    return true;
    }else{
    return false;
   }
  }private static void initialize(String args[]) {
   processCommandLine(args);
  try{serverSocket=new ServerSocket(port);
   }catch(IOException e) {
     printMsg("Cannot create server socket "
        + "on port:  " + port + ".  Exiting...");
     System.exit(0);
    }
  }private void processClientRequest() {
 try{os=new BufferedWriter(
     new OutputStreamWriter(clientSocket.getOutputStream()));
    is=new BufferedReader(
     new InputStreamReader(clientSocket.getInputStream()));
    }catch(IOException e) { //Catch exception if any
   printMsg("Cannot handle client connection.");
   cleanup();
   return;
   }try{
   String input=is.readLine();
   if (input!=null){
    input=APP_NAME+": "+input+" "+getDateTime();
    os.write(input, 0, input.length());
    os.flush();
   }
  }catch(IOException e){ //handle file access errors
    printMsg("I/O error while processing the file.");
   }
        cleanup();
    }private void cleanup(){
     try {
     if (is!=null) {
         is.close();
     }if (os!=null) {
     os.close();
    }if (clientSocket!=null) {
      clientSocket.close();
    }
   }catch (IOException e) {  //Catch exception if any
    printMsg("Input or Output Error while closing connections.");
   }
   //End Time
   long end=System.currentTimeMillis();
   System.out.println("End : " + end);
   long elapsedTime=end-start;
  //Total Time
  System.out.println("The process took approximately:" + elapsedTime + " milliseconds");
 }private static void processCommandLine(String args[]) {
   if (args.length!=1) {
     return;
  }port=Integer.parseInt(args[0]);
  if (port<1||port>65535){
    port=PORT;
    printMsg("Using Port " + port + " instead.");
   }
 }private static void printMsg(String msg){
   System.out.println(APP_NAME + ":  " + msg);
    }private String getDateTime() {
      DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Date date=new Date();
      return dateFormat.format(date);
    }
}