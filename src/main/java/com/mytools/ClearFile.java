package com.mytools;

import java.io.*;

/**
 * 清除文件内容
 *
 * @author xcl
 */
public class ClearFile {
    public static void main(String[] args) {
        clearCurrentPath();
    }

    /**
     * 清理当前工作目录下的文件，不包括.jar文件和path.txt文件
     */
    public static void clearCurrentPath() {
        //当前工作目录。可执行jar运行时，获取当前工作目录
        //String path = System.getProperty("user.dir");
        String path = "F:\\test";
        File dir = new File(path);
        //为了安全，禁止直接清除磁盘根目录
        if (dir.getParent() != null) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.equalsIgnoreCase("path.txt")) {
                        return false;
                    }
                    File file = new File(dir.getPath() + File.separator + name);
                    if (file.isFile() && name.indexOf(".") > 0) {
                        String suffix = name.substring(name.lastIndexOf("."));
                        return !".jar".equalsIgnoreCase(suffix);
                    }
                    return true;
                }
            };
            File[] files = dir.listFiles(filter);
            if (files != null && files.length>0) {
                clearFiles(files);
            }
        }
    }

    /**
     * 清理指定文件或目录下的文件，只把文件内容清空，文件保留。
     *
     * @param files 要清理的文件集合
     */
    public static void clearFiles(File[] files) {
        clearFiles(files, null);
    }

    /**
     * 清理指定文件或目录下的文件，只把文件内容清空，文件保留。
     *
     * @param files  要清理的文件集合
     * @param filter 子目录文件过滤器
     */
    public static void clearFiles(File[] files, FilenameFilter filter) {
        if (files == null || files.length == 0) return;
        String path;
        File[] list;
        for (File f : files) {
            if (f.exists()) {
                path = f.getPath();
                if (f.isFile()) {
                    //clearFile(path);//直接清空
                    coverAndClearFile(path);//先覆盖，后清空
                } else {
                    list = f.listFiles(filter);
                    if (list != null && list.length>0) {
                        clearFiles(list);
                    }
                }
            }
        }
    }

    /**
     * 清空文件内容
     * @param filePath 文件路径
     */
    private static void clearFile(String filePath) {
        if (filePath != null) {
            System.out.println("清理：" + filePath);
            FileOutputStream fos=null;
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    //覆盖
                    fos = new FileOutputStream(file, false);
                    fos.write(new byte[0]);//空数组覆盖
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(fos);
            }
        }
    }

    /**
     * 覆盖清空文件内容
     * @param filePath 文件路径
     */
    private static void coverAndClearFile(String filePath) {
        if (filePath != null) {
            System.out.println("清理：" + filePath);
            File file = new File(filePath);
            if (!file.exists() || file.isDirectory()) return;
            RandomAccessFile raf = null;
            FileOutputStream fos = null;
            try {
                raf = new RandomAccessFile(filePath, "rws");
                long length = raf.length();
                int blen = 1024*1024;
                blen = length<blen ? (int)length : blen;
                byte[] bytes = new byte[blen];
                System.out.println("length:"+length);
                raf.write(bytes);
                while (raf.getFilePointer()<length+blen) {
                    //写入空字符，覆盖内容
                    raf.write(bytes);
                }
                fos = new FileOutputStream(filePath,false);
                fos.write(new byte[0]);
                fos.flush();
                System.out.println(String.format("清空[%s]文件内容结束！",filePath));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(raf,fos);
            }
        }
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
