package com.zhuoxin.com.threasure9demo.register;

import com.zhuoxin.com.threasure9demo.User;
import com.zhuoxin.com.threasure9demo.UserPrefs;
import com.zhuoxin.com.threasure9demo.net.NetClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/8/3.
 */

public class RegisterPresenter {
    private  RegisterView registerView;

    public RegisterPresenter(RegisterView registerView) {
        this.registerView = registerView;
    }
    public  void  register(User user){
        NetClient.getInstance().getNetRequest().register(user).enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                registerView.hideProgress();
                if (response.isSuccessful()){
                    RegisterResult registerResult=response.body();
                    if (registerResult==null){
                        registerView.showMessage("未知错误");
                        return;
                    }
                    switch (registerResult.getErrcode()){
                        case 1:
                            UserPrefs.getInstance().setTokenid(registerResult.getTokenid());
                            registerView.NavigateToHomeActivity();
                            break;
                        default:
                            registerView.showMessage(registerResult.getErrmsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
registerView.hideProgress();
                registerView.showMessage("注册失败"+t.getMessage());
            }
        });
    }
}
