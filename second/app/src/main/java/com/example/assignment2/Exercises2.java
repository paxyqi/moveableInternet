package com.example.assignment2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer2);
        final View mainView = findViewById(R.id.relative_view);
        TextView centerText = findViewById(R.id.tv_center);
        int ViewCounts = getAllChildViewCount(mainView);
        centerText.setText(String.format("Views:%d", ViewCounts));
        Button removeBtn = findViewById(R.id.remove_btn);
        removeBtn.setOnClickListener(v -> {
            int count = ((ViewGroup) mainView).getChildCount();
            if(count<=2){
                Toast toast = Toast.makeText(this, "No view to remove", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            View toRemove;
            do {
                toRemove = ((ViewGroup) mainView).getChildAt((int) (Math.random() * count));
            } while (toRemove.getId() == R.id.tv_center || toRemove.getId() == R.id.remove_btn);
            ((ViewGroup) mainView).removeView(toRemove);
            centerText.setText(String.format("Views:%d", getAllChildViewCount(mainView)));
        });

    }

    public int getAllChildViewCount(View view) {
        if(view instanceof ViewGroup){
            int subViews = 0;
            for (int i=0;i<((ViewGroup) view).getChildCount();i++)
                subViews += getAllChildViewCount(((ViewGroup) view).getChildAt(i));
            return 1+subViews;
        }else
            return 1;
    }
}
