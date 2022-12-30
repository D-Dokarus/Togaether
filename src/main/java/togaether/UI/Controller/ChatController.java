package togaether.UI.Controller;

import javafx.application.Platform;
import togaether.BL.ChatClient;
import togaether.BL.Facade.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import togaether.BL.Model.Message;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatController {
  @FXML
  private Button returnButton;
  @FXML
  private TextArea inputMessage;
  @FXML
  private ListView messageList;
  @FXML
  private Button sendButton;
  @FXML
  private Label infoLabel;

  private ChatClient chatClient = null;

  @FXML
  protected void initialize() {
    this.reloadMessages();
    try {
      this.chatClient = new ChatClient("127.0.0.1", 5555, this);
    } catch (IOException e) {
      this.displayInfo("Attention : connection au chat en direct échouée");
    }
  }

  public void reloadMessages() {
    ChatFacade chat = ChatFacade.getInstance();
    //TO DO
    //TravelFacade travel = TravelFacade.createInstance();
    //ArrayList<Message> liste = chat.getMessagesByTravelId(travel.getId());
    try {
      ArrayList<Message> liste = (ArrayList<Message>) chat.getMessagesByTravelId(1);
      for(Message m : liste) {
        addMessage(m.toString());
      }

    } catch (TravelNotFoundException e) {
      this.displayInfo("Erreur lors du chargement des données : Le voyage n'a pas été trouvé");
    } catch (DBNotFoundException e) {
      this.displayInfo("Erreur lors du chargement des données : La connection à la base de données a échoué");
    }
  }

  public void onReturnButtonClicked() {
    this.close();
    //TO DO
  }
  public void onSendButtonClicked() {
    String text = this.inputMessage.getText();
    if(text.length() == 0)
      this.displayInfo("Attention : Message vide");
    else if(text.length() > 255)
      this.displayInfo("Attention : Nombre de caractères trop grand (supérieur à 255)");
    else {
      ChatFacade chat = ChatFacade.getInstance();
      Boolean success = chat.sendMessage(text);
      if(success) {
        this.inputMessage.clear();
        //TravelDAO travelDB = fact.getTravelDAO();
        //TO DO
        //Travel t = travelDB.findByTravelId(id);
        Integer t = 1;
        if(chatClient!=null) chatClient.handleMessageFromChatController(new Message(0, t, UserFacade.getInstance().getConnectedUser(), text, new Timestamp(System.currentTimeMillis())).toString());
      }
      else
        this.displayInfo("Attention : Le message n'a pas pu être envoyé, veuillez réessayer");
    }
  }

  private String formatText(String str) {
    String userName = "";
    if(UserFacade.getInstance().getConnectedUser() != null)
      userName = UserFacade.getInstance().getConnectedUser().getName();
    LocalDateTime t = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
    String minute = ((t.getMinute()+"").length() > 1 ? ""+t.getMinute() : "0"+t.getMinute()); //car par exemple si il est 12h07, getMinute renvoie juste 7
    String date = t.getDayOfMonth()+"/"+t.getMonthValue()+"/"+t.getYear()+" "+t.getHour()+":"+ minute;
    String text = date + " " + userName + " : " + str;
    return text;
  }

  public void addMessage(String s) {
    Platform.runLater(() -> messageList.getItems().add(s));
  }

  public void displayInfo(String info) {
    this.infoLabel.setText(info);
  }
  public void close() {
    if(this.chatClient != null)
      this.chatClient.quit();
  }
}
