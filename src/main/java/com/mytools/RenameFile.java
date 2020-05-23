package com.mytools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 重命名文件
 * @author xcl
 *
 */
public class RenameFile {
	
	public static void main(String[] args) {
		//当前工作目录
		String path = System.getProperty("user.dir");
		File file = new File(path);
		if(file.exists() && file.getParentFile()!=null
				&& file.getParentFile().getParentFile()!=null){
			File pathFile = new File(path+File.separator+"path.txt");
			if(!pathFile.exists()){//判断是否已经存在，不存在则创建
				try {
					boolean newFile = pathFile.createNewFile();
					if(!newFile) return;
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(pathFile,true);
				//当前正在运行的文件
				String runnerFilePath = System.getProperty("java.class.path");
				String[] ignoreFile = {pathFile.getPath(),runnerFilePath};
				renameFiles(file,fos,ignoreFile);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(fos!=null){
					try {
						fos.close();
					} catch (Exception e) {
					}
				}
			}
		}
		
	}
	
	private static void renameFiles(File file,FileOutputStream fos,String[] ignoreFile){
		if(file!=null && file.exists()){
			if(file.isDirectory()){
				File[] listFiles = file.listFiles();
				if(listFiles!=null && listFiles.length>0){
					Random ra = new Random();
					int nName;
					File nFile = null;
					for (File f : listFiles) {
						if(f.isDirectory()){
							renameFiles(f,fos,ignoreFile);
						}
						//是否可修改
						boolean flag = true;
						if(ignoreFile!=null && ignoreFile.length>0){
							for (String exFile : ignoreFile) {
								if(f.getPath().equals(exFile)){
									flag = false;
									break;
								}
							}
						}
						
						if(flag){
							long st = System.currentTimeMillis();
							do{
								if((System.currentTimeMillis()-st)>1000*10){
									System.exit(0);//获取新名称超过10秒，终止程序
								}
								
								try {
									nName = ra.nextInt(90000)+10000;//5位随机数
									nFile = new File(f.getParent()+File.separator+nName);
								} catch (Exception e) {}
							}while(nFile==null || nFile.exists());
						
							writeContent(fos,f.getPath()+" >>> "+nFile.getPath());
							f.renameTo(nFile);
						}
					}
				}
			}
			
		}
	}
	
	//写入文件
	public static void writeContent(FileOutputStream fos,String content){
		if(fos!=null){
			try {
				fos.write("\r\n".getBytes());
				fos.write(content.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
}
