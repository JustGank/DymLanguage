package com.xjl.dymdemo.demo.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xjl.dymdemo.demo.R;

public class CustomTitle extends RelativeLayout {

    ImageView iv_back;
    TextView tv_title;

    public CustomTitle(Context context) {
        super(context);
        initView(context);
    }

    public CustomTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_custom_title, this);

        tv_title = findViewById(R.id.tv_title);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener((v) -> {
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });

    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

}
