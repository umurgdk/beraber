package org.beraber.beraber.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Activity implements Parcelable {
    public final String title;
    public final String description;
    public final long   user_id;

    public final transient User user;


    @Nullable
    @SerializedName("start_date")
    public final Date startDate;

    @Nullable
    public final long   id;

    public Activity(String title, String description, User user, int imagesCount, Date startDate, long id) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.user_id = user.id;
        this.startDate = startDate;
        this.id = id;
    }

    public Activity(Parcel in) {
        title = in.readString();
        description = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        user_id = in.readLong();
        startDate = (Date) in.readValue(Date.class.getClassLoader());
        id = in.readLong();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeParcelable(user, flags);
        dest.writeLong(user.id);
        dest.writeValue(startDate);
        dest.writeLong(id);

    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Activity createFromParcel(Parcel source) {
            return new Activity(source);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };
}
