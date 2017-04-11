package com.example.fleming.beautywallpaper.api;

import com.example.fleming.beautywallpaper.entity.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * GithubApi
 * Created by fleming on 17-4-10.
 */

public interface GithubApi {

    @GET("users/{user}")
    Observable<UserInfo> getMyInfo(@Path("user") String path);
}
