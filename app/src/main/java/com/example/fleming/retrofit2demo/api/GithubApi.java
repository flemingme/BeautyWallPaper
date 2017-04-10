package com.example.fleming.retrofit2demo.api;

import com.example.fleming.retrofit2demo.entity.UserInfo;

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
