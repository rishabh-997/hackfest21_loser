package com.rishabh.medidocapp.Brainy.MVP;

import com.rishabh.medidocapp.R;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
            case 0:
                viewHolder.bindImageSlide(R.mipmap.brainsider1);
                break;
            case 1:
                viewHolder.bindImageSlide(R.mipmap.brainslider2);
                break;
            case 2:
                viewHolder.bindImageSlide(R.mipmap.brainslider3);
                break;
        }
    }
}