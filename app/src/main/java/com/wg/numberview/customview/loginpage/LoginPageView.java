package com.wg.numberview.customview.loginpage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wg.numberview.R;

public class LoginPageView extends FrameLayout {

    public static final int SIZE_VERIFY_CODE_DEFAULT = 4;
    private int mColor;
    private int mVerifyCodeSize;
    private CheckBox mIsConfirm;
    public LoginPageView(@NonNull Context context) {
        this(context,null);
    }

    public LoginPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoginPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化属性
        initAttrs(context, attrs);
        //初始化控件
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.login_page_view,this);
        mIsConfirm = this.findViewById(R.id.report_check_box);
        EditText verifyCodeInput = this.findViewById(R.id.verify_code_input_box);
        if (mColor != -1){
            mIsConfirm.setTextColor(mColor);
        }
        if (mVerifyCodeSize != SIZE_VERIFY_CODE_DEFAULT){
            //TODO:
        }
    }

    private void initAttrs(@NonNull Context context, @Nullable AttributeSet attrs) {
        //获取自定义控件中的相关属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoginPageView);
        mColor =  a.getColor(R.styleable.LoginPageView_mainClolor,-1);
        mVerifyCodeSize = a.getInt(R.styleable.LoginPageView_verifyCodeSize,SIZE_VERIFY_CODE_DEFAULT);
        a.recycle();
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public int getmVerifyCodeSize() {
        return mVerifyCodeSize;
    }

    public void setmVerifyCodeSize(int mVerifyCodeSize) {
        this.mVerifyCodeSize = mVerifyCodeSize;
    }
}
