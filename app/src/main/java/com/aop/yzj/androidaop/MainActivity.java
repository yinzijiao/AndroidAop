package com.aop.yzj.androidaop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aop.yzj.aop_core.annotation.DoubleClick;
import com.aop.yzj.aop_core.annotation.MethodTrace;
import com.aop.yzj.aop_core.annotation.SingleClick;

public class MainActivity extends AppCompatActivity {

    @Override
    @MethodTrace(true)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDetail();
        getDetail("aaa");
        getDetail("vv", "bb");
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            @DoubleClick
            public void onClick(View v) {
                Log.e("aaaaaaaaaa", "aaaaaaaaaaaaaa");
            }
        });
    }

    @MethodTrace(true)
    void getDetail() {
        int a = 1;
        int b = 2;
        int c = a + b;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @MethodTrace(true)
    public void getDetail(String d) {
        int a = 1;
        int b = 2;
        int c = a * b;
    }

    @MethodTrace(true)
    public String getDetail(String d, String s) {
        return d + s;
    }
}
