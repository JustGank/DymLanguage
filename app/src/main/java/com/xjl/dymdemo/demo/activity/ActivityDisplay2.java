package com.xjl.dymdemo.demo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xjl.dymdemo.demo.R;
import com.xjl.dymdemo.demo.adapter.Display2Adapter;
import com.xjl.dymdemo.demo.dym.TextMap;
import com.xjl.dymdemo.demo.widget.CustomTitle;
import com.xjl.dymlanguage.DymLanguageManager;
import com.xjl.dymlanguage.impl.bindTextToMap;
import com.xjl.dymlanguage.util.StringUtil;

public class ActivityDisplay2 extends AppCompatActivity {

    @bindTextToMap(value = "test_recyclerview")
    CustomTitle custom_title;
    RecyclerView recyclerView;
    @bindTextToMap(value = "language_list")
    Display2Adapter display2Adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);

        custom_title = findViewById(R.id.custom_title);
        display2Adapter = new Display2Adapter(ActivityDisplay2.this, StringUtil.arrayToList(ActivityDisplay2.this, R.array.language_list));
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityDisplay2.this));
        recyclerView.setAdapter(display2Adapter);
        display2Adapter.setOnItemClickListener(onItemClickListener);

        DymLanguageManager.INSTANCE.bindAndRefresh(this);


    }

    Display2Adapter.OnItemClickListener onItemClickListener = (v, position) -> {
        if (position == 0) {
            TextMap.coverToChinese();
        } else if (position == 1) {
            TextMap.coverToEnglish();
        }
        runOnUiThread(DymLanguageManager.INSTANCE::refresh);

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DymLanguageManager.INSTANCE.unBind(this);
    }
}
