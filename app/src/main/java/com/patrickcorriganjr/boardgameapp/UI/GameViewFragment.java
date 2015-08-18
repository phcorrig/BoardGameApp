package com.patrickcorriganjr.boardgameapp.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.patrickcorriganjr.boardgameapp.Data.DBContract;
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

    private Game mGame = null;

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
    @InjectView(R.id.timesPlayedTextView)
    TextView timesPlayedTextView;
    @InjectView(R.id.whenBoughtTextView)
    TextView whenBoughtTextView;
    @InjectView(R.id.difficultyTextView)
    TextView difficultyTextView;
    @InjectView(R.id.ratingBar)
    RatingBar ratingBar;

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

        setHasOptionsMenu(true);
        ButterKnife.inject(this, rootView);

        mGame = getArguments().getParcelable(ARG_PARCELABLE);

        mGame = getGame(mGame.getID());
        if(mGame != null) {
            ID = mGame.getID();
            nameTextView.setText(mGame.getName());
            mCurrentPhotoPath2 = mGame.getCurrentPhotoPath2();
            mCurrentPhotoPath = "file:" + mCurrentPhotoPath2;
            //ratingBar.setRating(mGame.getRating());
            minPlayersTextView.setText(mGame.getMinPlayers() + "");
            maxPlayersTextView.setText(mGame.getMaxPlayers() + "");
            idealPlayersTextView.setText(mGame.getIdealPlayers() + "");
            // Category
            gameLengthTextView.setText(mGame.getGameLength() + "");
            //ratingTextView.setText(mGame.getRating() + "");
            timesPlayedTextView.setText(mGame.getTimesPlayed() + "");
            whenBoughtTextView.setText(mGame.getWhenBought());
            difficultyTextView.setText(mGame.getDifficulty());

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
    public void onResume() {
        super.onResume();

        if(mGame != null) {
            ratingBar.setRating(mGame.getRating());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.game_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GameEditFragment.newInstance(mGame)).addToBackStack("tag")
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    Game getGame(int pos){
        GamesDbHelper dbHelper = new GamesDbHelper(getActivity());

        Cursor cursor = dbHelper.getEntry(pos);
        cursor.moveToFirst();

        int ID = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable._ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_NAME));
        String currentPhotoPath2 = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PICTURE));
        int minPlayers = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_MIN_PLAYERS));
        int maxPlayers = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_MAX_PLAYERS));
        int idealPlayers = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_IDEAL_PLAYERS));
        // Category
        int gameLength = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PLAY_TIME));
        int rating = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_RATING));
        int timesPlayed = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_TIMES_PLAYED));
        String whenBought = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PURCHASE_DATE));
        String difficulty = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_DIFFICULTY));

        Game newGame = new Game(ID, name, currentPhotoPath2, minPlayers, maxPlayers, idealPlayers, gameLength, rating, timesPlayed, whenBought, difficulty);

        return newGame;
    }
}
