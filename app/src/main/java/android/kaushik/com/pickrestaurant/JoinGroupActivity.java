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
    public final String SHARED_PREFERENCES = "android.kaushik.com.pickrestaurant";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private final String GROUP_NAME_KEY = "GROUP_NAME";
    private final String APP_MODE_KEY = "APP_MODE";
    private final String APP_MODE_GROUP = "GROUP_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

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
        String username = sharedPreferences.getString("username", "");

        // Query to get the list of users with username equal to given username
        Query usersList = databaseReference.child("users").orderByChild("username").equalTo(username);
        usersList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    //Update groupName for all the users resulted in the query
                    snapshot.getRef().child("groupName").setValue(groupName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(GROUP_NAME_KEY, groupName);
        sharedPreferencesEditor.putString(APP_MODE_KEY, APP_MODE_GROUP);
        sharedPreferencesEditor.apply();

    }

    private void showActivity(Class classname)
    {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
}
