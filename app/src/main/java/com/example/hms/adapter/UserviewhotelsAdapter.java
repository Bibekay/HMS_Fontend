package com.example.hms.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hms.R;
import com.example.hms.models.Hotels;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.hms.url.url.imagePath;

public class UserviewhotelsAdapter extends RecyclerView.Adapter  <UserviewhotelsAdapter.UserviewhotelsViewHolder> {


    Context mContext;
    List<Hotels> hotelsList;
    private List<Hotels> filteruserList;

    public UserviewhotelsAdapter(Context mContext, List<Hotels> filteruserList) {
        this.mContext = mContext;
        this.filteruserList = filteruserList;

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
