package togaether.BL.Model;

import java.sql.Date;
import java.time.LocalDate;

public class Friend {
    private User user1;
    private User user2;
    private Date beginningOfFriendship;

    public Friend(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Friend(User user1, User user2, Date beginningOfFriendship) {
        this.user1 = user1;
        this.user2 = user2;
        this.beginningOfFriendship = beginningOfFriendship;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Date getBeginningOfFriendship() {
        return beginningOfFriendship;
    }

}
