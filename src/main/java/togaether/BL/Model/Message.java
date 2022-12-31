package togaether.BL.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message {
  private int id;
  private int travel;
  private Collaborator collaborator;
  private String content;
  private Timestamp date;


  public Message(int id, int travel, Collaborator collaborator, String content, Timestamp date) {
    this.id = id;
    this.travel = travel;
    this.collaborator = collaborator;
    this.content = content;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public int getTravel() {
    return travel;
  }

  public Collaborator getCollaborator() {
    return collaborator;
  }

  public String getContent() {
    return content;
  }

  public Timestamp getDate() {
    return date;
  }

  public String toString() {
    LocalDateTime t = this.getDate().toLocalDateTime();
    String minute = ((t.getMinute()+"").length() > 1 ? ""+t.getMinute() : "0"+t.getMinute()); //car par exemple si il est 12h07, getMinute renvoie juste 7
    String date = t.getDayOfMonth()+"/"+t.getMonthValue()+"/"+t.getYear()+" "+t.getHour()+":"+minute;
    String text = date + " " + this.getCollaborator().getName() + " : " + this.getContent();
    return text;
  }
}
