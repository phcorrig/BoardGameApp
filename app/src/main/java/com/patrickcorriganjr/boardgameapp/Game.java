package com.patrickcorriganjr.boardgameapp;

/**
 * Created by Patrick on 7/10/2015.
 */
public class Game {

    int mID;
    String mName;
    String mCurrentPhotoPath2;
    String mCurrentPhotoPath;// = "file:" + mCurrentPhotoPath2;
    int mMinPlayers;
    int mMaxPlayers;
    int idealPlayers;
    // Category
    int mGameLength;
    int mRating;
    int mTimesPlayed;
    String mWhenBought;
    String mDifficulty;

    public Game(int mID, String mName, String mCurrentPhotoPath2, int mMinPlayers, int mMaxPlayers, int idealPlayers, int mGameLength, int mRating, int mTimesPlayed, String mWhenBought, String mDifficulty) {
        this.mID = mID;
        this.mName = mName;
        this.mCurrentPhotoPath2 = mCurrentPhotoPath2;
        this.mMinPlayers = mMinPlayers;
        this.mMaxPlayers = mMaxPlayers;
        this.idealPlayers = idealPlayers;
        this.mGameLength = mGameLength;
        this.mRating = mRating;
        this.mTimesPlayed = mTimesPlayed;
        this.mWhenBought = mWhenBought;
        this.mDifficulty = mDifficulty;
    }

    public int getmID() {
        return mID;
    }

    public String getmName() {
        return mName;
    }

    public String getmCurrentPhotoPath2() {
        return mCurrentPhotoPath2;
    }

    public int getmMinPlayers() {
        return mMinPlayers;
    }

    public int getmMaxPlayers() {
        return mMaxPlayers;
    }

    public int getIdealPlayers() {
        return idealPlayers;
    }

    public int getmGameLength() {
        return mGameLength;
    }

    public int getmRating() {
        return mRating;
    }

    public int getmTimesPlayed() {
        return mTimesPlayed;
    }

    public String getmWhenBought() {
        return mWhenBought;
    }

    public String getmDifficulty() {
        return mDifficulty;
    }
}
