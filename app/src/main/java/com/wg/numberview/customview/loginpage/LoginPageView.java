package com.wg.numberview.customview.loginpage;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wg.numberview.R;
import com.wg.numberview.customview.App;

import java.lang.reflect.Field;

/**
 * 点击获取手机号码--》条件手机号码是否正确
 * 点击登录---》正确的手机号码+验证码+同意了协议
 */
public class LoginPageView extends FrameLayout {

    //手机号码的规则
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
    public static final int SIZE_VERIFY_CODE_DEFAULT = 4;
    private int mColor;
    private int mVerifyCodeSize;
    private CheckBox mIsConfirm;
    private OnLoginPageActionListener mActionListenner = null;
    private LoginKeyboard mLoginKeyboard;
    private EditText mPhoneNumInput;
    private EditText mVerifyCodeInput;
    private TextView mGetVerifyCodeBtn;
    private View mLoginBtn;
    private boolean isPhoneNumOk = false;
    private boolean isAgreementOk = false;
    private  boolean isVerifyCodeOk = false;
    private Handler mHandler;

    /**
     * 关注点：倒计时多长时间:duration
     * 时间间隔：dt
     * 通知UI更新
     * @param context
     */
    private int totalDuration = 60 * 1000;
    private int dTime = 1000;
    private int restTime = totalDuration;
     public void startCountDown(){
         mHandler = App.getHandler();
         mHandler.post(new Runnable() {
            @Override
            public void run() {
                restTime -= dTime;
               if (restTime > 0){
                   mHandler.postDelayed(this,dTime);
                   //直接更新UI
                   mGetVerifyCodeBtn.setText("("+restTime/1000 +")秒");
                   mGetVerifyCodeBtn.setEnabled(false);
               }else {
                   mGetVerifyCodeBtn.setText("获取验证码");
                   mGetVerifyCodeBtn.setEnabled(true);
               }


            }
        });
     }


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
        mIsConfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAgreementOk = b;
                updateAllBtnState();
            }
        });

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
                //获取验证码
                if (mActionListenner != null){
                    //拿到手机号
                    String phoneNum = mPhoneNumInput.getText().toString().trim();
                    Log.d(TAG, " phoneNum ==>"+phoneNum);
                    mActionListenner.onGetVerifyCodeClick(phoneNum);
                    //开始倒计时
                    startCountDown();
                } else {
                    throw new IllegalArgumentException("no action to get verify ");
                }
            }
        });

        mVerifyCodeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isVerifyCodeOk = charSequence.length() == 4;
                updateAllBtnState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPhoneNumInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  //变化的时候去检查手机号码是否符合格式
                Log.d(TAG, "content -- >"+ charSequence);
                String phoneNum = charSequence.toString();
                boolean isMatch = phoneNum.matches(REGEX_MOBILE_EXACT);
                isPhoneNumOk = phoneNum.length() == 11 && isMatch;
                updateAllBtnState();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

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
         mPhoneNumInput.requestFocus();
         mGetVerifyCodeBtn = this.findViewById(R.id.get_verify_code_btn);
         disableCopyAndPaste(mPhoneNumInput);
         disableCopyAndPaste(mVerifyCodeInput);
         mLoginBtn = this.findViewById(R.id.login_btn);
         this.updateAllBtnState();
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

    private void updateAllBtnState(){
        mGetVerifyCodeBtn.setEnabled(isPhoneNumOk);
        mLoginBtn.setEnabled(isPhoneNumOk && isAgreementOk && isVerifyCodeOk);
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
        void onGetVerifyCodeClick(String phoneNum);
        void onOpenAgreementClick();
        void onConfirmClick(String verifyCode,String phoneNum);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void disableCopyAndPaste(final EditText editText) {
        try {
            if (editText == null) {
                return ;
            }

            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            editText.setLongClickable(false);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // setInsertionDisabled when user touches the view
                        setInsertionDisabled(editText);
                    }

                    return false;
                }
            });
            editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInsertionDisabled(EditText editText) {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(editText);

            // if this view supports insertion handles
            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

            // if this view supports selection handles
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
