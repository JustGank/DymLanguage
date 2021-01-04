package com.xjl.dymlanguage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;

public class AutoCreateTextMap {

    /**
     * 注: 1.未考虑到很多<array> <item> 等文本。
     *
     * 2.对应自己的业务进行修改代码即可。
     *
     * 3.不喜勿喷，纯属个人记录使用。有好的优化，可以给一下提醒。
     * */

    public void readLine() throws Exception {
        String s1 = "";
        File file = new File("a.txt");
        FileOutputStream in = new FileOutputStream("b.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        String s3 = "\r\n";
        while ((line = br.readLine()) != null) {
            s1 = line;
            if (s1.trim().length() == 0) {
                continue;
            }
            String s2 = s1.substring(s1.indexOf("name=") + 6, s1.indexOf(">") - 1);
            //如果xml的命名有前缀，可以方便的过滤
            if (!s2.contains("")) {
                continue;
            }
            in.write(s2.getBytes(), 0, s2.length());
            in.write(s3.getBytes());
        }
        in.close();
        br.close();
    }

    public void readLine2() throws Exception {
        String s1 = "";
        File file = new File("a.txt");
        OutputStreamWriter in = new OutputStreamWriter(new FileOutputStream("b.txt") ,"utf-8");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        String s3 = "\r\n";
        while ((line = br.readLine()) != null) {
            s1 = line;
            if (s1.trim().length() == 0 || !s1.contains("/string")) {
                continue;
            }

            String s2 = s1.substring(s1.indexOf("\">") + 2, s1.indexOf("/string") - 1);
            in.write(s2);
            in.write(s3);
        }
        in.close();
        br.close();
    }


}
