package com.example.chapter3.homework;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

public class PlaceholderFragment extends Fragment {


    LottieAnimationView loadingView;
    ListView firendListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View view = inflater.inflate(R.layout.fragment_placeholder,container,false);
        String[] data = getArguments().getStringArray("data");
        loadingView = view.findViewById(R.id.loading_view);
        firendListView = new ListView(getContext());
        firendListView.setAdapter(new ArrayAdapter<>(Objects.requireNonNull(getContext()),android.R.layout.simple_expandable_list_item_1,data));
        firendListView.setVisibility(View.VISIBLE);
        firendListView.setAlpha(0f);
        ViewGroup viewGroup = (ViewGroup)view;
        viewGroup.addView(firendListView);
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                Animator fadeOutAnimator = AnimatorInflater.loadAnimator(getContext(), R.animator.fade_out);
                final Animator fadeInAnimator = AnimatorInflater.loadAnimator(getContext(), R.animator.fade_in);
                fadeOutAnimator.setTarget(loadingView);
                fadeInAnimator.setTarget(firendListView);
                fadeOutAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) { }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        fadeInAnimator.start();
                        ViewGroup container = (ViewGroup)loadingView.getParent();
                        container.removeView(loadingView);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) { }

                    @Override
                    public void onAnimationRepeat(Animator animator) { }
                });
                fadeOutAnimator.start();
            }
        }, 5000);
    }
}
