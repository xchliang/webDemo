package com.mytools;

import java.io.*;

/**
 * 清除文件内容
 *
 * @author xcl
 */
public class ClearFile {
    static String accessPath="testss";
    public static void main(String[] args) {
        clearCurrentPath();
    }

    public static boolean validPath(String path) {
        int beginIndex = path.indexOf(File.separator) + 1;
        if (beginIndex<1){
            return false;
        }
        int endIndex = path.indexOf(File.separator, beginIndex);
        String dir;
        if (endIndex > beginIndex){
            dir = path.substring(beginIndex, endIndex);
        }else {
            dir = path.substring(beginIndex);
        }
        System.out.println(dir);
        return accessPath.equals(dir);
    }

    /**
     * 清理当前工作目录下的文件，不包括.jar文件和path.txt文件
     */
    public static void clearCurrentPath() {
        //当前工作目录。可执行jar运行时，获取当前工作目录
        String path = System.getProperty("user.dir");
//        String path = "H:\\testss\\jar";
        clearPath(path);
    }
    public static void clearPath(String path) {
        if(!validPath(path)){
            System.out.println("非指定路径，拒绝执行！");
            return;
        }
        String dataPath = path.substring(0, path.indexOf(accessPath) + accessPath.length())
                + File.separator + "data";
        File dir = new File(dataPath);
        //为了安全，禁止直接清除磁盘根目录
        if (dir.exists() && dir.getParentFile() != null && dir.getParentFile().getParentFile() != null) {
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
        File[] list;
        for (File f : files) {
            if (f.exists()) {
                if (f.isFile()) {
                    coverAndClear(f);//先覆盖，后清空
                } else {
                    list = f.listFiles(filter);
                    if (list != null && list.length>0) {
                        clearFiles(list, filter);
                    }
                }
            }
        }
    }

    /**
     * 清空文件内容
     * @param file 文件
     */
    public static boolean clearNull(File file) {
        if (file != null && file.exists() && file.isFile()) {
            FileOutputStream fos=null;
            try {
                //覆盖，完全清空
                fos = new FileOutputStream(file, false);
                fos.write(new byte[0]);//空数组覆盖
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                close(fos);
            }
        }
        return true;
    }

    /**
     * 覆盖清空文件内容
     * @param file 文件
     */
    public static void coverAndClear(File file) {
        if (file != null && file.exists() && file.isFile()) {
            coverBlank(file);
            clearNull(file);
        }
    }

    /**
     * 文件内容覆盖为空
     * @param file 文件
     * @return 是否成功
     */
    public static boolean coverBlank(File file) {
        if (file != null && file.exists() && file.isFile()) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(file, "rws");
                long length = raf.length();
                int blen = 1024*1024;
                blen = length<blen ? (int)length : blen;
                byte[] bytes = new byte[blen];
                raf.write(bytes);
                while (raf.getFilePointer()<length+blen) {
                    //写入空字符，覆盖内容
                    raf.write(bytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                close(raf);
            }
        }
        return true;
    }

    private static void close(Closeable... closeables) {
        if (closeables != null && closeables.length != 0) {
            for (Closeable c : closeables) {
                if (c != null) {
                    try {
                        if(c instanceof Flushable){
                            ((Flushable) c).flush();
                        }
                        c.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
