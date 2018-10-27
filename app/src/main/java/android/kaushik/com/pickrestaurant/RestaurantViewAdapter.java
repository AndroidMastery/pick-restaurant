package android.kaushik.com.pickrestaurant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class RestaurantViewAdapter extends RecyclerView.Adapter implements RecyclerView.OnClickListener{


    private ArrayList<String> restaurantList;

    public RestaurantViewAdapter(ArrayList<String> restaurantList)
    {
        this.restaurantList = restaurantList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView restaurantItem;
        public final Button removeRestaurantItem;

        public ViewHolder(View view)
        {
            super(view);

            this.view = view;
            restaurantItem = view.findViewById(R.id.restaurant_item);
            removeRestaurantItem = view.findViewById(R.id.remove_button);
        }
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row, parent, false);
        return new ViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView textView = holder.itemView.findViewById(R.id.restaurant_item);
        textView.setText(restaurantList.get(position));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.remove_button:
                Log.i("Adapter", "Remove button clicked");
        }
    }

    public int getItemCount() {
        if(restaurantList != null)
        {
            return restaurantList.size();
        }
        return 0;
    }
}
