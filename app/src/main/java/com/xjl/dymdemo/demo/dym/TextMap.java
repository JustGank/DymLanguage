package com.xjl.dymdemo.demo.dym;

import com.xjl.dymlanguage.DymLanguageManager;

public class TextMap {

    public static String main_title = "Test Custom View";
    public static String account = "Account";
    public static String account_hint = "Please input your account.";
    public static String login = "Login";

    public static String test_fragment = "Test Fragment";
    public static String jump_setting = "Jump To Test RecyclerView";

    public static String test_recyclerview = "Test RecyclerView";

    public static String[] language_list = {"Set Chinese", "Set English"};


    public static void coverToChinese() {
        TextMap.main_title = "测试自定义View";
        TextMap.account = "账户";
        TextMap.account_hint = "请输入你的用户名";
        TextMap.login = "登录";

        TextMap.test_fragment = "测试 Fragment";
        TextMap.jump_setting = "跳转至测试RecyclerView";

        TextMap.test_recyclerview = "测试列表";

        TextMap.language_list = new String[]{"设置中文", "设置英文"};

        DymLanguageManager.INSTANCE.setLanguageMap(TextMap.class);
    }

    public static void coverToEnglish() {
        TextMap.main_title = "Test Custom View";
        TextMap.account = "Account";
        TextMap.account_hint = "Please input your account.";
        TextMap.login = "Login";

        TextMap.test_fragment = "Test Fragment";
        TextMap.jump_setting = "Jump To Test RecyclerView";

        TextMap.test_recyclerview = "Test RecyclerView";

        TextMap.language_list = new String[]{"Set Chinese", "Set English"};

        DymLanguageManager.INSTANCE.setLanguageMap(TextMap.class);
    }
}
