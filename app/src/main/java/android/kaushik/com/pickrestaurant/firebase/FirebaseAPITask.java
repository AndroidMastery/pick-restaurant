package android.kaushik.com.pickrestaurant.firebase;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseAPITask extends AsyncTask {
    private final String TAG = "FirebaseAPITask";
    private String api_key;
    private String token;

    public FirebaseAPITask(String api_key, String token)
    {

        Log.i(TAG, "Sending to firebase...");
        Log.i(TAG, "API Key : " + api_key);
        Log.i(TAG, "Token : " + token);

        this.api_key = api_key;
        this.token = token;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try
        {
            Log.i(TAG, "Trying to buzz team...");
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Authorization", api_key);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("to", token);

            JSONObject notificationObject = new JSONObject();

            notificationObject.put("title", "Push Notification from the app");
            notificationObject.put("body", "Test message");
            jsonObject.put("notification", notificationObject);
            Log.i(TAG, "JSON Object : " + jsonObject.toString());
            OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
            writer.write(jsonObject.toString());
            writer.flush();
            httpURLConnection.getInputStream();
            Log.i(TAG, "Buzz sent to token : " + token);
            Log.i(TAG, "Buzzed team successfully!");

        }
        catch (Exception e)
        {
            Log.i(TAG, "Buzz team failed!");
            e.printStackTrace();
        }
        return null;
    }
}
