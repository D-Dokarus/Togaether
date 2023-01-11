package togaether.BL;

import java.io.*;
import java.net.InetAddress;
import java.util.Observable;

import com.lloseng.ocsf.server.*;

public class ChatServer implements java.util.Observer {

  final public static int DEFAULT_PORT = 5555;
  ObservableOriginatorServer service;

  //Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public ChatServer(int port) throws IOException {
    this.service = new ObservableOriginatorServer(port);
    this.service.addObserver(this);
    this.service.listen();
  }


  //Instance methods ************************************************

  @Override
  public void update(Observable o, Object arg) {
    Object message = ((OriginatorMessage) arg).getMessage();
    ConnectionToClient originator = ((OriginatorMessage) arg).getOriginator();
    this.handleMessageFromClient(message, originator);
  }

  protected void clientConnected(ConnectionToClient client) {}

  protected synchronized void clientDisconnected(ConnectionToClient client) {}

  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client) {
    if(client != null) {
      String s = (String) msg;
      if(client.getInfo("id") == null) //Premier message reçu, id du voyage
        client.setInfo("id", s);
      else
        this.sendToTravel(s, (String) client.getInfo("id"));
      //this.service.sendToAllClients(msg);
    }
  }

  public void sendToTravel(String msg, String id) {
    Thread[] clientThreadList = this.service.getClientConnections();

    for (int i=0; i<clientThreadList.length; i++) {
      if(((ConnectionToClient)clientThreadList[i]).getInfo("id").equals(id)) {
        try {
          ((ConnectionToClient)clientThreadList[i]).sendToClient(msg);
        }
        catch (Exception ex) {}
      }
    }
  }

  public static void main(String[] args) {
    try{
      new ChatServer(DEFAULT_PORT);
      System.out.println(InetAddress.getLocalHost().toString().split("/")[1]);
    }
    catch (Exception ex) {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
