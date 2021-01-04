package com.xjl.dymlanguage.impl;

import com.xjl.dymlanguage.bean.DymLanguageBean;

public interface DymCustomerView {
    //单独处理自定义View或者布局
    public void customerViewRefresh(DymLanguageBean dymLanguageBean,String[] strings);
}
