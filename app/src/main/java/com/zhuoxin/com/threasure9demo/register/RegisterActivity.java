package com.zhuoxin.com.threasure9demo.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.zhuoxin.com.threasure9demo.R;
import com.zhuoxin.com.threasure9demo.commons.ActivityUtils;
import com.zhuoxin.com.threasure9demo.commons.RegexUtils;
import com.zhuoxin.com.threasure9demo.custom.AlertDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_Username)
    EditText etUsername;
    @BindView(R.id.et_Password)
    EditText etPassword;
    @BindView(R.id.et_Confirm)
    EditText etConfirm;
    @BindView(R.id.btn_Register)
    Button btnRegister;
    private ActivityUtils activityUtils;
    private String mConfirm;
    private String mPassWord;
    private String mUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        //设置toolbal
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //显示返回箭头
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("注册");

        }
        etUsername.addTextChangedListener(mTextWatcher);
        etPassword.addTextChangedListener(mTextWatcher);
        etConfirm.addTextChangedListener(mTextWatcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private TextWatcher mTextWatcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mUsername = etUsername.getText().toString();
            mPassWord= etPassword.getText().toString();
            mConfirm = etConfirm.getText().toString();
            boolean canRegister = !TextUtils.isEmpty(mUsername)&& !TextUtils.isEmpty(mPassWord) && !TextUtils.isEmpty(mConfirm);
            btnRegister.setEnabled(canRegister);

        }
    };

    @OnClick(R.id.btn_Register)
    public void onViewClicked() {
        if (RegexUtils.verifyUsername(mUsername)!=RegexUtils.VERIFY_SUCCESS){
            AlertDialogFragment.getInstance(getString(R.string.username_error),getString(R.string.username_rules))
                    .show(getSupportFragmentManager(),"username_error");
            return;
        }
        if (RegexUtils.verifyPassword(mPassWord)!=RegexUtils.VERIFY_SUCCESS){
            AlertDialogFragment.getInstance(getString(R.string.password_error),getString(R.string.password_rules))
                    .show(getSupportFragmentManager(),"password_error");
            return;
        }

        if (!mPassWord.equals(mConfirm)){
            AlertDialogFragment.getInstance("密码确认错误","前后输入密码不一致")
                    .show(getSupportFragmentManager(),"password_error");
            return;
        }
        // TODO: 2017/7/31
        activityUtils.showToast("注册成功！");
    }

}
