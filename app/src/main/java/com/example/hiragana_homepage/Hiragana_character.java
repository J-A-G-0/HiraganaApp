package com.example.hiragana_homepage;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

import com.google.gson.annotations.SerializedName;

public class Hiragana_character implements Parcelable {
    @SerializedName("latin")
    String latinCharacter;
    @SerializedName("hiragana")
    String hiraganaCharacter;
    String mnemonic;
    int thumbnail;
    int audio;

    public Hiragana_character() {

    }

    //Parcelable bits start
// Should this guy be public like in the video?
    protected Hiragana_character(Parcel in) {
        latinCharacter = in.readString();
        hiraganaCharacter = in.readString();
        mnemonic = in.readString();
        thumbnail = in.readInt();
        audio = in.readInt();
    }

    public static final Creator<Hiragana_character> CREATOR = new Creator<Hiragana_character>() {
        @Override
        public Hiragana_character createFromParcel(Parcel in) {
            return new Hiragana_character(in);
        }

        @Override
        public Hiragana_character[] newArray(int size) {
            return new Hiragana_character[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    //Remember have to read and write in the same order!
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(latinCharacter);
        parcel.writeString(hiraganaCharacter);
        parcel.writeString(mnemonic);
        parcel.writeInt(thumbnail);
        parcel.writeInt(audio);
    }

    //Parcelable bits end


    public String getLatinCharacter() {
        return latinCharacter;
    }

    public void setLatinCharacter(String latinCharacter) {
        this.latinCharacter = latinCharacter;
    }

    public String getHiraganaCharacter() {
        return hiraganaCharacter;
    }

    public void setHiraganaCharacter(String hiraganaCharacter) {
        this.hiraganaCharacter = hiraganaCharacter;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public Hiragana_character(String latinCharacter, String hiraganaCharacter, String mnemonic, int thumbnail, int audio) {
        this.latinCharacter = latinCharacter;
        this.hiraganaCharacter = hiraganaCharacter;
        this.mnemonic = mnemonic;
        this.thumbnail = thumbnail;
        this.audio = audio;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    public void setAudioFile() {
        String latinChar = getLatinCharacter();
        switch (latinChar) {
            case "a":
                setAudio(R.raw.a);
                break;
            case "i":
                setAudio(R.raw.i);
                break;
            case "u":
                setAudio(R.raw.u);
                break;
            case "e":
                setAudio(R.raw.e);
                break;
            case "o":
            case "wo":
                setAudio(R.raw.o);
                break;
            case "ka":
                setAudio(R.raw.ka);
                break;
            case "ki":
                setAudio(R.raw.ki);
                break;
            case "ku":
                setAudio(R.raw.ku);
                break;
            case "ke":
                setAudio(R.raw.ke);
                break;
            case "ko":
                setAudio(R.raw.ko);
                break;
            case "sa":
                setAudio(R.raw.sa);
                break;
            case "shi":
                setAudio(R.raw.shi);
                break;
            case "su":
                setAudio(R.raw.su);
                break;
            case "se":
                setAudio(R.raw.se);
                break;
            case "so":
                setAudio(R.raw.so);
                break;
            case "ta":
                setAudio(R.raw.ta);
                break;
            case "chi":
                setAudio(R.raw.chi);
                break;
            case "tsu":
                setAudio(R.raw.tsu);
                break;
            case "te":
                setAudio(R.raw.te);
                break;
            case "to":
                setAudio(R.raw.to);
                break;
            case "na":
                setAudio(R.raw.na);
                break;
            case "ni":
                setAudio(R.raw.ni);
                break;
            case "nu":
                setAudio(R.raw.nu);
                break;
            case "ne":
                setAudio(R.raw.ne);
                break;
            case "no":
                setAudio(R.raw.no);
                break;
            case "ha":
                setAudio(R.raw.ha);
                break;
            case "hi":
                setAudio(R.raw.hi);
                break;
            case "fu":
                setAudio(R.raw.fu);
                break;
            case "he":
                setAudio(R.raw.he);
                break;
            case "ho":
                setAudio(R.raw.ho);
                break;
            case "ma":
                setAudio(R.raw.ma);
                break;
            case "mi":
                setAudio(R.raw.mi);
                break;
            case "mu":
                setAudio(R.raw.mu);
                break;
            case "me":
                setAudio(R.raw.me);
                break;
            case "mo":
                setAudio(R.raw.mo);
                break;
            case "ya":
                setAudio(R.raw.a);
                break;
            case "yu":
                setAudio(R.raw.yu);
                break;
            case "yo":
                setAudio(R.raw.yo);
                break;
            case "ra":
                setAudio(R.raw.ra);
                break;
            case "ri":
                setAudio(R.raw.ri);
                break;
            case "ru":
                setAudio(R.raw.ru);
                break;
            case "re":
                setAudio(R.raw.re);
                break;
            case "ro":
                setAudio(R.raw.ro);
                break;
            case "wa":
                setAudio(R.raw.wa);
                break;
            case "n":
                setAudio(R.raw.n);
                break;
            default:
                setAudio(R.raw.a);
        }
    }

    public void setDrawable(){
        String latinChar = getLatinCharacter();
        switch (latinChar) {
            case "a":
                setThumbnail(R.drawable.a);
                break;
            case "i":
                setThumbnail(R.drawable.i);
                break;
            case "u":
                setThumbnail(R.drawable.u);
                break;
            case "e":
                setThumbnail(R.drawable.e);
                break;
            case "o":
                setThumbnail(R.drawable.o);
                break;
            case "ka":
                setThumbnail(R.drawable.ka);
                break;
            case "ki":
                setThumbnail(R.drawable.ki);
                break;
            case "ku":
                setThumbnail(R.drawable.ku);
                break;
            case "ke":
                setThumbnail(R.drawable.ke);
                break;
            case "ko":
                setThumbnail(R.drawable.ko);
                break;
            case "sa":
                setThumbnail(R.drawable.sa);
                break;
            case "shi":
                setThumbnail(R.drawable.shi);
                break;
            case "su":
                setThumbnail(R.drawable.su);
                break;
            case "se":
                setThumbnail(R.drawable.se);
                break;
            case "so":
                setThumbnail(R.drawable.so);
                break;
            case "ta":
                setThumbnail(R.drawable.ta);
                break;
            case "chi":
                setThumbnail(R.drawable.chi);
                break;
            case "tsu":
                setThumbnail(R.drawable.tsu);
                break;
            case "te":
                setThumbnail(R.drawable.te);
                break;
            case "to":
                setThumbnail(R.drawable.to);
                break;
            case "na":
                setThumbnail(R.drawable.na);
                break;
            case "ni":
                setThumbnail(R.drawable.ni);
                break;
            case "nu":
                setThumbnail(R.drawable.nu);
                break;
            case "ne":
                setThumbnail(R.drawable.ne);
                break;
            case "no":
                setThumbnail(R.drawable.no);
                break;
            case "ha":
                setThumbnail(R.drawable.ha);
                break;
            case "hi":
                setThumbnail(R.drawable.hi);
                break;
            case "fu":
                setThumbnail(R.drawable.fu);
                break;
            case "he":
                setThumbnail(R.drawable.he);
                break;
            case "ho":
                setThumbnail(R.drawable.ho);
                break;
            case "ma":
                setThumbnail(R.drawable.ma);
                break;
            case "mi":
                setThumbnail(R.drawable.mi);
                break;
            case "mu":
                setThumbnail(R.drawable.mu);
                break;
            case "me":
                setThumbnail(R.drawable.me);
                break;
            case "mo":
                setThumbnail(R.drawable.mo);
                break;
            case "ya":
                setThumbnail(R.drawable.ya);
                break;
            case "yu":
                setThumbnail(R.drawable.yu);
                break;
            case "yo":
                setThumbnail(R.drawable.yo);
                break;
            case "ra":
                setThumbnail(R.drawable.ra);
                break;
            case "ri":
                setThumbnail(R.drawable.ri);
                break;
            case "ru":
                setThumbnail(R.drawable.ru);
                break;
            case "re":
                setThumbnail(R.drawable.re);
                break;
            case "ro":
                setThumbnail(R.drawable.ro);
                break;
            case "wa":
                setThumbnail(R.drawable.wa);
                break;
            case "wo":
                setThumbnail(R.drawable.wo);
                break;
            case "n":
                setThumbnail(R.drawable.n);
                break;
            default:
                setThumbnail(R.drawable.mishima_barakei);
        }
    }

}


