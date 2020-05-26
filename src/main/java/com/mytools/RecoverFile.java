package com.mytools;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class RecoverFile {
    String logPath;
    RandomAccessFile raf;
    private long pos = -1;

    public static void main(String[] args) {
//        String path = System.getProperty("user.dir");
        String path = "H:\\testss\\data";
        new RecoverFile(path+ File.separator+"path.txt").recover();
    }

    public RecoverFile(String logPath) {
        this.logPath = logPath;
    }

    public void recover(){
        String temp;
        String[] pArr;
        File f1, f2;
        while ((temp=readLineByTail())!=null){
            System.out.println("恢复文件！"+temp);
            if((temp=temp.trim()).length()>0){
                pArr = temp.split(">>>");
                if (pArr.length==2) {
                    f1 = new File(pArr[0].trim());
                    f2 = new File(pArr[1].trim());
                    if (!f1.exists() && f2.exists()){
                        boolean b = f2.renameTo(f1);
                        if (!b)
                            System.out.println("恢复文件失败！"+temp);
                    } else{
                        System.out.println(String.format("恢复文件失败！%s %s %s %s %s", temp, pArr[0], (f1.exists() ? "存在" : "不存在"), pArr[1], (f2.exists() ? "存在" : "不存在")));
                    }
                }
            }
        }
        System.out.println("恢复完成！");
    }

    /**
     * 反向读取文件内容。
     * 从文件尾部向头部，按行读取文件内容
     * 2020年5月22日
     *
     * @author xcl
     */
    public String readLineByTail() {
        try {
            if(raf==null){
                raf = new RandomAccessFile(logPath, "r");
                pos = raf.length() - 1;
            }
            if (pos < 0) return null;
            int c;
            while (pos >= 0) {
                raf.seek(pos--);
                /* 换行符：'\n'或10  回车符：'\r'或13
                   windows回车和换行是两个字符，用于换行；但是也可能只有回车符或只有换行符。
                   文件开始位置，没有回车、换行符  */
                if (pos < 0 || (c = raf.read()) == '\r' || c == '\n') {
                    //RandomAccessFile读取文件时，用ISO-8859-1编码
                    String temp = raf.readLine();
                    if (temp!=null){
                        temp = new String(temp.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    }else if(pos>0){
                        temp="";
                    }
                    if (pos > 0) {//未到达文件头部，可继续向前移动指针，判读回车换行符
                        //跳过回车和换行两个字符
                        raf.seek(pos);
                        if ((c = raf.read()) == '\r' || c == '\n') {
                            pos--;
                        }
                    }
                    return temp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        close(raf);
        return null;
    }

    private static void close(Closeable... closeables) {
        if (closeables != null && closeables.length != 0) {
            for (Closeable c : closeables) {
                if (c != null) {
                    try {
                        c.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
