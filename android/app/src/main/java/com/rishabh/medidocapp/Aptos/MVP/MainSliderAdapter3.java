package com.rishabh.medidocapp.Aptos.MVP;

import com.rishabh.medidocapp.R;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter3 extends SliderAdapter {

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
            case 0:
                viewHolder.bindImageSlide(R.mipmap.eye1);
                break;
            case 1:
                viewHolder.bindImageSlide(R.mipmap.eye2);
                break;
            case 2:
                viewHolder.bindImageSlide(R.mipmap.eye3);
                break;
        }
    }
}