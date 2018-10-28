package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add_new_user_button:
                addNewUserToGroup();
                break;
            default:
                break;
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
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    //Update groupName for all the users resulted in the query
                    String groupName = sharedPreferences.getString(GROUP_NAME_KEY, "");
                    snapshot.getRef().child("groupName").setValue(groupName);
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
