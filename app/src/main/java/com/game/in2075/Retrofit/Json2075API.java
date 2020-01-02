package com.game.in2075.Retrofit;

import com.game.in2075.Retrofit.JsonClasses.FormReg;
import com.game.in2075.Retrofit.JsonClasses.Game;
import com.game.in2075.Retrofit.JsonClasses.Obj;
import com.game.in2075.Retrofit.JsonClasses.UserTO;
import com.google.gson.JsonElement;

import java.util.*;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Json2075API {

    @POST("authent/reg")
    Call<FormReg> setUserReg(@Body FormReg u);

    @POST("authent/log")
    Call<JsonElement> setUserLog(@Body FormReg u); //JsonElement type because we send FormReg instance and we will receive a UserTO instance as a response.

    @POST("authent/updatePassword")
    Call<Void> updateUserPassword(@Body UserTO u); //Void type because the response that we will receive, do not have any "body or class instance".

    @POST("shop/purchase")
    Call<Void> setPurchase(@Body LinkedList<Obj> li);

    @POST("authent/updatemoney")
    Call<Void> updateUserMoney(@Body UserTO u);

    @DELETE("authent/del/{username}/")
    Call<Void> setUserDel(@Path("username") String username);

    @GET("stats/myGame/{userId}")
    Call<List<Game>> getUserGames(@Path("userId") int userId);

    @GET("stats/myObj/{userId}")
    Call<LinkedList<Obj>> getUserInventory(@Path("userId") int userId);
}
