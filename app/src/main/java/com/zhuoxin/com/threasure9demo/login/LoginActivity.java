package com.zhuoxin.com.threasure9demo.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhuoxin.com.threasure9demo.R;
import com.zhuoxin.com.threasure9demo.commons.ActivityUtils;
import com.zhuoxin.com.threasure9demo.commons.RegexUtils;
import com.zhuoxin.com.threasure9demo.custom.AlertDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_Username)
    EditText etUsername;
    @BindView(R.id.et_Password)
    EditText etPassword;
    @BindView(R.id.tv_forgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.btn_Login)
    Button btnLogin;
    private ActivityUtils activityUtils;
    private String username;
    private String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.login);
        }
        etUsername.addTextChangedListener(mTextWatcher);
        etPassword.addTextChangedListener(mTextWatcher);

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

    private TextWatcher mTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            username = etUsername.getText().toString();
            passWord = etPassword.getText().toString();
            boolean canLogin=!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(passWord);
            btnLogin.setEnabled(canLogin);

        }
    };


    @OnClick(R.id.btn_Login)
    public void onViewClicked() {
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            AlertDialogFragment.getInstance(getString(R.string.username_error), getString(R.string.username_rules))
                    .show(getSupportFragmentManager(), "username_error");
            return;
        }
        if (RegexUtils.verifyPassword(passWord) != RegexUtils.VERIFY_SUCCESS) {
            AlertDialogFragment.getInstance(getString(R.string.password_error), getString(R.string.password_rules))
                    .show(getSupportFragmentManager(), "password_error");
            return;
        }
        activityUtils.showToast("登录成功");
    }
}
