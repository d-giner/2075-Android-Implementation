package com.game.in2075.Retrofit;

import com.game.in2075.Retrofit.JsonClasses.FormReg;

import java.util.*;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Json2075API {

    @POST("authent/reg")
    Call<FormReg> setUserReg(@Body FormReg u);

    @POST("authent/log")
    Call<FormReg> setUserLog(@Body FormReg u);

    @DELETE("authent/del/{username}/")
    Call<Void> setUserDel(@Path("username") String username);
}
