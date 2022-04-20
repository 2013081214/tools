package xmlfactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class getFeatureList {
    public static void main(String[] args) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //���ڰ�װInputStreamReader,��ߴ������ܡ���ΪBufferedReader�л���ģ���InputStreamReaderû�С�

        try {
            String str = "";
            List<String> nameList = new ArrayList<>();

            fis = new FileInputStream("src/test.xml");// FileInputStream ���ļ�ϵͳ�е�ĳ���ļ��л�ȡ�ֽ�
            isr = new InputStreamReader(fis);// InputStreamReader ���ֽ���ͨ���ַ���������,
            br = new BufferedReader(isr);// ���ַ��������ж�ȡ�ļ��е�����,��װ��һ��new InputStreamReader�Ķ���
            String anchorStart = "<feature name=\""; //ê��
            String anchorEnd = "\"/>"; //ê��

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
            System.out.println("�Ҳ���ָ���ļ�");

        } catch (IOException e) {
            System.out.println("��ȡ�ļ�ʧ��");

        } finally {
            try {
                br.close();
                isr.close();
                fis.close();// �رյ�ʱ����ð����Ⱥ�˳��ر���󿪵��ȹر������ȹ�s,�ٹ�n,����m
            } catch (IOException e) {
                e.printStackTrace();

            }

        }

    }

}	