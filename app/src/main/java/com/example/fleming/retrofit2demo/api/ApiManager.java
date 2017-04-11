package com.example.fleming.retrofit2demo.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApiManager
 * Created by fleming on 17-4-10.
 */
public class ApiManager {

    private static final int CONNECT_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 10;
    private GithubApi mGithubApi;
    private GankApi mGankApi;
    private static ApiManager sApiManager;
    private static OkHttpClient mClient;

    public static ApiManager getInstence() {
        if (sApiManager == null) {
            synchronized (ApiManager.class) {
                if (sApiManager == null) {
                    sApiManager = new ApiManager();
                }
            }
        }
        mClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return sApiManager;
    }

    /**
     * 封装 github API
     * @return GithubApi
     */
    public GithubApi getGithubService() {
        if (mGithubApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.GITHUB_API_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mGithubApi = retrofit.create(GithubApi.class);
        }
        return mGithubApi;
    }

    /**
     * 封装gank.io API
     * @return GankApi
     */
    public GankApi getGankService() {
        if (mGankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.GANK_IO_URL)
                    .client(mClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mGankApi = retrofit.create(GankApi.class);
        }
        return mGankApi;
    }

}
