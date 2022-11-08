package com.wg.numberview;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wg.numberview.customview.numberinput.InputNumberView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        InputNumberView inputNumberView = this.findViewById(R.id.input_number_view);
//        inputNumberView.setOnNumberChangeListenter(this);

    }

//    @Override
//    public void onNumberChange(int value) {
//        Log.d(TAG, "current value is == >" + value);
//    }
//
//    @Override
//    public void onNumberMax(int maxValue) {
//        Log.d(TAG, "current maxValue is == >" + maxValue);
//    }
//
//    @Override
//    public void onNumberMin(int minValue) {
//        Log.d(TAG, "current minValue is == >" + minValue);
//    }


}