package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddNewUserActivity extends AppCompatActivity implements View.OnClickListener{


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final String TAG = "AddNewUserActivity";
    public final String SHARED_PREFERENCES = "android.kaushik.com.pickrestaurant";
    private SharedPreferences sharedPreferences;
    private final String GROUP_NAME_KEY = "GROUP_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        findViewById(R.id.add_new_user_button).setOnClickListener(this);
        findViewById(R.id.invite_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add_new_user_button:
                addNewUserToGroup();
                break;
            case R.id.invite_button:
                sendEmailInvite();
                break;
            default:
                break;
        }
    }

    public void sendEmailInvite()
    {
        TextView textView = findViewById(R.id.invite_email_address);
        String to_email = textView.getText().toString();
        String current_user = sharedPreferences.getString("firstname", "");
        String subject = current_user + " is inviting to join a group in Team Lunch App";
        String current_group = sharedPreferences.getString("groupName", "");
        String message = "Hi there, \n\n  " +
                "This is an invite to join " + current_group + "in Team Lunch App. \n\n" +
                "Download the app from <a href=\"\">here</>. \n" +
                "Enter " + current_group.toUpperCase() + "while joining the group. \n\n" +
                "Thanks,\n" +
                current_user;


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setDataAndType(Uri.parse("mailto:"), "text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{to_email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT   , message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNewUserToGroup()
    {
        TextView textView = findViewById(R.id.new_user_username);
        String username = textView.getText().toString();

        Query query = databaseReference.child("users").orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount() == 0)
                {
                    Toast.makeText(AddNewUserActivity.this, "User not found. Send an Invite", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        //Update groupName for all the users resulted in the query
                        String groupName = sharedPreferences.getString(GROUP_NAME_KEY, "");
                        snapshot.getRef().child("groupName").setValue(groupName);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Go to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
