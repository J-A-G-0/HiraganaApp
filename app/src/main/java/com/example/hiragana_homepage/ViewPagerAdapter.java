package com.example.hiragana_homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.w3c.dom.Text;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int[] images = {

            R.drawable.a,
            R.drawable.ic_baseline_headphones_24,
            R.drawable.ic_baseline_escalator_warning_24,
            R.drawable.ic_baseline_access_time_filled_24,
            R.drawable.ic_baseline_rocket_launch_24
    };

    int[] headings = {

            R.string.title_welcome,
            R.string.title_audio,
            R.string.title_otherMats,
            R.string.title_time,
            R.string.title_conclusion
    };

    int[] description = {

            R.string.desc_welcome,
            R.string.desc_audio,
            R.string.desc_otherMats,
            R.string.desc_time,
            R.string.desc_conclusion
    };

    public ViewPagerAdapter(Context context){

        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView slideTitleImage = (ImageView) view.findViewById(R.id.titleImage);
        TextView slideHeading = (TextView) view.findViewById(R.id.tvTitle);
        TextView slideDescription = (TextView) view.findViewById(R.id.tvContents);

        slideTitleImage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDescription.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);
    }
}
