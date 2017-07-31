package com.zhuoxin.com.threasure9demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zhuoxin.com.threasure9demo.commons.ActivityUtils;
import com.zhuoxin.com.threasure9demo.login.LoginActivity;
import com.zhuoxin.com.threasure9demo.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_Register)
    Button btnRegister;
    @BindView(R.id.btn_Login)
    Button btnLogin;
    private Unbinder mUnbinder;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
    }

    @OnClick({R.id.btn_Register, R.id.btn_Login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_Register:
                activityUtils.startActivity(RegisterActivity.class);
                break;
            case R.id.btn_Login:
                activityUtils.startActivity(LoginActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    mUnbinder.unbind();
    }
}
