package com.example.hiragana_homepage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Class for creating parcelable arrays that can be passed between activities.
 *
 * @author joelgodfrey
 */
public class Parcelable_Array implements Parcelable {
    private final ArrayList mParcelableArray;

    public Parcelable_Array(ArrayList arrayList){
        this.mParcelableArray = arrayList;
    }

    public ArrayList getmParcelableArray() {
        return mParcelableArray;
    }

    public Parcelable_Array (Parcel parcel)
    {
        this.mParcelableArray = parcel.readArrayList(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mParcelableArray);
    }

    public static Creator<Parcelable_Array> CREATOR = new Creator<Parcelable_Array>() {

        @Override
        public Parcelable_Array createFromParcel(Parcel source) {
            return new Parcelable_Array(source);
        }

        @Override
        public Parcelable_Array[] newArray(int size) {
            return new Parcelable_Array[size];
        }
    };

}
