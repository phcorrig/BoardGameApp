package com.patrickcorriganjr.boardgameapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Patrick on 7/10/2015.
 */
public class Game implements Parcelable {

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

    public int getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public String getCurrentPhotoPath2() {
        return mCurrentPhotoPath2;
    }

    public int getMinPlayers() {
        return mMinPlayers;
    }

    public int getMaxPlayers() {
        return mMaxPlayers;
    }

    public int getIdealPlayers() {
        return idealPlayers;
    }

    public int getGameLength() {
        return mGameLength;
    }

    public int getRating() {
        return mRating;
    }

    public int getTimesPlayed() {
        return mTimesPlayed;
    }

    public String getWhenBought() {
        return mWhenBought;
    }

    public String getDifficulty() {
        return mDifficulty;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mID);
        dest.writeString(this.mName);
        dest.writeString(this.mCurrentPhotoPath2);
        dest.writeString(this.mCurrentPhotoPath);
        dest.writeInt(this.mMinPlayers);
        dest.writeInt(this.mMaxPlayers);
        dest.writeInt(this.idealPlayers);
        dest.writeInt(this.mGameLength);
        dest.writeInt(this.mRating);
        dest.writeInt(this.mTimesPlayed);
        dest.writeString(this.mWhenBought);
        dest.writeString(this.mDifficulty);
    }

    protected Game(Parcel in) {
        this.mID = in.readInt();
        this.mName = in.readString();
        this.mCurrentPhotoPath2 = in.readString();
        this.mCurrentPhotoPath = in.readString();
        this.mMinPlayers = in.readInt();
        this.mMaxPlayers = in.readInt();
        this.idealPlayers = in.readInt();
        this.mGameLength = in.readInt();
        this.mRating = in.readInt();
        this.mTimesPlayed = in.readInt();
        this.mWhenBought = in.readString();
        this.mDifficulty = in.readString();
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
