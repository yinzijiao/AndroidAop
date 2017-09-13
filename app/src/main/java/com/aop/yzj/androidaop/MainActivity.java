package com.aop.yzj.androidaop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aop.yzj.aop_core.annotation.MethodTrace;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDetail();
        getDetail("aaa");
        getDetail("vv", "bb");
    }

    @MethodTrace(true)
    public void getDetail() {
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
