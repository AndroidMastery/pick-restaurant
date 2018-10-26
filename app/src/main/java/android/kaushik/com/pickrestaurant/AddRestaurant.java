package android.kaushik.com.pickrestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class AddRestaurant extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<String> restaurantList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        Button save_restaurant_button = (Button) findViewById(R.id.save_restaurant_button);
        save_restaurant_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        TextView textView = (TextView) findViewById(R.id.restaurant_name);
        String restaurant_name = textView.getText().toString();
        Intent getIntent = getIntent();
        restaurantList = getIntent.getStringArrayListExtra("restaurantList");
        String internalStorageFile = getIntent.getStringExtra("internal_storage");
        restaurantList.add(restaurant_name);

        writeToInternalStorage(new File(internalStorageFile));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

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
}
