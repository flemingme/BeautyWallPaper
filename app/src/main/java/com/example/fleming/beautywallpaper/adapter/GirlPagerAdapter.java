package com.example.fleming.beautywallpaper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fleming.beautywallpaper.R;
import com.example.fleming.beautywallpaper.entity.Girl;

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
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_girl, container, false);
        final ImageView ivGirl = (ImageView) view.findViewById(R.id.iv_girl);
        Glide.with(mContext)
                .load(mGirls.get(position).getUrl())
                .asBitmap()
                .placeholder(R.drawable.ic_girl)
                .error(R.drawable.load_error)
                .into(ivGirl);

        ivGirl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener != null) {
                    Bitmap bitmap = ((BitmapDrawable) ivGirl.getDrawable()).getBitmap();
                    mListener.showMenu(bitmap);
                }
                return true;
            }
        });

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

    private OnItemListener mListener;

    public void setOnItemListener(OnItemListener listener) {
        mListener = listener;
    }

    public interface OnItemListener {
        void showMenu(Bitmap bitmap);
    }

}
