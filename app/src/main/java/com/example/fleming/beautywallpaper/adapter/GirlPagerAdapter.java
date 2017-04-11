package com.example.fleming.beautywallpaper.adapter;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fleming.beautywallpaper.R;
import com.example.fleming.beautywallpaper.entity.Girl;

import java.io.IOException;
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
        final ImageView ivGirl = (ImageView) view.findViewById(R.id.iv_girl);
        Glide.with(mContext)
                .load(mGirls.get(position).getUrl())
                .asBitmap()
                .into(ivGirl);

        ivGirl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) ivGirl.getDrawable()).getBitmap();
                showSaveDialog(bitmap);
                return true;
            }
        });

        container.addView(view);
        return view;
    }

    private void showSaveDialog(final Bitmap bitmap) {
        new AlertDialog.Builder(mContext)
                .setItems(new CharSequence[]{"设置壁纸"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setWallPaper(bitmap);
                        Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void setWallPaper(Bitmap bitmap) {
        WallpaperManager manager = WallpaperManager.getInstance(mContext);
        try {
            manager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
