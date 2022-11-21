package com.wg.numberview.customview;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wg.numberview.R;
import com.wg.numberview.customview.loginpage.LoginPageView;

public class LoginActivity extends AppCompatActivity {

    private LoginPageView mLoginPageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES,
//                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
        setContentView(R.layout.activity_login);

        Log.d("LoginActivity", "onCreate: ");
        mLoginPageView = this.findViewById(R.id.login_page_view);
        mLoginPageView.setOnLoginPageActionListener(new LoginPageView.OnLoginPageActionListener() {
            @Override
            public void onGetVerifyCodeClick(String phoneNum) {
                //todo:去获取验证码
            }

            @Override
            public void onOpenAgreementClick() {
                //打开协议页面
            }

            @Override
            public void onConfirmClick(String verifyCode, String phoneNum) {
                //登录
            }
        });
    }
}
