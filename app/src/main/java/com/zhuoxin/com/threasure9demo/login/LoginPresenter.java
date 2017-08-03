package com.zhuoxin.com.threasure9demo.login;

import com.zhuoxin.com.threasure9demo.User;
import com.zhuoxin.com.threasure9demo.UserPrefs;
import com.zhuoxin.com.threasure9demo.net.NetClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/8/2.
 */

public class LoginPresenter {
    private  LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }
    public void  login( User user){
        //显示进度
        loginView.showProgress();
        NetClient.getInstance().getNetRequest().login(user).enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                loginView.hideProgress();
                if (response.isSuccessful()){
                    UserResult userResult=response.body();
                    if (userResult==null){
                        loginView.showMessage("未知错误！");
                        return;
                    }
                    if (userResult.getErrcode()!=1){
                        loginView.showMessage(userResult.getErrmsg());
                        return;
                    }
                    String headpic=userResult.getHeadpic();
                    int tokenid=userResult.getTokenid();
                    UserPrefs.getInstance().setPhoto(NetClient.BASE_URL+headpic);
                    UserPrefs.getInstance().setTokenid(tokenid);
                loginView.navigateToHomeActivity();
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
loginView.hideProgress();
                loginView.showMessage("请求失败"+t.getMessage());
            }
        });
    }
}
