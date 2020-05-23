package com.liang.demo;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 文件操作
 * 2020年5月22日
 *
 * @author xcl
 */
public class FileDemo {

    public static void main(String[] args) {
        String filePath = "F:\\test\\1.txt";
        readByTail(filePath);//从后往前按行读取
        deleteContent(filePath);//删除文件内容
        readByte(filePath);//读取字节
        clearFile(filePath);//清理文件内容
        readByte(filePath);//读取字节
    }

    /**
     * 按字节读取文件并打印
     *
     * @param filePath 文件路径
     */
    public static void readByte(String filePath) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(filePath, "r");
            int read;
            //单个字节读取文件
            while ((read = raf.read()) != -1) {
                System.out.println(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(raf);
        }
    }


    /**
     * 反向读取文件内容。
     * 从文件尾部向头部，按行读取文件内容
     * 2020年5月22日
     *
     * @author xcl
     */
    public static void readByTail(String filePath) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(filePath, "r");
            long pos = raf.length() - 1;
            if (pos < 0) return;
            int c;
            String temp;
            while (pos >= 0) {
                raf.seek(pos);
                /* 换行符：'\n'或10  回车符：'\r'或13
                   windows回车和换行是两个字符，用于换行；但是也可能只有回车符或只有换行符。
                   文件开始位置，没有回车、换行符  */
                if (pos == 0 || (c = raf.read()) == '\r' || c == '\n') {
                    //RandomAccessFile读取文件时，用ISO-8859-1编码
                    temp = new String(raf.readLine().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    System.out.println(temp);
                    if (pos > 0) {//未到达文件头部，可继续向前移动指针，判读回车换行符
                        //跳过回车和换行两个字符
                        raf.seek(pos - 1);
                        if ((c = raf.read()) == '\r' || c == '\n') {
                            pos--;
                        }
                    }
                }
                pos--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(raf);
        }
    }

    /**
     * <ol>
     * <li>删除文件内容，用空字符覆盖文本内容，不是真正清空内容</li>
     * <li>RandomAccessFile在文件指定位置写入指定长度的数组，可覆盖原有内容，达到删除的效果，但会留有空字符。</li>
     * <li>注意：（1）、写入的是空字符（Null），ascii码为0，而不是空格，空格ascii码为32</li>
     * <li>（2）、在中间写入数据，只会覆盖原有内容；只有在文件尾部写入数据，文件长度才会改变。</li>
     * </ol>
     *
     * @param filePath 文件路径
     */
    public static void deleteContent(String filePath) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(filePath, "rw");
            long pos = 0, length = raf.length();
            byte[] bytes = new byte[128];
            while (pos < length) {
                raf.seek(pos);
                //写入空字符数组，覆盖原来位置内容,指针位置往后的bytes.length个字符会替换为bytes.length个空字符
                raf.write(bytes);
                pos += bytes.length;
            }
            System.out.println("删除文件内容结束！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(raf);
        }
    }

    /**
     * 清空文件内容
     *
     * @param filePath 文件路径
     */
    public static void clearFile(String filePath) {
        System.out.println("清空文件内容！");
        FileWriter fileWriter=null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                fileWriter = new FileWriter(file);
                fileWriter.write("");
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fileWriter);
        }
    }

    public static void close(Closeable... closeables) {
        if (closeables == null || closeables.length == 0) return;
        for (Closeable c : closeables) {
            if (c == null) continue;
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
