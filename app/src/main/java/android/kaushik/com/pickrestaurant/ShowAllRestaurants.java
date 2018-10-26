package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowAllRestaurants extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<String> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_restaurants);

        //specify an adapter
        Intent intent = getIntent();
        restaurantList = intent.getStringArrayListExtra("restaurantList");

        RecyclerView recyclerView = findViewById(R.id.show_all_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RestaurantViewAdapter(restaurantList));

    }

    @Override
    public void onClick(View view) {

    }
}
