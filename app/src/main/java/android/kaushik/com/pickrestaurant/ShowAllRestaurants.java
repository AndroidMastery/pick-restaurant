package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowAllRestaurants extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_restaurants);

        //specify an adapter
        Intent intent = getIntent();
        ArrayList<String> restaurantList = intent.getStringArrayListExtra("restaurantList");

        RecyclerView recyclerView = findViewById(R.id.show_all_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RestaurantViewAdapter(restaurantList));
    }
}
