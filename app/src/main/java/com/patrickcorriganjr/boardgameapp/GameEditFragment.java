package com.patrickcorriganjr.boardgameapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Bag Boy Rebel on 6/14/2015.
 */
public class GameEditFragment extends Fragment {

    EditText mName;
    EditText mMinPlayers;
    EditText mMaxPlayers;
    EditText mIdealPlayers;
    EditText mGameLength;
    EditText mRating;
    EditText mTimesPlayed;
    EditText mWhenBought;
    EditText mDifficulty;

    Button mSubmitButton;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

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

        mName = (EditText)rootView.findViewById(R.id.nameEditText);
        mMinPlayers = (EditText)rootView.findViewById(R.id.minPlayersEditText);
        mMaxPlayers = (EditText)rootView.findViewById(R.id.maxPlayersEditText);
        mIdealPlayers = (EditText)rootView.findViewById(R.id.idealPlayersEditText);
        mGameLength = (EditText)rootView.findViewById(R.id.gameLengthEditText);
        mRating = (EditText)rootView.findViewById(R.id.ratingEditText);
        mTimesPlayed = (EditText)rootView.findViewById(R.id.timesPlayedEditText);
        mWhenBought = (EditText)rootView.findViewById(R.id.whenBoughtEditText);
        mDifficulty = (EditText)rootView.findViewById(R.id.difficultyEditText);

        mSubmitButton = (Button)rootView.findViewById(R.id.submitButton);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GamesDbHelper dbHelper = new GamesDbHelper(getActivity());

                dbHelper.insertEntry(mName.getText().toString(),
                        null,
                        mMinPlayers.getText().toString(),
                        mMaxPlayers.getText().toString(),
                        mIdealPlayers.getText().toString(),
                        null,
                        mGameLength.getText().toString(),
                        mRating.getText().toString(),
                        mTimesPlayed.getText().toString(),
                        mWhenBought.getText().toString(),
                        mDifficulty.getText().toString());

            }
        });

        GamesDbHelper dbHelper = new GamesDbHelper(getActivity());

        //dbHelper.insertEntry("Name2", null, 1, 4, 4, 0, 60, 5, 1, null, null);
        Cursor cursor = dbHelper.getAllEntries();

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] { DBContract.GamesTable.COLUMN_NAME_NAME,  DBContract.GamesTable.COLUMN_NAME_RATING},
                new int[] {android.R.id.text1, android.R.id.text2});

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


}
