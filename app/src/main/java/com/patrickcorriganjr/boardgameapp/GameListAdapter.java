package com.patrickcorriganjr.boardgameapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Patrick on 7/4/2015.
 */
public class GameListAdapter extends CursorAdapter {

    ImageView imageView;
    TextView titleTextView;

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private String mCurrentPhotoPath;

    public GameListAdapter(Context context, Cursor c) {
        super(context, c);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = mLayoutInflater.inflate(R.layout.game_list_item, viewGroup, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_NAME));
        mCurrentPhotoPath = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PICTURE));

        imageView = (ImageView) view.findViewById(R.id.listItemImageView);
        titleTextView = (TextView) view.findViewById(R.id.listItemTitleTextView);

        setPic();
        titleTextView.setText(title);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        try {
            InputStream in = mContext.getContentResolver().openInputStream(
                    Uri.parse("file:" + mCurrentPhotoPath));
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

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}
