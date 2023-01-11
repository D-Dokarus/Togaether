package togaether.BL;

import com.lloseng.ocsf.client.ObservableClient;
import com.lloseng.ocsf.server.ObservableServer;
import togaether.BL.Facade.TravelFacade;
import togaether.UI.Controller.ChatController;

import java.io.IOException;
import java.util.*;

public class ChatClient implements Observer {

  ChatController chatController;
  ObservableClient service;

  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param chatController The interface type variable.
   */

  public ChatClient(String host, int port, ChatController chatController) throws IOException
  {
    this.chatController = chatController;
    this.service = new ObservableClient("172.17.32.1", port);
    this.service.addObserver(this);

    //TO DO
    this.service.openConnection();
    this.service.sendToServer(""+ TravelFacade.getInstance().getTravel().getIdTravel());
  }


  //Instance methods ************************************************

  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) {
    String s = (String) msg;
    if(s.length() > 10) //Parce que le serveur s'amuse à envoyer aux autres le message automatique du client quand ils se connectent, c'est à dire l'id du voyage
      if(!s.equals(ObservableClient.CONNECTION_ESTABLISHED) && !s.equals(ObservableClient.CONNECTION_CLOSED) && !s.equals(ObservableServer.CLIENT_CONNECTED) && !s.equals(ObservableServer.CLIENT_DISCONNECTED))
        this.chatController.addMessage(s);
  }

  public void handleMessageFromChat(Object msg) {
    try {
      this.service.sendToServer(msg);
    } catch (IOException e) {
      this.chatController.displayInfo("Attention : le message n'a pas pu être envoyé au chat en direct (mais les autres utilisateurs verront le message si ils actualisent la page");
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    if(o instanceof ObservableClient)
      this.handleMessageFromServer(arg);
  }

  /**
   * This method handles all data coming from the controller
   *
   * @param message The message from the controller.
   */
  public void handleMessageFromChatController(String message) {
    try {
      this.service.sendToServer(message);
    }
    catch(IOException e) {
      this.chatController.displayInfo("Attention : Crash du chat en direct");
      quit();
    }
  }

  /**
   * This method terminates the client.
   */
  public void quit() {
    try {
      this.service.closeConnection();
    }
    catch(IOException e) {}
  }
}
