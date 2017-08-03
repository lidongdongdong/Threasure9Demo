package com.zhuoxin.com.threasure9demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zhuoxin.com.threasure9demo.commons.ActivityUtils;
import com.zhuoxin.com.threasure9demo.login.LoginActivity;
import com.zhuoxin.com.threasure9demo.map.HomeActivity;
import com.zhuoxin.com.threasure9demo.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION_MAIN = "action_main";
    @BindView(R.id.btn_Register)
    Button btnRegister;
    @BindView(R.id.btn_Login)
    Button btnLogin;
    private Unbinder mUnbinder;
    private ActivityUtils activityUtils;
BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        finish();
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        //判断是否是第一次登陆，若已登陆过了就直接跳转到HomeActivity
       SharedPreferences user_info = getSharedPreferences("user_info", MODE_PRIVATE);
        if (user_info.getInt("key_tokenid",0)==UserPrefs.getInstance().getTokenid()){
            activityUtils.startActivity(HomeActivity.class);
            finish();
            return;
        }
        IntentFilter intentFilter=new IntentFilter(ACTION_MAIN);
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(broadcastReceiver,intentFilter);
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
