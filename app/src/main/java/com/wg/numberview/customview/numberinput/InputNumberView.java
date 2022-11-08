package com.wg.numberview.customview.numberinput;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.wg.numberview.R;

public class InputNumberView extends RelativeLayout {

    private int mCurrentNnumber = 0;
    private View minusbtn;
    private EditText valueEdt;
    private View plusbtn;
    private OnNumberChangeListenter onNumberChangeListenter = null;
    private int max;
    private int min;
    private int defaultValue;
    private int step;
    private boolean disable;
    private int btnBgRes;

    public InputNumberView(Context context) {
        this(context,null);
    }

    public InputNumberView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InputNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);

        initView(context);
        //处理事件
        setUpEvent();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        //获取相关属性
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.InputNumberView);
        max = a.getInt(R.styleable.InputNumberView_max,0);
        min = a.getInt(R.styleable.InputNumberView_min,0);
        defaultValue = a.getInt(R.styleable.InputNumberView_defaultValue,0);
        this.mCurrentNnumber = defaultValue;
        step = a.getInt(R.styleable.InputNumberView_step,0);
        disable = a.getBoolean(R.styleable.InputNumberView_disable,false);
        btnBgRes = a.getResourceId(R.styleable.InputNumberView_btnBackground,-1);
        Log.d(TAG, "max ==> "+max);
        Log.d(TAG, "min ==> "+min);
        Log.d(TAG, "defaultValue ==> "+defaultValue);
        Log.d(TAG, "step ==> "+step);
        Log.d(TAG, "disable ==> "+disable);
        Log.d(TAG, "btnBgRes ==> "+btnBgRes);
        a.recycle();
    }

    private  void setUpEvent(){
      minusbtn.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
             plusbtn.setEnabled(true);
             mCurrentNnumber -= step;
              if (min != 0 && mCurrentNnumber < min){
                  view.setEnabled(false);
                  mCurrentNnumber = min;
                  Log.d(TAG, "current is min value...");
                  if (onNumberChangeListenter != null){
                      onNumberChangeListenter.onNumberMin(min);
                  }
              }
             updateText();
          }
      });

      plusbtn.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
              minusbtn.setEnabled(true);
             mCurrentNnumber += step;
              if (max != 0 && mCurrentNnumber > max){
                  view.setEnabled(false);
                  mCurrentNnumber = max;
                  Log.d(TAG, "current is max value...");
                  if (onNumberChangeListenter != null){
                      onNumberChangeListenter.onNumberMin(max);
                  }
              }
             updateText();
          }
      });
    }

    private void initView(Context context){
        //布局加载
        //以下代码功能一样
        //LayoutInflater.from(context).inflate(R.layout.input_number_view,this,true);
        //
        //LayoutInflater.from(context).inflate(R.layout.input_number_view,this);
        //
        View view = LayoutInflater.from(context).inflate(R.layout.input_number_view,this,false);
        addView(view);
        //以上代码功能一样，都是把View添加到当前容器里
         minusbtn = this.findViewById(R.id.minus_btn);
         valueEdt = this.findViewById(R.id.value_edt);
         plusbtn = this.findViewById(R.id.plus_btn);
         //初始化控件的值
         updateText();
         minusbtn.setEnabled(!disable);
         plusbtn.setEnabled(!disable);
    }

    public int getNumber(){
        return mCurrentNnumber;
    }

    public void setNumber(int value){
        mCurrentNnumber = value;
        this.updateText();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
        this.mCurrentNnumber = defaultValue;
        this.updateText();
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getBtnBgRes() {
        return btnBgRes;
    }

    public void setBtnBgRes(int btnBgRes) {
        this.btnBgRes = btnBgRes;
    }

    private void updateText(){
        valueEdt.setText(String.valueOf(mCurrentNnumber));
        if (onNumberChangeListenter != null){
            onNumberChangeListenter.onNumberChange(this.mCurrentNnumber);
        }
    }

    public void setOnNumberChangeListenter(OnNumberChangeListenter listenter){
        this.onNumberChangeListenter = listenter;
    }

    public interface OnNumberChangeListenter{
        void onNumberChange(int value);

        void onNumberMax(int maxValue);
        void onNumberMin(int minValue);
    }
}
