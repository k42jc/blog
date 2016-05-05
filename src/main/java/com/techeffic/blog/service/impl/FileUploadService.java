package com.techeffic.blog.service.impl;

import java.io.File;
import java.rmi.ServerException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.rowset.serial.SerialException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.service.IFileUploadService;
import com.techeffic.blog.util.NameComparator;
import com.techeffic.blog.util.SizeComparator;
import com.techeffic.blog.util.TypeComparator;

/**
 * 文件上传处理service
 * 
 * @author xudong_liao<br/>
 * @date 2015年12月24日<br/>
 */
@SuppressWarnings("all")
@Service
public class FileUploadService implements IFileUploadService {
	Logger logger = Logger.getLogger(FileUploadService.class);
	@Value("#{settings['fileUploadPath']}")
	String fileUploadPath = "";
	@Value("#{settings['imageExtension']}")
	String imageExtension = "";
	@Value("#{settings['flashExtension']}")
	String flashExtension = "";
	@Value("#{settings['mediaExtension']}")
	String mediaExtension = "";
	@Value("#{settings['fileExtension']}")
	String fileExtension = "";
	@Value("#{settings['fileMaxSize']}")
	Long fileMaxSize = 0l;
	@Value("#{settings['fileTypes']}")
	String fileTypes="";
	

	public String fileUpload(WebContext webCtx) throws Exception{
		// 设置保存文件路径
		String savePath = webCtx.getRequest().getSession().getServletContext()
				.getRealPath("/")
				+ "/"+fileUploadPath+"/";
		//文件保存目录URL
		String saveUrl = webCtx.getRequest().getContextPath()+"/"+fileUploadPath+"/";
		
		//定义允许上传的文件扩展名
		Map<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", imageExtension);
		extMap.put("flash", flashExtension);
		extMap.put("media", mediaExtension);
		extMap.put("file", fileExtension);
		
		//最大上传限制
		long maxSize = fileMaxSize;
		
		webCtx.getResponse().setContentType("text/html; charset=UTF-8");

		if(!ServletFileUpload.isMultipartContent(webCtx.getRequest())){
			return WebResponse.getError("请选择文件!");
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			return WebResponse.getError("上传目录不存在!");
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			return WebResponse.getError("上传目录没有写权限!");
		}

		String dirName = webCtx.getRequest().getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			return WebResponse.getError("目录名不正确!");
		}
		//创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = upload.parseRequest(webCtx.getRequest());
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				//检查文件大小
				if(item.getSize() > maxSize){
					return WebResponse.getError("上传文件大小超过限制!");
				}
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
					return WebResponse.getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式!");
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try{
					File uploadedFile = new File(savePath, newFileName);
					item.write(uploadedFile);
				}catch(Exception e){
					return WebResponse.getError("上传文件失败!");
				}

				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				System.out.println(obj.toJSONString());
				return obj.toJSONString();
			}
		}
		return null;
	}


	public String fileManage(WebContext webCtx) throws Exception{
		//根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String rootPath = webCtx.getRequest().getSession().getServletContext().getRealPath("/") +"/"+fileUploadPath+ "/";
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl  = webCtx.getRequest().getContextPath() + "/"+fileUploadPath+"/";
		//图片扩展名
		String[] imageTypes = imageExtension.split(",");

		String dirName = webCtx.getRequest().getParameter("dir");
		if (dirName != null) {
			if(!Arrays.<String>asList(fileTypes.split(",")).contains(dirName)){
				throw new SerialException("无效文件类型！");
			}
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		//根据path参数，设置各路径和URL
		String path = webCtx.getRequest().getParameter("path") != null ? webCtx.getRequest().getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		//排序形式，name or size or type
		String order = webCtx.getRequest().getParameter("order") != null ? webCtx.getRequest().getParameter("order").toLowerCase() : "name";

		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			throw new ServerException("非法操作！");
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			System.out.println("Parameter is not valid.");
			throw new ServerException("参数异常！");
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			throw new ServerException("目录不存在！");
		}

		//遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(imageTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);

		webCtx.getResponse().setContentType("application/json; charset=UTF-8");
		System.out.println(result.toJSONString());
		return result.toJSONString();
	};
	
}


