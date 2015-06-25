package com.patrickcorriganjr.boardgameapp;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Bag Boy Rebel on 6/14/2015.
 */
public class GameListFragment extends Fragment {

    ListView mListView;
    FloatingActionButton mFab;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GameListFragment newInstance(int sectionNumber) {
        GameListFragment fragment = new GameListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GameListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mFab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GameEditFragment.newInstance(1)).addToBackStack("tag")
                        .commit();
            }
        });

        mListView = (ListView)rootView.findViewById(R.id.gamesList);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(mListView);

        GamesDbHelper dbHelper = new GamesDbHelper(getActivity());

        //dbHelper.insertEntry("Name2", null, 1, 4, 4, 0, 60, 5, 1, null, null);
        Cursor cursor = dbHelper.getAllEntries();

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] { DBContract.GamesTable.COLUMN_NAME_NAME,  DBContract.GamesTable.COLUMN_NAME_RATING},
                new int[] {android.R.id.text1, android.R.id.text2});

        mListView.setAdapter(simpleCursorAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
