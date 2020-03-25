package com.example.assignment2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//import android.support.v7.app.AppCompatActivity;
/**
 * 作业1：
 * Logcat在屏幕旋转的时候 #onStop() #onDestroy()会展示出来
 * 但我们的 mLifecycleDisplay 由于生命周期的原因(Tips:执行#onStop()之后，UI界面我们是看不到的)并没有展示
 * 在原有@see Exercises1 基础上如何补全它，让其跟logcat的展示一样?
 * <p>
 * Tips：思考用比Activity的生命周期要长的来存储？  （比如：application、static变量）
 */
public class Exercises1 extends AppCompatActivity {
    final private String TAG = "Exercises1";
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";

    List<String> lifeCycleList = null;
    private TextView mLifecycleDisplay = null;
    HashMap<String, Object> globalVar = null;

    private void logAndAppend(String lifecycleEvent) {
        Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);
        lifeCycleList.add(lifecycleEvent + "\n");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer1);
        mLifecycleDisplay = findViewById(R.id.tv_loglifecycle);
        globalVar= ((MainApplication) getApplication()).getGlobalVar();
        if (globalVar.get(TAG) == null) {
            lifeCycleList = new ArrayList<String>();
        } else {
            lifeCycleList = (List<String>) globalVar.get(TAG);
        }
        logAndAppend(ON_CREATE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logAndAppend(ON_RESTART);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logAndAppend(ON_START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logAndAppend(ON_RESUME);
        mLifecycleDisplay.setText("Lifecycle callbacks:\n");
        lifeCycleList.forEach(item -> mLifecycleDisplay.append(item));
    }

    @Override
    protected void onPause() {
        super.onPause();
        logAndAppend(ON_PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        logAndAppend(ON_STOP);
    }

    @Override
    protected void onDestroy() {
        if(globalVar.get(TAG)==null)
            globalVar.put(TAG, lifeCycleList);
        else
            globalVar.replace(TAG, lifeCycleList);
        super.onDestroy();
        logAndAppend(ON_DESTROY);
    }

    public void resetLifecycleDisplay(View view) {
        mLifecycleDisplay.setText("Lifecycle callbacks:\n");
        lifeCycleList.clear();
        if (globalVar.get(TAG) == null)
            globalVar.put(TAG, lifeCycleList);
        else
            globalVar.replace(TAG, lifeCycleList);
    }

    public void back(View v) {
        finish();
    }
}
