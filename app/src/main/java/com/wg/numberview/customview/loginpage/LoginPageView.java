package com.wg.numberview.customview.loginpage;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wg.numberview.R;
import com.wg.numberview.customview.numberinput.InputNumberView;

/**
 * 点击获取手机号码--》条件手机号码是否正确
 */
public class LoginPageView extends FrameLayout {

    public static final int SIZE_VERIFY_CODE_DEFAULT = 4;
    private int mColor;
    private int mVerifyCodeSize;
    private CheckBox mIsConfirm;
    private OnLoginPageActionListener mActionListenner = null;
    private LoginKeyboard mLoginKeyboard;
    private EditText mPhoneNumInput;
    private EditText mVerifyCodeInput;
    private View mGetVerifyCodeBtn;

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
        disableEdtFocus2Keypad();
        //初始化点击事件
        initEvent();
    }

    private void disableEdtFocus2Keypad() {
        mPhoneNumInput.setShowSoftInputOnFocus(false); //禁止系统自带的键盘显示
        mVerifyCodeInput.setShowSoftInputOnFocus(false);
    }

    private void initEvent() {
        mLoginKeyboard.setOnKeyPressListener(new LoginKeyboard.OnKeyPressListener() {
            @Override
            public void onNumberPress(int number) {
                //数字被点击
                //插入数字
                //获取焦点输入框的值
                EditText focusEdt = getFocusEdt();
                if (focusEdt != null){
                    Editable text = focusEdt.getText();
                    int index = focusEdt.getSelectionEnd();//获取光标的位置
                    text.insert(index,String.valueOf(number));
                }
            }

            @Override
            public void onBackPress() {
                //退格键
                //获取焦点输入框的值
                EditText focusEdt = getFocusEdt();
                if (focusEdt != null){
                    int index = focusEdt.getSelectionEnd();//获取光标的位置
                    Editable editable = focusEdt.getText();
                    if (index > 0){
                        editable.delete(index-1,index);
                    }

                }
            }
        });

        mGetVerifyCodeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:获取验证码
            }
        });

    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.login_page_view,this);
        mIsConfirm = this.findViewById(R.id.report_check_box);
        mVerifyCodeInput = this.findViewById(R.id.verify_code_input_box);
        if (mColor != -1){
            mIsConfirm.setTextColor(mColor);
        }
        mVerifyCodeInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mVerifyCodeSize)});
         mLoginKeyboard = this.findViewById(R.id.number_key_pad);
         mPhoneNumInput = this.findViewById(R.id.phone_num_input_box);
         mGetVerifyCodeBtn = this.findViewById(R.id.get_verify_code_btn);

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

    /**
     * 获取当前有焦点的输入框
     * 使用要注意判空
     * @return
     */
    private EditText getFocusEdt(){
       View view = this.findFocus();
        if (view instanceof EditText){
           return  (EditText)view;
        }
        return null;
    }

    public int getmVerifyCodeSize() {
        return mVerifyCodeSize;
    }

    public void setmVerifyCodeSize(int mVerifyCodeSize) {
        this.mVerifyCodeSize = mVerifyCodeSize;
    }

    public void setOnLoginPageActionListener(OnLoginPageActionListener listener){
        this.mActionListenner = listener;
    }

    public interface OnLoginPageActionListener{
        void onGetVerifyCodeClick(int phoneNum);
        void onOpenAgreementClick();
        void onConfirmClick(String verifyCode,String phoneNum);
    }
}
