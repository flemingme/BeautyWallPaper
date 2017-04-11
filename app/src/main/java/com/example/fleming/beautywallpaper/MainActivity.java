package com.example.fleming.beautywallpaper;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.fleming.beautywallpaper.adapter.GirlPagerAdapter;
import com.example.fleming.beautywallpaper.api.ApiManager;
import com.example.fleming.beautywallpaper.entity.GirlData;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "fleming";
    private ViewPager mViewPager;
    private int mPage = 1;
    private int index;
    private GirlPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadGirls(mPage);
        initEvent();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_girl);
    }

    private void initEvent() {
        mAdapter = new GirlPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new GirlPageChangeListener());
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
        ApiManager.getInstence().getGankService()
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
