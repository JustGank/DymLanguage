package com.xjl.dymdemo.demo.dym;

import android.util.Log;

import com.xjl.dymdemo.demo.adapter.Display2Adapter;
import com.xjl.dymdemo.demo.widget.CustomTitle;
import com.xjl.dymlanguage.bean.DymLanguageBean;
import com.xjl.dymlanguage.impl.DymCustomerView;
import com.xjl.dymlanguage.util.StringUtil;

public class MyDymCustomerView implements DymCustomerView {

    private static final String TAG = "MyDymCustomerView";

    @Override
    public void customerViewRefresh(DymLanguageBean dymLanguageBean,String[] strings) {
        if (dymLanguageBean.bindView instanceof CustomTitle) {
            CustomTitle customTitle = (CustomTitle) dymLanguageBean.bindView;
            customTitle.setTitle(strings[0]);
        } else if (dymLanguageBean.bindView instanceof Display2Adapter) {
            Display2Adapter display2Adapter = (Display2Adapter) dymLanguageBean.bindView;
             display2Adapter.setList(StringUtil.arrayToList(strings));
        }
    }
}
