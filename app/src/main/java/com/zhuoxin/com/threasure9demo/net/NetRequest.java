package com.zhuoxin.com.threasure9demo.net;

import com.zhuoxin.com.threasure9demo.User;
import com.zhuoxin.com.threasure9demo.login.UserResult;
import com.zhuoxin.com.threasure9demo.register.RegisterResult;
import com.zhuoxin.com.threasure9demo.treasure.Area;
import com.zhuoxin.com.threasure9demo.treasure.Treasure;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/2.
 */

public interface NetRequest {
    //
    @POST("/Handler/UserHandler.ashx?action=login")
    Call<UserResult> login(@Body User user);
@POST ("/Handler/UserHandler.ashx?action=register")
Call<RegisterResult> register(@Body User user);
    //获取宝藏信息
//获取宝藏信息
    @POST("/Handler/\n" +
            "TreasureHandler.ashx?action=show")
    Call<List<Treasure>> getTreasure(@Body Area area);
}
