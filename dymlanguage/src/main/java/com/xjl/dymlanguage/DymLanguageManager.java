package com.xjl.dymlanguage;

import android.text.TextUtils;
import android.util.Log;

import com.xjl.dymlanguage.bean.DymLanguageBean;
import com.xjl.dymlanguage.impl.DymCustomerView;
import com.xjl.dymlanguage.impl.bindTextToMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public enum DymLanguageManager {
    INSTANCE;

    private final String TAG = DymLanguageManager.class.getSimpleName();

    private String TIP_Value[] = {"Please set content."};
    private final String TIP_Key = "TIP_Key";

    //缓存字典类里面的字符串数据
    public HashMap<String, String[]> stringHashMap = new HashMap<>();
    //页面和页面内控件形成的映射关系 一个页面可以有多个控件
    private HashMap<String, List<DymLanguageBean>> dymHashMap = new HashMap<>();

    //设置语言静态列表
    public DymLanguageManager setLanguageMap(Class c) {
        if (c != null) {
            stringHashMap.clear();
            stringHashMap.put(TIP_Key, TIP_Value);
            Field[] fields = c.getDeclaredFields();
            try {
                for (Field field : fields) {
                    if (field.get(c) instanceof String) {
                        stringHashMap.put(field.getName(), new String[]{(String) field.get(c)});
                    } else if (field.get(c) instanceof String[]) {
                        stringHashMap.put(field.getName(), (String[]) field.get(c));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * 注入绑定的对象的容器，如Activity ，Fragment 等。
     * 此方法，仅绑定，并不将框架当前的语言字典设置到UI控件中。
     */
    public void bind(Object o) {
        if (dymHashMap.get(o.getClass().getSimpleName()) == null) {
            Field[] fields = o.getClass().getDeclaredFields();
            List<DymLanguageBean> tempList = new ArrayList<>();
            for (int i = 0; i < fields.length; i++) {
                bindTextToMap b = fields[i].getAnnotation(bindTextToMap.class);
                if (b != null) {
                    DymLanguageBean dymLanguageBean = new DymLanguageBean();
                    try {
                        Field field = fields[i];
                        field.setAccessible(true);
                        dymLanguageBean.bindView = field.get(o);

                        if (TextUtils.isEmpty(b.value())
                                || stringHashMap.get(b.value()) == null
                                || stringHashMap.get(b.value()).length == 0) {
                            dymLanguageBean.stringHashMapKey = TIP_Key;
                            Log.e(TAG, "Your " + o.getClass().getSimpleName() + ".class field " + field.getName() + " bindTextToMap error!");
                        } else {
                            dymLanguageBean.stringHashMapKey = b.value();
                        }

                        dymLanguageBean.isHint = b.isHint();
                        tempList.add(dymLanguageBean);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            dymHashMap.put(o.getClass().getSimpleName(), tempList);
        }
    }

    public void bindAndRefresh(Object o) {
        bind(o);
        refresh(o);
    }

    //解除当前的绑定对象
    public void unBind(Object o) {
        if (dymHashMap.get(o.getClass().getSimpleName()) != null) {
            List<DymLanguageBean> dymLanguageBeans = dymHashMap.get(o.getClass().getSimpleName());
            dymLanguageBeans.clear();
            dymHashMap.remove(o.getClass().getSimpleName());
        }
    }

    public void refresh() {
        Iterator iterator = dymHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<DymLanguageBean>> entry = (Map.Entry<String, List<DymLanguageBean>>) iterator.next();
            List<DymLanguageBean> beanList = entry.getValue();
            for (int i = 0; i < beanList.size(); i++) {
                refreshText(beanList.get(i));
            }
        }
    }

    public void refresh(Object o) {
        List<DymLanguageBean> list = dymHashMap.get(o.getClass().getSimpleName());
        for (int i = 0; i < list.size(); i++) {
            refreshText(list.get(i));
        }
    }

    protected void refreshText(DymLanguageBean dymLanguageBean) {
        if (dymLanguageBean.isHint) {
            try {
                Method method = dymLanguageBean.bindView.getClass().getMethod("setHint", CharSequence.class);
                method.invoke(dymLanguageBean.bindView, Objects.requireNonNull(stringHashMap.get(dymLanguageBean.stringHashMapKey))[0]);
            } catch (Exception e) {
            }
        } else {
            try {
                Method method = dymLanguageBean.bindView.getClass().getMethod("setText", CharSequence.class);
                method.invoke(dymLanguageBean.bindView, Objects.requireNonNull(stringHashMap.get(dymLanguageBean.stringHashMapKey))[0]);
            } catch (Exception e) {
                customViewRefresh(dymLanguageBean, stringHashMap.get(dymLanguageBean.stringHashMapKey));
            }
        }
    }

    private void customViewRefresh(DymLanguageBean dymLanguageBean, String[] strings) {
        if (dymCustomerView != null) {
            dymCustomerView.customerViewRefresh(dymLanguageBean, strings);
        }
    }

    private DymCustomerView dymCustomerView;

    public DymLanguageManager setDymCustomerView(DymCustomerView dymCustomerView) {
        this.dymCustomerView = dymCustomerView;
        return this;
    }

}
