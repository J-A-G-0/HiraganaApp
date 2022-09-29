package com.example.hiragana_homepage;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

import com.google.gson.annotations.SerializedName;


/**
 * Class used to create HiraganaCharacter objects and make them parcelable.
 *
 * @author joelgodfrey
 */
public class Hiragana_character implements Parcelable {
    @SerializedName("latin")
    private String latinCharacter;
    @SerializedName("hiragana")
    private String hiraganaCharacter;
    private String mnemonic;
    private int thumbnail;
    private int audio;
    private int mnemonicImage;


    // Public, used to create char without having to provide all fields immediately.
    public Hiragana_character() {
    }

    //Parcelable Implementation begins
    protected Hiragana_character(Parcel in) {
        latinCharacter = in.readString();
        hiraganaCharacter = in.readString();
        mnemonic = in.readString();
        thumbnail = in.readInt();
        audio = in.readInt();
        mnemonicImage = in.readInt();
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


    //Remember to read and write in the same order.
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(latinCharacter);
        parcel.writeString(hiraganaCharacter);
        parcel.writeString(mnemonic);
        parcel.writeInt(thumbnail);
        parcel.writeInt(audio);
        parcel.writeInt(mnemonicImage);
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

    public int getMnemonicImage() {
        return mnemonicImage;
    }

    public Hiragana_character(String latinCharacter, String hiraganaCharacter, String mnemonic, int thumbnail, int audio, int mnemonicImage) {
        this.latinCharacter = latinCharacter;
        this.hiraganaCharacter = hiraganaCharacter;
        this.mnemonic = mnemonic;
        this.thumbnail = thumbnail;
        this.audio = audio;
        this.mnemonicImage = mnemonicImage;
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

    public void setMnemonicImage(int mnemonicImage) {
        this.mnemonicImage = mnemonicImage;
    }

    /**
     * Method to fetch the associated audio file of a Hiragana object.
     */

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

    /**
     * Method to fetch the associated mnemonic image file of a Hiragana object.
     */

    public void addMnemonicImage() {
        String latinChar = getLatinCharacter();
        switch (latinChar) {
            case "a":
                setMnemonicImage(R.drawable.a_mnemonic);
                break;
            case "i":
                setMnemonicImage(R.drawable.i_mnemonic);
                break;
            case "u":
                setMnemonicImage(R.drawable.u_mnemonic);
                break;
            case "e":
                setMnemonicImage(R.drawable.e_mnemonic);
                break;
            case "o":
                setMnemonicImage(R.drawable.o_mnemonic);
                break;
            case "ka":
                setMnemonicImage(R.drawable.ka_mnemonic);
                break;
            case "ki":
                setMnemonicImage(R.drawable.ki_mnemonic);
                break;
            case "ku":
                setMnemonicImage(R.drawable.ku_mnemonic);
                break;
            case "ke":
                setMnemonicImage(R.drawable.ke_mnemonic);
                break;
            case "ko":
                setMnemonicImage(R.drawable.ko_mnemonic);
                break;
            case "sa":
                setMnemonicImage(R.drawable.sa_mnemonic);
                break;
            case "shi":
                setMnemonicImage(R.drawable.shi_mnemonic);
                break;
            case "su":
                setMnemonicImage(R.drawable.su_mnemonic);
                break;
            case "se":
                setMnemonicImage(R.drawable.se_mnemonic);
                break;
            case "so":
                setMnemonicImage(R.drawable.so_mnemonic);
                break;
            case "ta":
                setMnemonicImage(R.drawable.ta_mnemonic);
                break;
            case "chi":
                setMnemonicImage(R.drawable.chi_mnemonic);
                break;
            case "tsu":
                setMnemonicImage(R.drawable.tsu_mnemonic);
                break;
            case "te":
                setMnemonicImage(R.drawable.te_mnemonic);
                break;
            case "to":
                setMnemonicImage(R.drawable.to_mnemonic);
                break;
            case "na":
                setMnemonicImage(R.drawable.na_mnemonic);
                break;
            case "ni":
                setMnemonicImage(R.drawable.ni_mnemonic);
                break;
            case "nu":
                setMnemonicImage(R.drawable.nu_mnemonic);
                break;
            case "ne":
                setMnemonicImage(R.drawable.ne_mnemonic);
                break;
            case "no":
                setMnemonicImage(R.drawable.no_mnemonic);
                break;
            case "ha":
                setMnemonicImage(R.drawable.ha_mnemonic);
                break;
            case "hi":
                setMnemonicImage(R.drawable.hi_mnemonic);
                break;
            case "fu":
                setMnemonicImage(R.drawable.fu_mnemonic);
                break;
            case "he":
                setMnemonicImage(R.drawable.he_mnemonic);
                break;
            case "ho":
                setMnemonicImage(R.drawable.ho_mnemonic);
                break;
            case "ma":
                setMnemonicImage(R.drawable.ma_mnemonic);
                break;
            case "mi":
                setMnemonicImage(R.drawable.mi_mnemonic);
                break;
            case "mu":
                setMnemonicImage(R.drawable.mu_mnemonic);
                break;
            case "me":
                setMnemonicImage(R.drawable.me_mnemonic);
                break;
            case "mo":
                setMnemonicImage(R.drawable.mo_mnemonic);
                break;
            case "ya":
                setMnemonicImage(R.drawable.ya_mnemonic);
                break;
            case "yu":
                setMnemonicImage(R.drawable.yu_mnemonic);
                break;
            case "yo":
                setMnemonicImage(R.drawable.yo_mnemonic);
                break;
            case "ra":
                setMnemonicImage(R.drawable.ra_mnemonic);
                break;
            case "ri":
                setMnemonicImage(R.drawable.ri_mnemonic);
                break;
            case "ru":
                setMnemonicImage(R.drawable.ru_mnemonic);
                break;
            case "re":
                setMnemonicImage(R.drawable.re_mnemonic);
                break;
            case "ro":
                setMnemonicImage(R.drawable.ro_mnemonic);
                break;
            case "wa":
                setMnemonicImage(R.drawable.wa_mnemonic);
                break;
            case "wo":
                setMnemonicImage(R.drawable.wo_mnemonic);
                break;
            case "n":
                setMnemonicImage(R.drawable.n_mnemonic);
                break;
            default:
                setMnemonicImage(R.drawable.a_mnemonic);
        }
    }

    /**
     * Method to fetch the associated drawable file of a Hiragana object.
     */

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
                setThumbnail(R.drawable.a);
        }
    }

}


