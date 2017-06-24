package com.xiaoq.blog.request;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user on 2017/6/19.
 * packageName：com.xiaoq.blog.request.
 * projectName: Blog.
 * email: xiao.qing@wonhigh.cn
 * desc: TODO
 */
public interface LoginSerives {
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> login(@Field("username") String username,
                             @Field("password") String password);

}
