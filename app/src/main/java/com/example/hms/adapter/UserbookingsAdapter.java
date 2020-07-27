package com.example.hms.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hms.R;
import com.example.hms.models.Bookings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.hms.url.url.imagePath;

public class UserbookingsAdapter extends  RecyclerView.Adapter<UserbookingsAdapter.UserbookingsViewHolder>{
    Context mContext;
    List<Bookings> bookingsList;
    private List<Bookings> filterbookingLists ;

    public UserbookingsAdapter(Context mContext, List<Bookings> bookingsList) {
        this.mContext = mContext;
        this.bookingsList = bookingsList;
        filterbookingLists = new ArrayList<>(bookingsList);
    }

    @NonNull
    @Override
    public UserbookingsAdapter.UserbookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_userbookings_adapter, parent, false);
        return new UserbookingsViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull UserbookingsAdapter.UserbookingsViewHolder holder,  int i) {

        final Bookings bookings = bookingsList.get(i);

        String imgPath = imagePath+bookings.getHotel().getHotel_image();
        Picasso.get().load(imgPath).into(holder.hotel_image);

        holder.title.setText(bookings.getHotel().getHotel_name());
        holder.hotelName.setText(bookings.getHotel().getHotel_name());
        holder.descriptions.setText(bookings.getHotel().getDescription());
        holder.cost.setText(bookings.getHotel().getPrice());


        boolean isExpandble = bookingsList.get(i).isExpandable();
        holder.expandableLayout.setVisibility(isExpandble ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    public class UserbookingsViewHolder extends RecyclerView.ViewHolder {

        CircleImageView hotel_image;
        TextView hotelName, title, descriptions, cost;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public UserbookingsViewHolder(@NonNull View itemView) {
            super(itemView);


            hotel_image = itemView.findViewById(R.id.civ_hotelImage);
            hotelName = itemView.findViewById(R.id.tv_hotel_name1);
            descriptions = itemView.findViewById(R.id.tv_Description);
            cost = itemView.findViewById(R.id.tv_Price);
            title = itemView.findViewById(R.id.tv_hotel_name);


            linearLayout = itemView.findViewById(R.id.linear_layout_adminuserinfo);
            expandableLayout = itemView.findViewById(R.id.expandable_layout_adminuserdetails);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bookings bookings = bookingsList.get(getAdapterPosition());
                    bookings.setExpandable(!bookings.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }

    }

}