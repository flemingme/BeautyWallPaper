package com.example.fleming.retrofit2demo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.fleming.retrofit2demo.adapter.MeiziPagerAdapter;
import com.example.fleming.retrofit2demo.api.ApiManager;
import com.example.fleming.retrofit2demo.entity.Girl;
import com.example.fleming.retrofit2demo.entity.GirlData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "chen";
    private ViewPager mViewPager;
    private List<String> imgUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadMeizi();
        initView();
        initEvent();
    }

    private void loadMeizi() {
        ApiManager.getInstence().getGankService()
                .getMeiziList(1)
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
                        for (Girl m : value.getResults()) {
                            imgUrls.add(m.getUrl());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        mViewPager.setAdapter(new MeiziPagerAdapter(MainActivity.this, imgUrls));
                    }
                });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_meizi);
    }

    private void initEvent() {

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
