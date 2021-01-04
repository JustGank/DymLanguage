package com.xjl.dymlanguage.bean;



public class DymLanguageBean {

    public Object bindView = null;
    public boolean isHint = false;
    public String stringHashMapKey;

    /**
     * @param stringHashMapKey DymLanguageManager#stringHashMap 对应的key 用于查找对应的字符串数组
     * @param bindView 为需要刷新的控件
     * @param isHint
     * */
    public DymLanguageBean(String stringHashMapKey, Object bindView, boolean isHint) {
        this.stringHashMapKey = stringHashMapKey;
        this.bindView = bindView;
        this.isHint = isHint;
    }

    public DymLanguageBean(){}
}
