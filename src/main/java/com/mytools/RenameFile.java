package com.mytools;

import java.io.*;
import java.util.Objects;
import java.util.Random;

/**
 * 重命名文件
 * @author xcl
 *
 */
public class RenameFile {
	FileOutputStream fos;
	String logPath;

	public static void main(String[] args) {
		renameCurrentPath();
	}

	public RenameFile(String logPath) {
		this.logPath = logPath;
	}

	public static void renameCurrentPath() {
		//当前工作目录
		//String path = System.getProperty("user.dir");
		String path = "F:\\test";
		String logPath = path+File.separator+"path.txt";
		RenameFile rn = new RenameFile(logPath);
		File dir = new File(path);
		//为了安全，禁止直接操作磁盘根目录
		if(dir.exists() && dir.getParentFile()!=null){
			//当前正在运行的文件
			String runningFilePath = System.getProperty("java.class.path");
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if(Objects.equals(pathname.getPath(), runningFilePath) || Objects.equals(pathname.getPath(), logPath)){
						return false;
					}
					String name = pathname.getName();
					if (pathname.isFile() && name.indexOf(".") > 0) {
						String suffix = name.substring(name.lastIndexOf("."));
						return !".jar".equalsIgnoreCase(suffix);
					}
					return true;
				}
			};

			File[] files = dir.listFiles(filter);
			if (files != null && files.length>0) {
				//重命名文件
				rn.renameFiles(files,null);
			}
		}
		System.out.println("重命名结束！");
	}

	private void renameFiles(File[] files, FileFilter filter){
		if (files==null || files.length==0) return;
		boolean close = false;
		if (fos==null){
			close = true;
			//创建日志文件
			File pathFile = new File(logPath);
			if(!pathFile.exists()){
				try {
					boolean newFile = pathFile.createNewFile();
					if(!newFile) return;
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
			try {
				fos = new FileOutputStream(pathFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		Random ra = new Random();
		int bound=100000;
		int nName;
		File nFile = null;
		for (File file : files) {
			if (file==null || !file.exists()){
				continue;
			}

			if (file.isDirectory()){//文件夹
				renameFiles(file.listFiles(filter),filter);
			}

			long st = System.currentTimeMillis();
			do{
				if((System.currentTimeMillis()-st)>1000*10){
					//System.exit(0);//获取新名称超过10秒，终止程序
					bound *= 10;//随机数文件名重复率高，扩大范围
				}
				try {
					nName = ra.nextInt(bound-bound/10)+bound/10;//随机数
					nFile = new File(file.getParent()+File.separator+nName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}while(nFile==null || nFile.exists());

			boolean b = file.renameTo(nFile);
			if (b){
				writeContent(file.getPath()+" >>> "+nFile.getPath());
			}else{
				System.out.println(String.format("文件重命名失败！原：%s 新：%s",file.getPath(),nFile.getPath()));
			}
		}
		if (close) close(fos);
	}
	
	//写入文件
	public void writeContent(String content){
		if(fos!=null){
			try {
				fos.write("\r\n".getBytes());
				fos.write(content.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Closeable... closeables) {
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
