package com.awsm.awsmproject;

/**
 * Created by Yashal Shakti
 */
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] stores;
    Context mContext;
    public MyAdapter(String myStores[], Context context)
    {
        stores=myStores;
        mContext=context;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        ViewHolder vh = new ViewHolder((v));
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.storeName.setText(stores[i]);
        viewHolder.storeImage.setImageResource(mContext.getResources().getIdentifier(stores[i].toLowerCase(), "drawable", mContext.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return stores.length;
    }


    //ViewHolder for performance parameters , decreases lag
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView storeName;
        public ImageView storeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            storeImage = (ImageView) itemView.findViewById(R.id.storeImage);
            storeName= (TextView)itemView.findViewById(R.id.storeName);

        }
    }
}