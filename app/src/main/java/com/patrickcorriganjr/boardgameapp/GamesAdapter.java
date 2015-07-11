package com.patrickcorriganjr.boardgameapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Patrick on 7/10/2015.
 */
public class GamesAdapter extends BaseAdapter {
    ArrayList<Game> mGamesList;
    Context mContext;

    public GamesAdapter(Context context, ArrayList<Game> gamesList){
        mContext = context;
        mGamesList = gamesList;
    }

    @Override
    public int getCount() {
        return mGamesList.size();
    }

    @Override
    public Game getItem(int i) {
        return mGamesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mGamesList.get(i).getmID();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.game_list_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.listItemImageView);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.listItemTitleTextView);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Game game = mGamesList.get(i);
        setPic(game.getmCurrentPhotoPath2(), holder.imageView);
        holder.titleTextView.setText((game.getmName()));

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView titleTextView;
    }

    private void setPic(String currentPhotoPath, ImageView imageView) {
        if(currentPhotoPath == null){
            return;
        }

        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        try {
            InputStream in = mContext.getContentResolver().openInputStream(
                    Uri.parse("file:" + currentPhotoPath));
            BitmapFactory.decodeStream(in, null, bmOptions);
        } catch (FileNotFoundException e) {
            String test = "";
            // do something
        }
        //BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = 1;//Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}
