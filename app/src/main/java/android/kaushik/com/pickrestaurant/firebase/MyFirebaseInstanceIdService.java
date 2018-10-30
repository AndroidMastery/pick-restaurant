package android.kaushik.com.pickrestaurant.firebase;

import android.content.SharedPreferences;
import android.kaushik.com.pickrestaurant.Constants;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private final String TAG = "MyFirebaseInstanceIdService";
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        String username = sharedPreferences.getString(Constants.CURRENT_USERNAME, "");

        // save this token in firebase database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child(username).child(Constants.FCM_TOKEN).setValue(refreshedToken);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.FCM_TOKEN, refreshedToken);
        editor.putString(Constants.API_KEY_KEY, Constants.API_KEY);
        editor.apply();
    }
}
