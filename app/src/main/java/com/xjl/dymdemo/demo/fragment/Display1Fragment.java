package com.xjl.dymdemo.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xjl.dymdemo.demo.R;
import com.xjl.dymdemo.demo.activity.ActivityDisplay2;
import com.xjl.dymlanguage.DymLanguageManager;
import com.xjl.dymlanguage.impl.bindTextToMap;


public class Display1Fragment extends Fragment {

    View view;
    @bindTextToMap(value = "jump_setting")
    Button jump_setting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display1, null);
        jump_setting = view.findViewById(R.id.jump_setting);
        jump_setting.setOnClickListener((v)->{
            startActivity(new Intent(getActivity(), ActivityDisplay2.class));
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DymLanguageManager.INSTANCE.bindAndRefresh(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DymLanguageManager.INSTANCE.unBind(this);
    }
}
