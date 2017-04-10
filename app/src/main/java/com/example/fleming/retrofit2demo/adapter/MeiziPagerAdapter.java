package com.example.fleming.retrofit2demo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fleming.retrofit2demo.R;

import java.util.List;

/**
 * MeiziPagerAdapter
 * Created by fleming on 17-4-10.
 */

public class MeiziPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mImgUrls;

    public MeiziPagerAdapter(Context context, List<String> imgUrls) {
        mContext = context;
        mImgUrls = imgUrls;
    }

    @Override
    public int getCount() {
        return mImgUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_meizi, container, false);
        ImageView ivMezi = (ImageView) view.findViewById(R.id.iv_meizi);
        Glide.with(mContext)
                .load(mImgUrls.get(position))
                .into(ivMezi);
        container.addView(view);
        return view;
    }
}
