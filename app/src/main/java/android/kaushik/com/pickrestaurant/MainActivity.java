package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

//This is the Launcher activity. App execution starts from this activity

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public ArrayList<String> restaurantList = new ArrayList<String>();
    public String restaurantsListFileName = "restaurants_data.txt";
    public String TAG = "MainActivity";
    public SharedPreferences sharedPreferences;
    public final String SHARED_PREFERENCES = "android.kaushik.com.pickrestaurant";
    private final String APP_MODE_KEY = "APP_MODE";
    private final String APP_MODE_INDIVIDUAL = "INDIVIDUAL_MODE";
    private final String APP_MODE_GROUP = "GROUP_MODE";
    private final String GROUP_NAME_KEY = "GROUP_NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Team Lunch");
        setSupportActionBar(toolbar);

        // Use SharedPreferences to store data internally
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);

        // If username is not set, show user profile screen
        showUserProfileActivity(false);

        // If internal storage file exists, don't read from asset file.
        // else, read from asset file. Asset file should only be used for first time initialization.
        File filePath = new File(getFilesDir()+"/"+restaurantsListFileName);
        if(filePath.exists())
        {
            // read from internal storage file
            restaurantList = readFromInternalStorage(filePath);
        }
        else
        {
            restaurantList = loadRestaurantsList();
            // write to internal storage file
            writeToInternalStorage(filePath);
        }


        findViewById(R.id.pick_button).setOnClickListener(this);
        findViewById(R.id.add_restaurant).setOnClickListener(this);
        findViewById(R.id.show_all_button).setOnClickListener(this);
        findViewById(R.id.new_poll_fab_action).setOnClickListener(this);
        findViewById(R.id.add_user_fab_action).setOnClickListener(this);


    }

    public void showUserProfileActivity(boolean show)
    {   String username = sharedPreferences.getString("username", "");
        if(show) //TODO: Show=true is for development case only
        {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);

        }
        else {
            if(username.isEmpty())
            {
                // Intent is used to navigate to next activity (screen) and pass along required data.
                Intent userProfilesIntent = new Intent(this, UserProfileActivity.class);
                startActivity(userProfilesIntent);
            }
        }

    }



    public ArrayList<String> readFromInternalStorage(File file)
    {
        ArrayList<String> list = new ArrayList<String>();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            Log.i("ReadFromInternalStorage", file.getAbsolutePath());
            String str;
            while((str = bufferedReader.readLine()) != null)
            {
                list.add(str);
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            Log.e("MainActivity", "Error wile reading from internal storage");
        }

        return list;
    }

    public void writeToInternalStorage(File file){
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            Log.i("WriteToInternalStorage", file.getAbsolutePath());
            for(String restaurantName : restaurantList)
            {
                bufferedWriter.write(restaurantName+'\n');
            }
            bufferedWriter.flush();
            bufferedWriter.close();

        }
        catch(Exception e)
        {
            Log.e("MainActivity", "Error wile writing to internal storage");
        }
    }

    public ArrayList<String> loadRestaurantsList()
    {   ArrayList<String> restaurantList = new ArrayList<String>();
        AssetManager assetManager = getAssets();
        InputStream inputStream;
        try{
            inputStream = assetManager.open("restaurants_list.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while((str = bufferedReader.readLine()) != null)
            {
                restaurantList.add(str);
            }

        }
        catch(IOException io)
        {
            Log.e("MainActivity", "Error wile loading from assets file");
        }
        return restaurantList;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.pick_button:
                show_selected_restaurant();
                break;
            case R.id.show_all_button:
                show_all_restaurants();
                break;
            case R.id.add_restaurant:
                add_new_restaurant();
                break;
            case R.id.add_user_fab_action:
                goToActivity(AddNewUserActivity.class);
                break;
            case R.id.new_poll_fab_action:
                goToActivity(CreatePollActivity.class);
                break;
            default:
                break;
        }


    }

    public void goToActivity(Class classname)
    {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }


    public void add_new_restaurant()
    {
        Intent intent = new Intent(this, AddRestaurant.class);
        intent.putStringArrayListExtra("restaurantList", restaurantList);
        intent.putExtra("internal_storage", getFilesDir()+"/"+restaurantsListFileName);
        startActivity(intent);

    }

    public void show_all_restaurants()
    {
        Intent intent = new Intent(this, ShowAllRestaurants.class);
        intent.putStringArrayListExtra("restaurantList", restaurantList);
        startActivity(intent);
    }


    public void show_selected_restaurant(){
        Random random = new Random();
        int i = random.nextInt(restaurantList.size());
        String restaurantName = restaurantList.get(i);
        Log.i("Restaurant picked : ", restaurantName);
        Intent intent = new Intent(this, ShowRestaurant.class);
        intent.putExtra("restaurant", restaurantName);
        intent.putStringArrayListExtra("restaurantList", restaurantList);
        startActivity(intent);
    }
}
