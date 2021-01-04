package com.xjl.dymlanguage.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static List<String> arrayToList(String[] strings) {
        ArrayList arrayList = new ArrayList();
        for (String s : strings) {
            arrayList.add(s);
        }
        return arrayList;
    }

    public static List<String> arrayToList(Context context, int strResId) {
        List<String> arrayList=null;
        if(context!=null){
            arrayList=arrayToList(context.getResources().getStringArray(strResId));
        }
        return arrayList;
    }
}
