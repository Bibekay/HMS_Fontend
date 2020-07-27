package com.example.hms.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hms.R;
import com.example.hms.activities.users.UserviewhotelsdetailsActivity;
import com.example.hms.models.Hotels;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.hms.url.url.imagePath;

public class UserviewhotelsAdapter extends RecyclerView.Adapter  <UserviewhotelsAdapter.UserviewhotelsViewHolder> {


    Context mContext;
    List<Hotels> hotelsList;
    private List<Hotels> filterhotelList;

    public UserviewhotelsAdapter(Context mContext, List<Hotels> hotelsList) {
        this.mContext = mContext;
        this.hotelsList = hotelsList;
        filterhotelList = new ArrayList<>(hotelsList);

    }

    @NonNull
    @Override
    public UserviewhotelsAdapter.UserviewhotelsViewHolder onCreateViewHolder(@NonNull
                                                                               ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_userviewhotels_adapter,parent,false);
        return new UserviewhotelsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserviewhotelsAdapter.UserviewhotelsViewHolder holder, int i) {

        final Hotels hotels = hotelsList.get(i);

        String imgPath = imagePath + hotels.getHotel_image();
        Picasso.get().load(imgPath).into(holder.hotelImage);

        holder.hotelName.setText(hotels.getHotel_name());
        holder.description.setText(hotels.getDescription());
        holder.price.setText(hotels.getPrice());

        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openOderActivity = new Intent(mContext, UserviewhotelsdetailsActivity.class);
                openOderActivity.putExtra("id", hotels.get_id());
                openOderActivity.putExtra("hotel_name",hotels.getHotel_name());
                openOderActivity.putExtra("description",hotels.getDescription());
                openOderActivity.putExtra("price",hotels.getPrice());
                openOderActivity.putExtra("hotel_image", hotels.getHotel_image());

                mContext.startActivity(openOderActivity);


            }
        });




    }
        @Override
        public int getItemCount() {
            return hotelsList.size();
        }

        public class UserviewhotelsViewHolder extends RecyclerView.ViewHolder {

            ImageView hotelImage;
            TextView hotelName, description, price;
            Button viewMore;

            public UserviewhotelsViewHolder(@NonNull View itemView) {
                super(itemView);


                hotelImage = itemView.findViewById(R.id.iv_hotelimage);
                hotelName = itemView.findViewById(R.id.tv_hotel_name);
                description = itemView.findViewById(R.id.tv_description);
                price = itemView.findViewById(R.id.tv_price);
                viewMore = itemView.findViewById(R.id.btnViewMore);


            }
        }

    }
