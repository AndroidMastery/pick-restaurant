package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.kaushik.com.pickrestaurant.firebase.User;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoinGroupActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final String TAG = "UserProfileActivity";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private final String APP_MODE_KEY = "APP_MODE";
    private final String APP_MODE_GROUP = "GROUP_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);

        findViewById(R.id.join_group_save_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.join_group_save_button:
                join_user_to_group();
                showActivity(MainActivity.class);
                break;
            default:
                break;
        }
    }

    public void join_user_to_group(){
        TextView textView = (TextView) findViewById(R.id.group_name_text_box);
        final String groupName = textView.getText().toString();
        String username = sharedPreferences.getString(Constants.FCM_USERNAME, "");

        // Query to get the list of users with username equal to given username
        Query usersList = databaseReference.child(Constants.FCM_USERS)
                .orderByChild(Constants.FCM_USERNAME).equalTo(username);
        usersList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    //Update groupName for all the users resulted in the query
                    snapshot.getRef().child(Constants.FCM_GROUPNAME).setValue(groupName);
                    sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.putString(Constants.FCM_GROUPNAME, groupName);
                    sharedPreferencesEditor.putString(APP_MODE_KEY, APP_MODE_GROUP);
                    sharedPreferencesEditor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void showActivity(Class classname)
    {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
}
