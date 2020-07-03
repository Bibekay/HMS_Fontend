package com.example.hms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hms.R;
import com.example.hms.models.Users;


import java.util.ArrayList;
import java.util.List;



public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder> {

    Context mContext;
    List<Users> usersList;
    private List<Users> filteruserList;

    public UserInfoAdapter(Context mContext, List<Users> usersList) {

        this.mContext = mContext;
        this.usersList = usersList;
        filteruserList = new ArrayList<>(usersList);

    }

    @NonNull
    @Override
    public UserInfoAdapter.UserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_userinfo_details,parent,false);
        return new UserInfoViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull UserInfoAdapter.UserInfoViewHolder holder, int i) {

        final Users users = usersList.get(i);
//        String imgPath = url.imagePath+users.getImage();
        holder.fullname.setText(users.getFullname());
        holder.contact.setText(users.getContact());
        holder.username.setText(users.getUsername());
        holder.email.setText(users.getEmail());
        holder.username.setText(users.getUsername());

       // Picasso.get().load(imgPath).into(holder.imgProfile);

        boolean isExpandble = usersList.get(i).isExpandable();
        holder.expandableLayout.setVisibility(isExpandble ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public Filter getFilter()

    {
        return userssfilter;
    }

    private Filter userssfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Users> filteredList= new ArrayList<>();
            if (constraint == null || constraint.length()==0){
                filteredList.addAll(filteruserList);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Users users : filteruserList){
                    if(users.getUsername().toLowerCase().contains(filterPattern)){
                        filteredList.add(users);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            usersList.clear();
            usersList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


    public class UserInfoViewHolder extends RecyclerView.ViewHolder {


        TextView fullname, contact, email, username;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public UserInfoViewHolder(@NonNull View itemView) {
            super(itemView);


            fullname = itemView.findViewById(R.id.user_fullname);
            contact = itemView.findViewById(R.id.user_contact);
            email = itemView.findViewById(R.id.user_email);
            username = itemView.findViewById(R.id.user_username);


            linearLayout = itemView.findViewById(R.id.linear_layout_adminuserinfo);
            expandableLayout = itemView.findViewById(R.id.expandable_layout_adminuserdetails);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Users users = usersList.get(getAdapterPosition());
                    users.setExpandable(!users.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
