package com.example.fleming.beautywallpaper;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.fleming.beautywallpaper.adapter.GirlPagerAdapter;
import com.example.fleming.beautywallpaper.api.ApiManager;
import com.example.fleming.beautywallpaper.base.BaseActivity;
import com.example.fleming.beautywallpaper.entity.GirlData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements GirlPagerAdapter.OnItemListener {

    private final String TAG = "fleming";

    private ViewPager mViewPager;
    private int mPage = 1;
    private int index;
    private GirlPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.need_permission));

        initView();
        initEvent();

        loadGirls(mPage);
    }

    @Override
    protected int addLayout() {
        return R.layout.activity_main;
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_girl);
    }

    private void initEvent() {
        mAdapter = new GirlPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new GirlPageChangeListener());
        mAdapter.setOnItemListener(this);
    }

    @Override
    public void showMenu(final Bitmap bitmap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(R.array.menu_text,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                setWallPaper(bitmap);
                                break;
                            case 1:
                                saveWallPaper(bitmap);
                                break;
                        }
                    }
                });
        builder.show();
    }

    private void saveWallPaper(Bitmap bitmap) {
        String fileName = Calendar.getInstance().getTimeInMillis() + ".jpg";
        File directory = new File(Environment.getExternalStorageDirectory(), "girls");
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(directory, fileName);
        Log.d(TAG, "saveWallPaper: " + file.getAbsolutePath());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.exists()) {
            toast(getString(R.string.save_suc, file.getAbsolutePath()));
        }
    }

    private void setWallPaper(Bitmap bitmap) {
        WallpaperManager manager = WallpaperManager.getInstance(this);
        try {
            manager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        toast(getString(R.string.set_suc));
    }

    private class GirlPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(TAG, "onPageScrolled: position=" + position + ", positionOffset="
                    + positionOffset + ", positionOffsetPixels=" + positionOffsetPixels);
            if (position == index - 1) {
                Log.d(TAG, "request next page girl");
                loadGirls(++mPage);
            }
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG, "onPageScrollStateChanged: state=" + state);
        }
    }

    private void loadGirls(int pager) {
        ApiManager.getInstance().getGankService()
                .getMeiziList(pager)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(GirlData value) {
                        Log.d(TAG, "onNext: " + value.getResults().size());
                        index += value.getResults().size();
                        mAdapter.setData(value.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

}
