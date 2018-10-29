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
import android.widget.Toast;

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
        Log.i(TAG, "onCreate");
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

                User user = new User(this.getStringFromTextViewId(R.id.username_textview),
                        this.getStringFromTextViewId(R.id.firstname_textview),
                        this.getStringFromTextViewId(R.id.lastname_textview));

                // Add user details to firebase and saves username in shared preferences
                addUser(user);
                break;
        }

    }

    public String validateUser(User user)
    {

        String message;
        if(user.getUsername().isEmpty())
        {
             message = "ERROR: username is mandatory!";
            return message;
        }
        //TODO: Validate if user already exists
        message = "User details saved successfully!";
        return message;
    }

    public void addUser(User user)
    {
        Log.i(TAG, "addUser - start");
        String validateUserMessage = validateUser(user);
        if(!validateUserMessage.startsWith("ERROR"))
        {
            // Get database reference
            databaseReference = firebaseDatabase.getReference("users");
            databaseReference.push().setValue(user);

            sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putString("username",user.getUsername());
            sharedPreferencesEditor.putString("firstname", user.getFirstname());
            sharedPreferencesEditor.putString("lastname", user.getLastname());
            sharedPreferencesEditor.apply();
            Log.i(TAG, "username : " + user.getUsername() + " added to preferences successfully!");

            Toast toast = Toast.makeText(getApplicationContext(), validateUserMessage, Toast.LENGTH_LONG);
            toast.show();

            Intent intent = new Intent(this, GroupSetupSkipActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), validateUserMessage, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public String getStringFromTextViewId(int textViewId){
        TextView textView = findViewById(textViewId);
        return textView.getText().toString();
    }
}
