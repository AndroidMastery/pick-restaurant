package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.app.SearchManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ShowRestaurant extends AppCompatActivity implements View.OnClickListener{

    private String current_restaurant;
    private ArrayList<String> restaurantList;
    private ArrayList<String> tempRestaurantList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurant);

        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("restaurant");
        current_restaurant = restaurantName;
        ArrayList<String> restaurantList = intent.getStringArrayListExtra("restaurantList");
        TextView textView = (TextView) findViewById(R.id.show_restaurant);
        textView.setText(restaurantName);

        Button try_again_button = (Button) findViewById(R.id.try_again_button);
        try_again_button.setOnClickListener(this);

        Button navigate_to_restaurant_button = (Button) findViewById(R.id.navigate_to_restaurant_button);
        navigate_to_restaurant_button.setOnClickListener(this);

        restaurantList = intent.getStringArrayListExtra("restaurantList");
        tempRestaurantList = restaurantList;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.try_again_button:
                pick_restaurant();
                break;
            case R.id.navigate_to_restaurant_button:
                navigate_to_restaurant();
                break;
            default:
                break;
        }
    }

    public void pick_restaurant()
    {

        if(!tempRestaurantList.isEmpty())
        {
            Random random = new Random();
            int i = random.nextInt(tempRestaurantList.size());
            String restaurantName = tempRestaurantList.get(i);
            current_restaurant = restaurantName;
            TextView textView = (TextView) findViewById(R.id.show_restaurant);
            textView.setText(restaurantName);
            tempRestaurantList.remove(i);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "No more restaurants in the list", Toast.LENGTH_SHORT );
            toast.show();
        }

    }



    public void navigate_to_restaurant()
    {
        String query = "Navigate me to " + current_restaurant;
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query); // query contains search string
        startActivity(intent);
    }
}
