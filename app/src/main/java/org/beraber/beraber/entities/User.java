package org.beraber.beraber.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public final String name;
    public final String bio;

    public final long   id;

    public User(String name, String bio, long id) {
        this.name = name;
        this.bio = bio;
        this.id = id;
    }

    public User(Parcel in) {
        name = in.readString();
        bio  = in.readString();
        id   = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(bio);
        dest.writeLong(id);
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
