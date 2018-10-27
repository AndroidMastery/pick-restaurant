package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.kaushik.com.pickrestaurant.firebase.User;
import android.support.annotation.NonNull;
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
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use SharedPreferences to store data internally
        SharedPreferences sharedPreferences = getSharedPreferences("android.kaushik.com.pickrestaurant", MODE_PRIVATE);
        if(sharedPreferences.contains("username"))
            Log.i(TAG, sharedPreferences.getString("username", ""));
        else
        {
            Log.i(TAG, "Username does not exist. Showing UserProfile screen!");

            // Intent is used to navigate to next activity (screen) and pass along required data.
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }

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


        Button pick_button = (Button) findViewById(R.id.pick_button);
        pick_button.setOnClickListener(this);
        Button add_restaurant_button = (Button) findViewById(R.id.add_restaurant);
        add_restaurant_button.setOnClickListener(this);
        Button show_all_restaurants_button = (Button) findViewById(R.id.show_all_button);
        show_all_restaurants_button.setOnClickListener(this);


    }


    public void getUser(String userId)
    {
        final DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(userId);
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                TextView textView = (TextView) findViewById(R.id.username_id);
                textView.setText(user.getUsername());
                Log.i(TAG, "username set to : " + user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);

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
                Log.i("Button clicked: ", "Select Restaurant");
                show_selected_restaurant();
                break;
            case R.id.show_all_button:
                Log.i("Button clicked: ", "Show All Restaurants");
                show_all_restaurants();
                break;
            case R.id.add_restaurant:
                Log.i("Button clicked: ", "Add Restaurant");
                add_new_restaurant();
                break;
            default:
                break;
        }


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
