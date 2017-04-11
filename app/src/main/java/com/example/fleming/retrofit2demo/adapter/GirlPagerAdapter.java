package com.example.fleming.retrofit2demo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fleming.retrofit2demo.R;
import com.example.fleming.retrofit2demo.entity.Girl;

import java.util.ArrayList;
import java.util.List;

/**
 * GirlPagerAdapter
 * Created by fleming on 17-4-10.
 */

public class GirlPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Girl> mGirls;

    public GirlPagerAdapter(Context context) {
        mContext = context;
        mGirls = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mGirls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_girl, container, false);
        ImageView ivGirl = (ImageView) view.findViewById(R.id.iv_girl);
        Glide.with(mContext)
                .load(mGirls.get(position).getUrl())
                .into(ivGirl);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setData(List<Girl> girls) {
        mGirls.addAll(girls);
        notifyDataSetChanged();
    }
}
