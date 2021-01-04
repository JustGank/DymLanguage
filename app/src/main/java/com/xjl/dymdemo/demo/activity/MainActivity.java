package com.xjl.dymdemo.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xjl.dymdemo.demo.R;
import com.xjl.dymdemo.demo.widget.CustomTitle;
import com.xjl.dymlanguage.DymLanguageManager;
import com.xjl.dymlanguage.impl.bindTextToMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @bindTextToMap(value = "main_title")
    protected CustomTitle customTitle;
    @bindTextToMap(value = "account")
    protected TextView account;
    @bindTextToMap(value = "account_hint",isHint = true)
    protected EditText edit_account;
    @bindTextToMap(value = "login")
    protected Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
        DymLanguageManager.INSTANCE.bindAndRefresh(this);

    }

    private void initView() {
        customTitle = (CustomTitle) findViewById(R.id.custom_title);
        account = (TextView) findViewById(R.id.account);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(MainActivity.this);
        edit_account=findViewById(R.id.edit_account);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            startActivity(new Intent(MainActivity.this, ActivityDisplay1.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DymLanguageManager.INSTANCE.unBind(this);
    }
}
