package com.example.notesv3.domain;

// В списке будет название, дата создания и содержимое

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

import java.util.Date;

public class Notes implements Parcelable {

    private final String id;
    private final String name;
    private final Date date;
    private final String note;

    public Notes(String id, String name, Date date, String note) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.note = note;
    }

    protected Notes(Parcel in) {
        id = in.readString();
        name = in.readString();
        date = new Date(in.readLong());
        note = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeLong(date.getTime());
        parcel.writeString(note);
    }
}
