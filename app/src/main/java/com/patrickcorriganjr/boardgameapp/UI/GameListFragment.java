package com.patrickcorriganjr.boardgameapp.UI;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.patrickcorriganjr.boardgameapp.Data.DBContract;
import com.patrickcorriganjr.boardgameapp.Data.Game;
import com.patrickcorriganjr.boardgameapp.Adapters.GamesAdapter;
import com.patrickcorriganjr.boardgameapp.Data.GamesDbHelper;
import com.patrickcorriganjr.boardgameapp.MainActivity;
import com.patrickcorriganjr.boardgameapp.R;

import java.util.ArrayList;

/**
 * Created by Bag Boy Rebel on 6/14/2015.
 */
public class GameListFragment extends Fragment {

    ListView mListView;
    FloatingActionButton mFab;
    GamesAdapter adapter2;

    ArrayList<Game> gameList;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GameListFragment newInstance(){//int sectionNumber) {
        GameListFragment fragment = new GameListFragment();
        //Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        //fragment.setArguments(args);
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
                        .replace(R.id.container, GameEditFragment.newInstance(null)).addToBackStack("tag")
                        .commit();
            }
        });

        mListView = (ListView)rootView.findViewById(R.id.gamesList);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(mListView);

        GamesDbHelper dbHelper = new GamesDbHelper(getActivity());

        Cursor cursor = dbHelper.getAllEntries();

        getGames();
        adapter2 = new GamesAdapter(getActivity(), gameList);
        mListView.setAdapter(adapter2);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Game game = (Game) adapter2.getItem(i);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, GameViewFragment.newInstance(game)).addToBackStack("tag")
                        .commit();
            }
        });



        return rootView;
    }

    public void getGames(){
        GamesDbHelper dbHelper = new GamesDbHelper(getActivity());
        gameList = new ArrayList<Game>();

        Cursor cursor = dbHelper.getAllEntries();
        //cursor.moveToFirst();

        while(cursor.moveToNext()){
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
            gameList.add(newGame);
        }
    }
}
