package android.kaushik.com.pickrestaurant.firebase;

import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    private String username;
    private String firstname;
    private String lastname;
    private String groupName;

    User(){ }

    public User(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.groupName = "(Not Available)";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
