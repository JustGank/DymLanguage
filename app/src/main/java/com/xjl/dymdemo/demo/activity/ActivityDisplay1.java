package com.xjl.dymdemo.demo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.xjl.dymdemo.demo.R;
import com.xjl.dymdemo.demo.fragment.Display1Fragment;
import com.xjl.dymdemo.demo.widget.CustomTitle;
import com.xjl.dymlanguage.DymLanguageManager;
import com.xjl.dymlanguage.impl.bindTextToMap;

public class ActivityDisplay1 extends AppCompatActivity {

    @bindTextToMap(value = "test_fragment")
    CustomTitle custom_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display1);

        custom_title = findViewById(R.id.custom_title);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new Display1Fragment()).commit();

        DymLanguageManager.INSTANCE.bindAndRefresh(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DymLanguageManager.INSTANCE.unBind(this);
    }
}
