package com.zhuoxin.com.threasure9demo.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zhuoxin.com.threasure9demo.MainActivity;
import com.zhuoxin.com.threasure9demo.R;
import com.zhuoxin.com.threasure9demo.UserPrefs;
import com.zhuoxin.com.threasure9demo.commons.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
private ActivityUtils activityUtils;
    private ImageView mUserIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
        getSupportActionBar().setDisplayShowTitleEnabled(false);}
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
    actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    navigation.setNavigationItemSelectedListener(this);
        mUserIcon= (ImageView) navigation.getHeaderView(0).findViewById(R.id.iv_usericon);
    mUserIcon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activityUtils.showToast("跳转到个人信息界面");
        }
    });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String photo= UserPrefs.getInstance().getPhoto();
        if (photo!=null){
            Picasso.with(this).load(photo).into(mUserIcon);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_hide:
                activityUtils.showToast("埋藏宝藏");
                break;
            case R.id.menu_my_list:
                activityUtils.showToast("我的列表");
                break;
            case R.id.menu_help:
                activityUtils.showToast("帮助");
                break;
            case R.id.menu_logout:
                UserPrefs.getInstance().clearUser();
                activityUtils.startActivity(MainActivity.class);
                break;
        }
        return false;
    }


}
