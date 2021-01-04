# 一、简介
[DymLanguage-动态、非关闭、可拓展的多语言框架。](https://github.com/JustGank/DymLanguage)

语言的准确性，对于多语言用户来说，既是对该国文化的尊重，也是对产品严谨态度的体现。

在推荐产品的时候对客户说，我们的产品支持动态的更新系统语言，和实时的增加系统语言，在销售时也是一个吸引客户的亮点。

下面先给大家看下实现效果：


![在这里插入图片描述](https://img-blog.csdnimg.cn/20210104102524710.gif#pic_center)

[图片看不到的可以去csdn。](https://blog.csdn.net/u010451990/article/details/84448827)

# 二、快速使用
## 2.1 框架的初始化

```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DymLanguageManager.INSTANCE
                .setDymCustomerView(new MyDymCustomerView())
                .setLanguageMap(TextMap.class);
    }
}
```
这里面注入了两个参数：

 - `MyDymCustomerView`是处理自定义View的集合管理类，在拓展部分在进行介绍。
 - `TextMap` 是我们独立与系统的语言字典类。
## 2.2 自定义字典 `TextMap`
由于Android系统原生不并支持动态的设置多语言，且TextView通过ResId这个形式设置文字内容的方法都是final的。所以我们的解决方案是独立的一套语言系统，同样的需要一个独立的语言字典，这个类一般的形式可以是：

```java
public class TextMap {
    public static String main_title = "Test Custom View";
    public static String account = "Account";
    public static String account_hint = "Please input your account.";
    public static String[] language_list = {"Set Chinese", "Set English"};
}
```

这个类就是我们独立于系统的语言字典，里面存放我们需要使用到的词条。词条内容建议和应用默认支持的语言词条相同即可。

同样的当我们从服务器端请求到新的字典集合时（如加入俄文），只需要对这个类中的词条进行覆盖即可。

里面的`String[]`是为了适配RecyclerView和多参数的自定义View准备的。这里会在拓展部分介绍。

## 2.3 标记需要实时刷新View
首先我们先介绍下常见的TextView以及其子View的绑定。
可以理解为含有  `setText(String s);  setHint(String s);`  方法的类。

```java
    @bindTextToMap(value = "account")
    protected TextView account;
    @bindTextToMap(value = "account_hint",isHint = true)
    protected EditText edit_account;
```

绑定的意思是将我们的UI控件和字典中的词条进行绑定，绑定是通过注解的方式实现的，这里我们需要设置的是两个参数，第一个是词条的key,也就是`TextMap`中对应词条的变量名称。

如：
```java
public static String account = "Account";
```

中的 **account** 就是变量的名称。

第二参数`isHint`很好理解，当前的字符串的显示是否是在Hint下的。默认为false,需要开启的时候传 true。 



## 2.4 绑定和解绑定
在我们完成View的标记后，就需要将标记告知框架，也就是完成绑定。这里需要将控件的容器传给框架。

此时我们可以使用方法：

```java
    /**
     * 注入绑定的对象的容器，如Activity ，Fragment 等。
     * 此方法，仅绑定，并不将框架当前的语言字典设置到UI控件中。
     */
	DymLanguageManager.INSTANCE.bind(this)；
	
	//绑定并将当前框架的字典内容绑定到UI控件上。
	DymLanguageManager.INSTANCE.bindAndRefresh(this);
```

在我们销毁页面时需要解除绑定此时可以使用：

```java
@Override
    protected void onDestroy() {
        super.onDestroy();
        DymLanguageManager.INSTANCE.unBind(this);
    }
```
## 2.5 动态刷新
在我们完成字典内容的刷新后，仅需要调用框架的refersh方法即可完成所有绑定控件的内容刷新。

代码参考如下：

```java
//将当前的字典内容变更为中文。
TextMap.coverToChinese();
//变更后刷新
runOnUiThread(DymLanguageManager.INSTANCE::refresh);
```

## 2.6 拓展-兼容 自定义View 和 列表
### 2.6.1 代码实例：
这里要说明的是，框架只是帮我们解决了含有 `setText(String s);  setHint(String s);` 方法的控件的自动注入，对于自定义View或者列表是需要自行实现的。

在继承 `DymCustomerView` 接口后实现 `customerViewRefresh` 方法即可。
```java
public class MyDymCustomerView implements DymCustomerView {
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
```

实现后将 MyDymCustomerView 注册给框架即可。

```java
DymLanguageManager.INSTANCE.setDymCustomerView(new MyDymCustomerView())
```

### 2.6.2 自定义View
在实际开发中，框架是不能干预和感知自定义View设置语言的方法的，所以关于自定义View部分为了更好的灵活性，我们将此部分交给开发者，开发者可以通过判断绑定的View是谁的实例的方式，决定如何将内容注入到控件，如`CustomTitle`就是Demo中的自定义标题拦，他是通过`setTitle`方法注入标题内容的，那么我们可以在强转后调用对应方法。

### 2.6.3 多参数自定义View
多参数自定义View的实现也很好理解，我们可以看到

```java
public void customerViewRefresh(DymLanguageBean dymLanguageBean,String[] strings)
```
刷新方法回调时，第二个参数是数组形式的，所以我们只需要在字典声明的时候，将多个参数声明到词条中即可。


### 2.6.4 列表
在实际开发中一个比较常见的场景是，通过列表（或网格）的形式将提示性资源展示给用户，如微信-我。![在这里插入图片描述](https://img-blog.csdnimg.cn/20210104115023893.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTA0NTE5OTA=,size_16,color_FFFFFF,t_70)
这种需求一般来说通过RecyclerView或者ListView(GridView)来实现较为方便后期的维护。所以这里统一了自定义View和列表的实现。

**注：我们这里标记的不再是View,而是Adapter。**

```java

    @bindTextToMap(value = "language_list")
    Display2Adapter display2Adapter;
    
    //在 customerViewRefresh 方法中的实现。
	if (dymLanguageBean.bindView instanceof Display2Adapter) {
            Display2Adapter display2Adapter = (Display2Adapter) dymLanguageBean.bindView;
            display2Adapter.setList(StringUtil.arrayToList(strings));
       }
```

## 2.7 校验
在框架的实际使用中发现由于通过字符串而非变量的方式声明`bindTextToMap`中的value,这容易产生两个问题：**一个是忘记写value**、**一个是value的值写错了**，在解决时发现这类问题非常隐蔽且不易排查。

所以为了解决以上问题加入校验模块，当我们忘写或者写错时，日志会提醒我们出错的控件在哪。

如：

```java
    @bindTextToMap(value = "test_recyclevriew")
    CustomTitle custom_title;
    RecyclerView recyclerView;
    @bindTextToMap(value = "")
    Display2Adapter display2Adapter;
```
这两个控件一个value写错了，一个忘记写了。那么在日志中就会输出：

```java
E/DymLanguageManager: Your ActivityDisplay2.class field custom_title bindTextToMap error!
E/DymLanguageManager: Your ActivityDisplay2.class field display2Adapter bindTextToMap error!
```

告诉我们发生问题的类和其对应的属性。

# 三、实现流程图

当不依赖于系统，就需要我们自己做一套语言系统，通过和后台交互实现动态的切换显示的语言。

下面是在流程上两者的区别。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181124153106730.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTA0NTE5OTA=,size_16,color_FFFFFF,t_70)


# 四、具体实现

首先我们先看注解类：

```java
@Inherited()
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface bindTextToMap {
    String value() default "";

    boolean isHint() default false;
}
```
 1. 目标 -> 类的属性
 2. 类型 -> 运行时注解
 3. 两个注解属性分别为：内容，即绑定的Key的名称（String类型）；第二个为是否是hint的显示类型（boolean类型）。 

## 4.1 DymLanguageManager 
接下来是管理类，也是整个项目的核心和设计的体现，后面我们会介绍重点方法。

管理类中主要存放了两个集合，一个是容器和其内部绑定词条的控件集合。

```java
private HashMap<String, List<DymLanguageBean>> dymHashMap = new HashMap<>();
```
以需要绑定的类名称为Map的Key(**利用类名的单一性**),List为该类中所有需要刷新的控件集合。
 
 一个是字典的集合，key为TextMap变量的名称,value为变量值：

```java
    //缓存字典类里面的字符串数据
    public HashMap<String, String[]> stringHashMap = new HashMap<>();
```

## 4.2 DymLanguageBean
DymLanguageBean缓存了数据在TextMap中的位置和控件，方便后面刷新的时候使用。

```java
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
}
```

下面我们来看该类的几个核心方法:
## 4.3 绑定方法 bind(Object o)
```java
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
```

**bind(Object o)** 将我们要绑定的对象传入。

首先获取到传入对象的所有属性，然后对属性进行遍历得到属性的的注解：

```java
 bindTextToMap b = fields[i].getAnnotation(bindTextToMap.class);
```

 当注解存在的情况下，我们解析注解，分别将需要刷新的view,注解中的key值和是否是hint的值放入到动态语言管理对象中。
 
## 4.4 解绑方法 unBind(Object o)

```java
    public void unBind(Object o) {
        if (dymHashMap.get(o.getClass().getSimpleName()) != null) {
            List<DymLanguageBean> dymLanguageBeans = dymHashMap.get(o.getClass().getSimpleName());
            dymLanguageBeans.clear();
            dymHashMap.remove(o.getClass().getSimpleName());
        }
    }
```

找到Map中对应的的对象进行释放，回收资源。



## 4.5 刷新内容的方法

```java
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

```

刷新方法主要帮我们完成了含有 `setText(String s)` 与 `setHint(String s)` 的类的自动注入，当不含有以上两个方法的控件被绑定后，会调用我们自定义实现的 `DymCustomerView` 进行注入，如何注入需要开发者自行实现。
  
当我们的TextMap刷新的时候，我们只需要调用refresh()方法即可。 


