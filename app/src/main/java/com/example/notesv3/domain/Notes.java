package com.example.notesv3.domain;

// В списке будет название, дата создания и содержимое

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

public class Notes implements Parcelable {

    @StringRes // аннотация для понимания, что это не просто int, а какой-то ресурс
    private final int name;
    @StringRes
    private final int date;
    @StringRes
    private final int note;

    public Notes(int name, int date, int note) {
        this.name = name;
        this.date = date;
        this.note = note;
    }

    protected Notes(Parcel in) {
        name = in.readInt();
        date = in.readInt();
        note = in.readInt();
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

    public int getName() {
        return name;
    }

    public int getDate() {
        return date;
    }

    public int getNote() {
        return note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(name);
        parcel.writeInt(date);
        parcel.writeInt(note);
    }
}
