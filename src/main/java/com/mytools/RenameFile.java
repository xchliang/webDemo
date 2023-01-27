package com.mytools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * 重命名文件
 * 随机数重命名
 * @author xcl
 *
 */
public class RenameFile {
	static String accessPath="testss";
	BufferedWriter out;
	String logPath;
	boolean close=false;

	public static void main(String[] args) {
		renameCurrentPath();
		//readChar();
	}

	private static void readChar() {
		String path = "F:\\test";
		String logPath = path+ File.separator+"path.txt";

		try {
			RandomAccessFile raf = new RandomAccessFile(logPath,"r");
			int c;
			while ((c=raf.read())!=-1) {
				System.out.println(c);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public RenameFile(String logPath) {
		this.logPath = logPath;
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

	public static void renameCurrentPath() {
		//当前工作目录
		String path = System.getProperty("user.dir");
		//String path = "H:\\testss\\jar";
		renamePath(path);
	}
	public static void renamePath(String path) {
		if(!validPath(path)){
			System.out.println("非指定路径，拒绝执行！");
			return;
		}
		String dataPath = path.substring(0, path.indexOf(accessPath) + accessPath.length())
				+ File.separator + "data";
		String logPath = dataPath + File.separator + "path.txt";
		System.out.println("logFile: "+logPath);
		RenameFile rn = new RenameFile(logPath);
		File dir = new File(dataPath);
		//为了安全，禁止直接操作磁盘根目录
		if(dir.exists() && dir.getParentFile()!=null && dir.getParentFile().getParentFile()!=null){
			//当前正在运行的文件
			String runningFilePath = System.getProperty("java.class.path");
			FileFilter filter = new FileFilter() {
				String[] ignoreFile;
				public FileFilter params(String[] ignoreFile){
					this.ignoreFile = ignoreFile;
					return this;
				}
				@Override
				public boolean accept(File pathname) {
					if (ignoreFile != null && ignoreFile.length>0) {
						for (String s : ignoreFile) {
							if(Objects.equals(pathname.getPath(), new File(s).getPath())){
								return false;
							}
						}
					}
					String name = pathname.getName();
					if (pathname.isFile() && name.indexOf(".") > 0) {
						String suffix = name.substring(name.lastIndexOf("."));
						return !".jar".equalsIgnoreCase(suffix);
					}
					return true;
				}
			}.params(new String[]{runningFilePath,logPath});

			File[] files = dir.listFiles(filter);
			if (files != null && files.length>0) {
				//重命名文件
				rn.rename(files,null);
			}
		}
		System.out.println("重命名结束！");
	}

	public void rename(File[] files, FileFilter filter){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		writeContent(String.format("================ %s =====================",time));
		renameFiles(files, filter);
		close(out);
	}

	public void renameFiles(File[] files, FileFilter filter){
		if (files==null || files.length==0) return;
		Random ra = new Random();
		int nName, min=10000, bound=9*min;
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
					min *= 10;
				}
				try {
					nName = ra.nextInt(bound)+min;//随机数
					nFile = new File(file.getParent()+File.separator+nName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}while(nFile==null || nFile.exists());

			boolean b = file.renameTo(nFile);
			String content = file.getPath() + " >>> " + nFile.getPath();
			if (b){
				writeContent(content);
			}else{
				System.out.printf("文件重命名失败！%s", content);
			}
		}

	}
	
	//写入文件
	private void writeContent(String content){
		if (out == null){
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
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathFile, true), StandardCharsets.UTF_8));
				if (pathFile.length()>0){
					out.newLine();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(out !=null){
			try {
			    if (content!=null) {
			        out.write(content);
                }
				out.newLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
