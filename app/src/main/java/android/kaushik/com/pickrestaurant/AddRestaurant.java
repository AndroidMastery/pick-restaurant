package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AddRestaurant extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        Button save_restaurant_button = (Button) findViewById(R.id.save_restaurant_button);
        save_restaurant_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String restaurant_name = findViewById(R.id.restaurant_name).toString();

        save_restaurant();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void save_restaurant()
    {
        // Asset files cannot be edited. Write the updates to an internal storage

    }
}
