package com.zhuoxin.com.threasure9demo.login;

/**
 * Created by Administrator on 2017/8/2.
 */

public interface LoginView {
    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void  navigateToHomeActivity();

}
