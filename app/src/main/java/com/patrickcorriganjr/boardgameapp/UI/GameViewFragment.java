package com.patrickcorriganjr.boardgameapp.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.patrickcorriganjr.boardgameapp.Data.Game;
import com.patrickcorriganjr.boardgameapp.Data.GamesDbHelper;
import com.patrickcorriganjr.boardgameapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Bag Boy Rebel on 6/14/2015.
 */
public class GameViewFragment extends Fragment {

    public static final String TAG = GameViewFragment.class.getSimpleName();

        /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_PARCELABLE = "parcelable";

    private int ID = -1;

    @InjectView(R.id.nameTextView)
    TextView nameTextView;
    @InjectView(R.id.imageView2)
    ImageView imageView;
    @InjectView(R.id.minPlayersTextView)
    TextView minPlayersTextView;
    @InjectView(R.id.maxPlayersTextView)
    TextView maxPlayersTextView;
    @InjectView(R.id.idealPlayersTextView)
    TextView idealPlayersTextView;
    @InjectView(R.id.gameLengthTextView)
    TextView gameLengthTextView;
    @InjectView(R.id.ratingTextView)
    TextView ratingTextView;
    @InjectView(R.id.timesPlayedTextView)
    TextView timesPlayedTextView;
    @InjectView(R.id.whenBoughtTextView)
    TextView whenBoughtTextView;
    @InjectView(R.id.difficultyTextView)
    TextView difficultyTextView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GameViewFragment newInstance(Parcelable parcelable) {
        GameViewFragment fragment = new GameViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARCELABLE, parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    public GameViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view, container, false);

        ButterKnife.inject(this, rootView);

        Game game = getArguments().getParcelable(ARG_PARCELABLE);
        if(game != null) {
            ID = game.getID();
            nameTextView.setText(game.getName());
            mCurrentPhotoPath2 = game.getCurrentPhotoPath2();
            mCurrentPhotoPath = "file:" + mCurrentPhotoPath2;
            minPlayersTextView.setText(game.getMinPlayers() + "");
            maxPlayersTextView.setText(game.getMaxPlayers() + "");
            idealPlayersTextView.setText(game.getIdealPlayers() + "");
            // Category
            gameLengthTextView.setText(game.getGameLength() + "");
            ratingTextView.setText(game.getRating() + "");
            timesPlayedTextView.setText(game.getTimesPlayed() + "");
            whenBoughtTextView.setText(game.getWhenBought());
            difficultyTextView.setText(game.getDifficulty());

            if(mCurrentPhotoPath2 != null) {
                setPic();
            }
        }
        else{
            Log.e(TAG, "There was a problem loading the game");
            Toast.makeText(getActivity(), "Could not load game", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    String mCurrentPhotoPath;
    String mCurrentPhotoPath2;

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        try {
            InputStream in = getActivity().getContentResolver().openInputStream(
                    Uri.parse(mCurrentPhotoPath));
            BitmapFactory.decodeStream(in, null, bmOptions);
        } catch (FileNotFoundException e) {
            // do something
        }
        //BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = 1;
        if(targetH > 0 && targetW > 0) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath2, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}
