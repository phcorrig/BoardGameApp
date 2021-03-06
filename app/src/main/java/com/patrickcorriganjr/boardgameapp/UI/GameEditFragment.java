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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
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
public class GameEditFragment extends Fragment {

    public static final String TAG = GameEditFragment.class.getSimpleName();

        /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_PARCELABLE = "parcelable";

    Game mGame;

    private int ID = -1;

    @InjectView(R.id.nameEditText)
    EditText nameEditText;
    @InjectView(R.id.imageView)
    ImageView imageView;
    @InjectView(R.id.minPlayersEditText)
    EditText minPlayersEditText;
    @InjectView(R.id.maxPlayersEditText)
    EditText maxPlayersEditText;
    @InjectView(R.id.idealPlayersEditText)
    EditText idealPlayersEditText;
    @InjectView(R.id.gameLengthEditText)
    EditText gameLengthEditText;
    @InjectView(R.id.ratingEditText)
    EditText ratingEditText;
    @InjectView(R.id.timesPlayedEditText)
    EditText timesPlayedEditText;
    @InjectView(R.id.whenBoughtEditText)
    EditText whenBoughtEditText;
    @InjectView(R.id.difficultyEditText)
    EditText difficultyEditText;
    @InjectView(R.id.submitButton)
    Button submitButton;
    @InjectView(R.id.ratingBarEdit)
    RatingBar ratingBar;
    @InjectView(R.id.difficultySpinner)
    Spinner difficultySpinner;

    @OnClick (R.id.submitButton)
    void submit(){
        GamesDbHelper dbHelper = new GamesDbHelper(getActivity());

        if(ID == -1) {
            dbHelper.insertEntry(nameEditText.getText().toString(),
                    mCurrentPhotoPath2,
                    minPlayersEditText.getText().toString(),
                    maxPlayersEditText.getText().toString(),
                    idealPlayersEditText.getText().toString(),
                    null,
                    gameLengthEditText.getText().toString(),
                    ratingBar.getRating() + "",//ratingEditText.getText().toString(),
                    timesPlayedEditText.getText().toString(),
                    whenBoughtEditText.getText().toString(),
                    difficultySpinner.getSelectedItemPosition() + ""); //difficultyEditText.getText().toString());
        }
        else{
            dbHelper.updateEntry("" + ID,
                    nameEditText.getText().toString(),
                    mCurrentPhotoPath2,
                    minPlayersEditText.getText().toString(),
                    maxPlayersEditText.getText().toString(),
                    idealPlayersEditText.getText().toString(),
                    null,
                    gameLengthEditText.getText().toString(),
                    ratingBar.getRating() + "",//ratingEditText.getText().toString(),
                    timesPlayedEditText.getText().toString(),
                    whenBoughtEditText.getText().toString(),
                    difficultySpinner.getSelectedItemPosition() + ""); //difficultyEditText.getText().toString());
        }
        getFragmentManager().popBackStack();
    }

    @OnClick (R.id.imageView)
    void selectPhoto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.camera_choices, mDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GameEditFragment newInstance(Parcelable parcelable) {
        GameEditFragment fragment = new GameEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARCELABLE, parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    public GameEditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit, container, false);

        ButterKnife.inject(this, rootView);

        mGame = getArguments().getParcelable(ARG_PARCELABLE);
        if(mGame != null) {
            ID = mGame.getID();
            nameEditText.setText(mGame.getName());
            mCurrentPhotoPath2 = mGame.getCurrentPhotoPath2();
            mCurrentPhotoPath = "file:" + mCurrentPhotoPath2;
            minPlayersEditText.setText(mGame.getMinPlayers() + "");
            maxPlayersEditText.setText(mGame.getMaxPlayers() + "");
            idealPlayersEditText.setText(mGame.getIdealPlayers() + "");
            // Category
            gameLengthEditText.setText(mGame.getGameLength() + "");

            ratingEditText.setText(mGame.getRating() + "");
            timesPlayedEditText.setText(mGame.getTimesPlayed() + "");
            whenBoughtEditText.setText(mGame.getWhenBought());
            difficultyEditText.setText(mGame.getDifficulty());
            difficultySpinner.setSelection(Integer.parseInt(mGame.getDifficulty()));

            if(mCurrentPhotoPath2 != null) {
                setPic();
            }
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

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final int REQUEST_IMAGE_SELECT = 2;

    protected DialogInterface.OnClickListener mDialogListener =
        new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0: // Take picture
                        dispatchTakePictureIntent();
                        break;
                    case 1: // Choose picture
                        Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        choosePhotoIntent.setType("image/*");
                        startActivityForResult(choosePhotoIntent, REQUEST_IMAGE_SELECT);
                        break;
                }
            }
        };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            setPic();
        }
    }

    String mCurrentPhotoPath;
    String mCurrentPhotoPath2;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                getActivity().getExternalFilesDir(null)      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        mCurrentPhotoPath2 = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "There was a problem", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

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
