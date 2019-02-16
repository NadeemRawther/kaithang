package com.example.greeshma.bloodbank;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListSearch_Adapter extends RecyclerView.Adapter<ListSearch_Adapter.ProductViewHolder> {

    private Context mCtx;
    private List<ListSearch_Item> donorList;
    private List<ListSearch_Item> donorListCpy2;


    //getting the context and product list with constructor
    public ListSearch_Adapter(Context mCtx, List<ListSearch_Item> donorList) {
        this.mCtx = mCtx;
        this.donorList = donorList;
        this.donorListCpy2 =donorList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_search_items, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        final ListSearch_Item donor = donorList.get(position);
        //binding the data with the viewholder views
        holder.tvName.setText("Name: " + donor.getName());
        holder.tvPhoneNo.setText("Phone: " + donor.getPhoneNo());
        holder.tvPlace.setText("Place: " + donor.getPlace());

        holder.iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = donor.getPhoneNo();
                Toast.makeText(mCtx, ph, Toast.LENGTH_LONG).show();
              //  ActivityCompat.requestPermissions((Activity) mCtx, new String[]{Manifest.permission.CALL_PHONE}, 1);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
                mCtx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return donorList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhoneNo, tvPlace;
        public ImageView iv_call;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhoneNo = itemView.findViewById(R.id.tvPhoneNo);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            iv_call = itemView.findViewById(R.id.iv_call);


        }
    }

    public void filter(String text) {
         List<ListSearch_Item> donorListCpy1 = new ArrayList<ListSearch_Item>();

        //donorListCpy1.clear();
        if(text.isEmpty()){
            donorListCpy1=donorListCpy2;
        } else{
            text = text.toLowerCase();
            for(ListSearch_Item donor: donorListCpy2){
                if(donor.getName().toLowerCase().contains(text)){
                    donorListCpy1.add(donor);
                }
            }
        }
        donorList=donorListCpy1;
        notifyDataSetChanged();
    }
}
