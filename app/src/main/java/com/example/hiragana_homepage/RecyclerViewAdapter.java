package com.example.hiragana_homepage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final Context mContext;
    private final ArrayList<Hiragana_character> mData;

    public RecyclerViewAdapter(Context mContext, ArrayList<Hiragana_character> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_hiragana,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //What's going on here then, need to set this to 'current level'?
        SharedPreferencesHandler up = new SharedPreferencesHandler(mContext.getApplicationContext());
        up.setSp();
        //Here we make a temporary string of the current char's latinChar.
        String temp_char = mData.get(position).getLatinCharacter();
        // This latinChar is then searched for in SP, retrieving its associate level as a string.
        // holder.tv_current_level.setText(up.getSp().getString(temp_char, ""));
        String str_current_level = up.getSp().getString(temp_char, "");
        // This string is then used to generate a progress message, and set as text.
        holder.tv_current_level.setText(getProgressMsg(str_current_level));
        //The character's image is set.
        holder.char_thumbnail.setImageResource(mData.get(position).getThumbnail());
        setProgressGradientColour(holder.char_thumbnail, str_current_level);
        //holder.char_thumbnail.setColorFilter(R.color.teal_200);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_current_level;
        ImageView char_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_current_level = (TextView) itemView.findViewById(R.id.tv_current_level);
            char_thumbnail = (ImageView) itemView.findViewById(R.id.imv_hiragana_img);
        }
    }

    // Method to set strings for each character dependent on the user's previous scores.
public String getProgressMsg(String prog_str) {
        String progressMsg = "";
        Integer prog_int = Integer.valueOf(prog_str);
        if(prog_int == 0){
            progressMsg = "Not Studied";
        } else if(prog_int > 0 && prog_int < 4) {
            progressMsg = "Grade C";
        } else if(prog_int > 3 && prog_int < 7) {
            progressMsg = "Grade B";
        } else if(prog_int > 6 && prog_int < 10) {
            progressMsg = "Grade A";
        } else if(prog_int == 10) {
            progressMsg = "Mastered";
        }
        return progressMsg;
}

    // Method to set a different colour for each character's background dependent on score.
public void setProgressGradientColour(ImageView imv, String prog_str){
    Integer prog_int = Integer.valueOf(prog_str);
    int perfectGreenColour = Color.parseColor("#166b11");
    int greatGreenColour = Color.parseColor("#378816");
    int solidGreenColour = Color.parseColor("#569c1e");
    int beginnerGreenColour = Color.parseColor("#a0dc7b");
    int notStudiedGreyColour = Color.parseColor("#a6a6a6");

    ColorFilter perfectGreenFilter = new PorterDuffColorFilter(perfectGreenColour, PorterDuff.Mode.MULTIPLY);
    ColorFilter greatGreenFilter = new PorterDuffColorFilter(greatGreenColour, PorterDuff.Mode.MULTIPLY);
    ColorFilter solidGreenFilter = new PorterDuffColorFilter(solidGreenColour, PorterDuff.Mode.MULTIPLY);
    ColorFilter beginnerGreenFilter = new PorterDuffColorFilter(beginnerGreenColour, PorterDuff.Mode.MULTIPLY);
    ColorFilter notStudiedGreyFilter = new PorterDuffColorFilter(notStudiedGreyColour, PorterDuff.Mode.MULTIPLY);
    //imv.setColorFilter(perfectGreenFilter);

    if(prog_int == 0){
        imv.setColorFilter(notStudiedGreyFilter);
    } else if(prog_int > 0 && prog_int < 4) {
        imv.setColorFilter(beginnerGreenFilter);
    } else if(prog_int > 3 && prog_int < 7) {
        imv.setColorFilter(solidGreenFilter);
    } else if(prog_int > 6 && prog_int < 10) {
        imv.setColorFilter(greatGreenFilter);
    } else if(prog_int == 10) {
        imv.setColorFilter(perfectGreenFilter);
    }

}

}
