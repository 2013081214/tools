package xmlfactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class getFeatureList {
    public static void main(String[] args) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。

        try {
            String str = "";
            List<String> nameList = new ArrayList<>();

            fis = new FileInputStream("src/test.xml");// FileInputStream 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            String anchorStart = "<feature name=\""; //锚点
            String anchorEnd = "\"/>"; //锚点

            while ((str = br.readLine()) != null) {
                if (str.contains(anchorStart)) {
                    int startIndex = str.indexOf(anchorStart);
                    int endIndex = str.lastIndexOf(anchorEnd);
                    String name = str.substring(startIndex + anchorStart.length(), endIndex);
                    nameList.add(name);
                    System.out.println("name=" + name);
                }
            }
            System.out.println(nameList);


        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");

        } catch (IOException e) {
            System.out.println("读取文件失败");

        } finally {
            try {
                br.close();
                isr.close();
                fis.close();// 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();

            }

        }

    }

}	