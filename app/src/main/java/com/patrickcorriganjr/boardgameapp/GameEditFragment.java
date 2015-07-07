package com.patrickcorriganjr.boardgameapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

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
    private static final String ARG_SECTION_NUMBER = "section_number";
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

    @OnClick (R.id.submitButton)
    void submit(){
        GamesDbHelper dbHelper = new GamesDbHelper(getActivity());

        dbHelper.insertEntry(nameEditText.getText().toString(),
                mCurrentPhotoPath2,
                minPlayersEditText.getText().toString(),
                maxPlayersEditText.getText().toString(),
                idealPlayersEditText.getText().toString(),
                null,
                gameLengthEditText.getText().toString(),
                ratingEditText.getText().toString(),
                timesPlayedEditText.getText().toString(),
                whenBoughtEditText.getText().toString(),
                difficultyEditText.getText().toString());
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
    public static GameEditFragment newInstance(int sectionNumber) {
        GameEditFragment fragment = new GameEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
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

        int index = getArguments().getInt(ARG_SECTION_NUMBER);
        if(index > 0) {
            GamesDbHelper dbHelper = new GamesDbHelper(getActivity());
            Cursor cursor = dbHelper.getAllEntries();
            cursor.moveToPosition(index);

            nameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_NAME)));
            mCurrentPhotoPath2 = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PICTURE));
            mCurrentPhotoPath = "file:" + mCurrentPhotoPath2;
            minPlayersEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_MIN_PLAYERS)));
            maxPlayersEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_MAX_PLAYERS)));
            idealPlayersEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_IDEAL_PLAYERS)));
            // Category
            gameLengthEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PLAY_TIME)));
            ratingEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_RATING)));
            timesPlayedEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_TIMES_PLAYED)));
            whenBoughtEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PURCHASE_DATE)));
            difficultyEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_DIFFICULTY)));

            if(mCurrentPhotoPath2 != null) {
                setPic();
            }
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));*/
       /* int index = getArguments().getInt(ARG_SECTION_NUMBER);
        index = 1;
        if(index > 0) {
            GamesDbHelper dbHelper = new GamesDbHelper(getActivity());
            Cursor cursor = dbHelper.getAllEntries();
            cursor.moveToFirst();
            //cursor.moveToPosition(index);

            nameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_NAME)));
            mCurrentPhotoPath2 = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PICTURE));
            minPlayersEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_MIN_PLAYERS)));
            maxPlayersEditText.setText(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_MAX_PLAYERS)));
            idealPlayersEditText.setText(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_IDEAL_PLAYERS)));
            // Category
            gameLengthEditText.setText(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PLAY_TIME)));
            ratingEditText.setText(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_RATING)));
            timesPlayedEditText.setText(cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_TIMES_PLAYED)));
            whenBoughtEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_PURCHASE_DATE)));
            difficultyEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.GamesTable.COLUMN_NAME_DIFFICULTY)));
        }*/
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
                        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            // Create the File where the photo should go
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                                // Error occurred while creating the File
                                Log.e(TAG, "Exception: " + ex);
                            }
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(photoFile));
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                                /*Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                File f = new File(mCurrentPhotoPath);
                                Uri contentUri = Uri.fromFile(f);
                                imageView.setImageURI(contentUri);/
                            }
                        }*/
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
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);*/
            /*Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            imageView.setImageURI(contentUri);*/
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
