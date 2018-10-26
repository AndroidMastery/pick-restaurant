package android.kaushik.com.pickrestaurant;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public ArrayList<String> restaurantList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantList = loadRestaurantsList();
        Button pick_button = (Button) findViewById(R.id.pick_button);
        pick_button.setOnClickListener(this);
        Button add_restaurant_button = (Button) findViewById(R.id.add_restaurant);
        add_restaurant_button.setOnClickListener(this);
        Button show_all_restaurants_button = (Button) findViewById(R.id.show_all_button);
        show_all_restaurants_button.setOnClickListener(this);


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
            // catch the exception here
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
