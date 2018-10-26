package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ShowRestaurant extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurant);

        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("restaurant");
        ArrayList<String> restaurantList = intent.getStringArrayListExtra("restaurantList");
        TextView textView = (TextView) findViewById(R.id.show_restaurant);
        textView.setText(restaurantName);

        Button try_again_button = (Button) findViewById(R.id.try_again_button);
        try_again_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.try_again_button:
                pick_restaurant();
                break;
            default:
                break;
        }
    }

    public void pick_restaurant()
    {
        Intent intent = getIntent();
        ArrayList<String> restaurantList = intent.getStringArrayListExtra("restaurantList");

        Random random = new Random();
        int i = random.nextInt(restaurantList.size());
        String restaurantName = restaurantList.get(i);
        TextView textView = (TextView) findViewById(R.id.show_restaurant);
        textView.setText(restaurantName);

    }
}
