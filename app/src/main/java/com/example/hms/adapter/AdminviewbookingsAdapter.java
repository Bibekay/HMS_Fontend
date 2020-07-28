package com.example.hms.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class AdminviewbookingsAdapter extends RecyclerView.Adapter <AdminviewbookingsAdapter.AdminviewbookingsViewHolder> {

    Context mContext;
    List<Bookings> bookingsList;
    private List<Bookings> filterbookingLists ;

    public AdminviewbookingsAdapter(Context mContext, List<Bookings> bookingsList) {
        this.mContext = mContext;
        this.bookingsList = bookingsList;
        filterbookingLists = new ArrayList<>(bookingsList);
    }

    @NonNull
    @Override
    public AdminviewbookingsAdapter.AdminviewbookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adminviewbookings_adapter, parent, false);
        return new AdminviewbookingsAdapter.AdminviewbookingsViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdminviewbookingsAdapter.AdminviewbookingsViewHolder holder,  int i) {

        final Bookings bookings = bookingsList.get(i);

        String imgPath = imagePath+bookings.getUser().getImage();
        Picasso.get().load(imgPath).into(holder.user_image);

        String imageHotel = imagePath+bookings.getHotel().getHotel_image();
        Picasso.get().load(imageHotel).into(holder.hotelImage);



        holder.username.setText(bookings.getUser().getUsername());
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

    public class AdminviewbookingsViewHolder extends RecyclerView.ViewHolder {

        CircleImageView user_image;
        ImageView hotelImage;
        TextView username, hotelName, descriptions, cost;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public AdminviewbookingsViewHolder(@NonNull View itemView) {
            super(itemView);


            user_image = itemView.findViewById(R.id.civ_userImage);
            hotelImage = itemView.findViewById(R.id.iv_hotelImage);
            username = itemView.findViewById(R.id.tv_username);
            hotelName = itemView.findViewById(R.id.tv_hotel_name1);
            descriptions = itemView.findViewById(R.id.tv_Description);
            cost = itemView.findViewById(R.id.tv_Price);


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