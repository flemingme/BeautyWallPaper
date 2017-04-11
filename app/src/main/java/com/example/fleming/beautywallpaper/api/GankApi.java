package com.example.fleming.beautywallpaper.api;

import com.example.fleming.beautywallpaper.entity.GirlData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * GankApi
 * Created by fleming on 17-4-10.
 */

public interface GankApi {

    @GET("/api/data/福利/10/{page}")
    Observable<GirlData> getMeiziList(@Path("page") int page);
}
