package com.liang.util;

import java.io.*;

/**
 * 序列化
 *
 * @author xcl
 */
public class SerializeUtil {

    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    public static byte[] serialize(Object obj) {
        byte[] arr = null;
        ObjectOutputStream os = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(obj);
            arr = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(os);
        }
        return arr;
    }

    /**
     * 反序列化
     *
     * @param in
     * @return
     */
    public static Object unserialize(byte[] in) {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(in);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ois);
        }
        return obj;
    }

    public static void serialize(Object obj,String path) {
        ObjectOutputStream os = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            os = new ObjectOutputStream(fos);
            os.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(os);
        }
    }


    //从文件中读取序列化字符串
    public static Object unserializeFile(String path) {
        InputStream in = null;
        ObjectInputStream os = null;
        try {
            in = new FileInputStream(path);
            os = new ObjectInputStream(in);
            Object obj = os.readObject();
            System.out.println(obj);
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(os);
        }
        return null;
    }

    public static void close(Closeable... closeables) {
        if (closeables != null && closeables.length > 0) {
            for (Closeable close : closeables) {
                try {
                    close.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}