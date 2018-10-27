package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.kaushik.com.pickrestaurant.firebase.User;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

// This activity takes user details and stores them in firebase

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final String TAG = "UserProfileActivity";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        sharedPreferences = getSharedPreferences("android.kaushik.com.pickrestaurant", MODE_PRIVATE);

        Button save_button = findViewById(R.id.user_profile_save_button);
        save_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.user_profile_save_button:
                // This is to hide the message TextView that would show "user profile saved successfully" or "username is mandatory" error messages
                setMessageView(null);

                User user = new User(this.getStringFromTextViewId(R.id.username_textview),
                        this.getStringFromTextViewId(R.id.firstname_textview),
                        this.getStringFromTextViewId(R.id.lastname_textview));

                // Add user details to firebase and saves username in shared preferences
                addUser(user);
                break;
        }

    }


    private void setMessageView(String message)
    {
        Log.i(TAG, "setMessageView - start");
        TextView textView = findViewById(R.id.message_textview_hidden);
        if(message == null)
            textView.setVisibility(View.INVISIBLE);
        else{
            textView.setText(message);
            textView.setVisibility(View.VISIBLE);

            if(message.contains("success"))
                textView.setTextColor(Color.GREEN);
            else
                textView.setTextColor(Color.RED);
        }
        Log.i(TAG, "setMessageView - end");
    }

    public boolean validateUser(User user)
    {
        Log.i(TAG, "validateUser - start");
        String message;
        if(user.getUsername().isEmpty())
        {
             message = "username is mandatory!";
            setMessageView(message);
            Log.i(TAG, "validateUser - end");
            return false;
        }
        message = "User details saved successfully!";
        setMessageView(message);
        Log.i(TAG, "validateUser - end");
        return true;
    }

    public void addUser(User user)
    {
        Log.i(TAG, "addUser - start");
        if(validateUser(user))
        {
            // Get database reference
            databaseReference = firebaseDatabase.getReference("users");
            databaseReference.push().setValue(user);


            Log.i(TAG, "username entered: " + user.getUsername());
            sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putString("username",user.getUsername());
            sharedPreferencesEditor.apply();
            Log.i(TAG, "username : " + user.getUsername() + " added to preferences successfully!");
            Log.i(TAG, "addUser - end");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public String getStringFromTextViewId(int textViewId){
        TextView textView = findViewById(textViewId);
        return textView.getText().toString();
    }
}
