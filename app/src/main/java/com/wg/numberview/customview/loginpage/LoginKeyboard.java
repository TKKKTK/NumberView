package com.wg.numberview.customview.loginpage;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.wg.numberview.R;

public class LoginKeyboard extends LinearLayout implements View.OnClickListener {
    public LoginKeyboard(Context context) {
        this(context,null);
    }

    public LoginKeyboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        LayoutInflater.from(context).inflate(R.layout.num_key_pad,this);
//        for (int i = 0;i < getChildCount();i++){
//            LinearLayout linearLayout = (LinearLayout) getChildAt(i);
//            for (int j = 0;j < linearLayout.getChildCount();j++){
//                linearLayout.getChildAt(j).setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        CharSequence text = ((TextView)view).getText();
//                        Log.d(TAG, "Click value is => "+text);
//                    }
//                });
//            }
//        }
        initView();

    }

    private void initView() {
        this.findViewById(R.id.number_1).setOnClickListener(this);
        this.findViewById(R.id.number_2).setOnClickListener(this);
        this.findViewById(R.id.number_3).setOnClickListener(this);
        this.findViewById(R.id.number_4).setOnClickListener(this);
        this.findViewById(R.id.number_5).setOnClickListener(this);
        this.findViewById(R.id.number_6).setOnClickListener(this);
        this.findViewById(R.id.number_7).setOnClickListener(this);
        this.findViewById(R.id.number_8).setOnClickListener(this);
        this.findViewById(R.id.number_9).setOnClickListener(this);
        this.findViewById(R.id.number_0).setOnClickListener(this);
        this.findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewID = view.getId();
        if (view instanceof TextView){
           CharSequence text = ((TextView)view).getText();
            Log.d(TAG, "click value is =>: "+text);
        }
        switch (viewID){
            case R.id.number_1:
                break;
            case R.id.number_2:
                break;
            case R.id.number_3:
                break;
            case R.id.number_4:
                break;
            case R.id.number_5:
                break;
            case R.id.number_6:
                break;
            case R.id.number_7:
                break;
            case R.id.number_8:
                break;
            case R.id.number_9:
                break;
            case R.id.number_0:
                break;
            case R.id.back:
                break;
        }
    }
}
