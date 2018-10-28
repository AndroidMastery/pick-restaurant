package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GroupSetupSkipActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = "GroupSetupSkipActivity";
    private SharedPreferences sharedPreferences;
    public final String SHARED_PREFERENCES = "android.kaushik.com.pickrestaurant";
    private final String APP_MODE_KEY = "APP_MODE";
    private final String APP_MODE_INDIVIDUAL = "INDIVIDUAL_MODE";
    private final String APP_MODE_GROUP = "GROUP_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_setup_skip);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        //Create OnClick listeners for each button
        findViewById(R.id.join_group_button).setOnClickListener(this);
        findViewById(R.id.create_group_button).setOnClickListener(this);
        findViewById(R.id.group_setup_skip_button).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.group_setup_skip_button:
                Log.i(TAG, "Skip button clicked");

                // Setup app mode as individual
                addKeyValueToSharedPreferences(APP_MODE_KEY, APP_MODE_INDIVIDUAL);
                showActivity(MainActivity.class);
                break;
            case R.id.join_group_button:
                Log.i(TAG, "Join Group button clicked");
                showActivity(JoinGroupActivity.class);
                break;
            case R.id.create_group_button:
                Log.i(TAG, "Create Group button clicked");
                showActivity(CreateGroupActivity.class);
                break;
            default:
                break;
        }
    }

    private void addKeyValueToSharedPreferences(String key, String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void showActivity(Class classname)
    {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
}
