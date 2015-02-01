package com.awsm.awsmproject;

/**
 * Created by Yashal Shakti
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
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
      /*  if(i%2==0&&i%3!=0)
            new DownloadImageTask(viewHolder.storeImage).execute("http://static.panoramio.com/photos/large/68587240.jpg");
         else if(i%3==0)
            new DownloadImageTask(viewHolder.storeImage).execute("http://1.bp.blogspot.com/-Li_FJUPsvYU/UWlU0Yf1L_I/AAAAAAAAJTA/Vwj0hUu3EUA/s1600/sunset%2010.jpg");
          else*/
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

           bmImage.setImageBitmap(result);}
    }
}