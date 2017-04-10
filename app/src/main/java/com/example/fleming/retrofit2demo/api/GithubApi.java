package com.example.fleming.retrofit2demo.api;

import com.example.fleming.retrofit2demo.entity.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by PandaQ on 2017/3/24.
 * github api 拉取我的个人信息到关于
 */

public interface GithubApi {

    //获取某一用户的信息
    @GET("users/{user}")
    Observable<UserInfo> getMyInfo(@Path("user") String path);
}
