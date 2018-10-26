package android.kaushik.com.pickrestaurant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RestaurantViewAdapter extends RecyclerView.Adapter{

    private ArrayList<String> restaurantList;

    public RestaurantViewAdapter(ArrayList<String> restaurantList)
    {
        this.restaurantList = restaurantList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView restaurantItem;

        public ViewHolder(View view)
        {
            super(view);

            this.view = view;
            restaurantItem = view.findViewById(R.id.restaurant_item);
        }
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row, parent, false);
        return new ViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String restaurantName = restaurantList.get(position);
        TextView textView = holder.itemView.findViewById(R.id.restaurant_item);
        textView.setText(restaurantList.get(position));

    }

    public int getItemCount() {
        if(restaurantList != null)
        {
            return restaurantList.size();
        }
        return 0;
    }
}
