package togaether.BL.Tools;

import togaether.BL.Model.Friend;
import togaether.BL.Model.User;

import java.util.Comparator;

public class FriendComparator implements Comparator {

    ComparatorType method;
    User user;

    public FriendComparator(ComparatorType ct, User user){
        this.method = ct;
        this.user = user;
    }


    @Override
    public int compare(Object o1, Object o2) {
        int result = 0;
        if(o1 instanceof Friend f1 && o2 instanceof Friend f2){
            User u1 = null;
            User u2 = null;
            if(f1.getUser1().getId() != user.getId()){
                u1 = f1.getUser1();
            }else{
                u1 = f1.getUser2();
            }
            if(f2.getUser1().getId() != user.getId()){
                u2 = f2.getUser1();
            }else{
                u2 = f2.getUser2();
            }

            if(this.method == ComparatorType.PseudoInvAlph){
                result = u1.getPseudo().compareTo(u2.getPseudo());
            }else if(this.method == ComparatorType.PseudoAlph) {
                result = -1 * u1.getPseudo().compareTo(u2.getPseudo());
            }else if(this.method == ComparatorType.CountryInvAlph){
                result = u1.getCountry().compareTo(u2.getCountry());
            }else if(this.method == ComparatorType.CountryAlph){
                result = -1 * u1.getCountry().compareTo(u2.getCountry());
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
