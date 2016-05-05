package com.techeffic.blog.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 文件操作工具类
 * 
 * @author k42jc
 *
 */
public class FileUtil {
	/**
	 * 写入文件到磁盘 2016年3月11日
	 * 
	 * @param filePath
	 *            文件路径 包含到具体文件 如：C:/Users/Administrator/Desktop/sku.txt
	 * @param text
	 *            待写入的文本
	 * @param append
	 *            是否追加/false则覆盖原文本
	 * @return 写入是否成功！
	 *
	 */
	public static boolean write(String filePath, String text, boolean... append) {
		if (null == filePath || null == text || "".equals(filePath))
			throw new RuntimeException("请检查参数是否正确！");
		OutputStream os = null;
		try {
			boolean isAppend = false;
			if (append != null) {
				isAppend = append[0];
			}
			String path = filePath.substring(0, filePath.lastIndexOf("/"));
			File file = new File(path);
			if (!file.exists())
				file.mkdirs();
			file = new File(filePath);
			if (!file.exists())
				file.createNewFile();
			os = new FileOutputStream(file, isAppend);
			IOUtils.write(text, os);
			os.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(os);
		}
		return false;
	}

	/**
	 * 遍历拷贝文件 2016年3月11日
	 * 
	 * @param path
	 *            源目录 适用于：/path/下多个文件夹/每个文件夹里面都有文件
	 * @param targetPath
	 *            指定目录
	 *
	 */
	public static void copy(String path, String targetPath) {
		try {
			recursiveFiles(new File(path), targetPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("all")
	public static void main(String[] args) {

		File file = new File("C:/Users/Administrator/Desktop/0");
		try {
			recursiveFiles(file, "C:/Users/Administrator/Desktop/img");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 递归复制文件到指定目录 2016年3月11日
	 * 
	 * @param file
	 *            需要copy的文件
	 * @param targetPath
	 *            指定目录
	 * @throws IOException
	 *
	 */
	public static void recursiveFiles(File file, String targetPath)
			throws IOException {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					recursiveFiles(f, targetPath);
				} else if (f.isFile()) {
					FileUtils.copyFileToDirectory(f, new File(targetPath));
				}
			}
		} else {
			FileUtils.copyFileToDirectory(file, new File(targetPath));
		}
	}
}
