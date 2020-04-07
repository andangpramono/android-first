package com.example.first.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import com.example.first.model.ResObj;

import java.util.HashMap;

public  interface UserService {

    @FormUrlEncoded
    @POST("post_message")
    Call<ResponseBody> postMessage(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("/Api_android/login")
    Call<POST> loginRequest2(@Field("identity") String identity,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("identity") String identity,
                                    @Field("password") String password);


}