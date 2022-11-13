package com.wg.numberview.customview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wg.numberview.R;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate: ");
    }

    public void toLoginPage(View view){
       //点击跳转到登录页面
        startActivity(new Intent(this,LoginActivity.class));

    }
}
