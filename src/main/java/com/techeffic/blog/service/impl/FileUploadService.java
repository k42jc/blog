package com.techeffic.blog.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.service.IFileUploadService;
import com.techeffic.blog.util.FileUtil;
import com.techeffic.blog.util.JsonUtil;
import com.techeffic.blog.util.Log4jUtil;

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
	@Value("#{settings['fileExtension']}")
	String fileExtension = "";

	public WebResponse upload(WebContext webCtx, MultipartFile file) {
		WebResponse response = new WebResponse();
		String type = webCtx.getRequestParameter().getString("type");
		// 设置保存文件路径
		String savePath = webCtx.getRequest().getSession().getServletContext()
				.getRealPath("/")
				+ "/" + fileUploadPath + "/";
		// 文件保存目录URL 用于返回页面以及存放数据库
		String saveUrl = webCtx.getRequest().getContextPath() + "/"
				+ fileUploadPath + "/";
		// 定义允许上传的文件扩展名
		Map<String, String> extMap = new HashMap<String, String>();
		if ("image".equals(type)) {
			extMap.put("image", imageExtension);
		} else if ("file".equals(type)) {
			extMap.put("file", fileExtension);
		}
		if (!ServletFileUpload.isMultipartContent(webCtx.getRequest())) {// 上传仅支持POST与enctype="multipart/form-data"类型
			response.put("message", "请检查文件上传组件配置！");
			return response;
		}
		/*
		 * String dirName = "";
		 * if(file.getContentType().contains("image")){//文件类型存放在image子文件夹下
		 * 图片上传需要压缩/切割 dirName = "image"; }else{ dirName = "file"; }
		 */
		// 创建文件夹
		savePath += type + "/";
		saveUrl += type + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		// 每天上传的文件再存放在指定子目录
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		// 原始文件名
		String fileName = file.getOriginalFilename();
		/*
		 * long fileSize = file.getSize(); // 检查文件大小 if (fileSize > fileMaxSize)
		 * { response.put("error", "上传文件大小超过限制!"); return response; }
		 */
		// 检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		if (!Arrays.<String> asList(extMap.get(type).split(",")).contains(
				fileExt)) {
			response.put("message", "不支持此文件类型!");
			return response;
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss_S");
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(100) + "." + fileExt;
		try {
			File uploadedFile = new File(savePath, newFileName);
			file.transferTo(uploadedFile);
			saveUrl += newFileName;
			response.put("message", "上传成功！");
			response.put("url", saveUrl);
			response.put("path", savePath + newFileName);
			logger.info("文件上传成功，保存路径：" + savePath + newFileName
					+ "<br/>,访问url：" + saveUrl);
			return response;
		} catch (Exception e) {
			response.put("message", "上传操作出现错误!");
			return response;
		}

	}

	@Override
	public String fileManage(WebContext webCtx) {
		// 根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String rootPath = webCtx.getRequest().getSession().getServletContext()
				.getRealPath("/")
				+ "/" + fileUploadPath + "/";
		// 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl = webCtx.getRequest().getContextPath() + "/"
				+ fileUploadPath + "/";
		// 图片扩展名
		String[] imageTypes = imageExtension.split(",");

		String dirName = webCtx.getRequest().getParameter("dir");
		if (dirName != null) {
			/*
			 * if (!Arrays.<String>
			 * asList(fileTypes.split(",")).contains(dirName)) { throw new
			 * SerialException("无效文件类型！"); }
			 */
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		// 根据path参数，设置各路径和URL
		String path = webCtx.getRequest().getParameter("path") != null ? webCtx
				.getRequest().getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0,
					currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0,
					str.lastIndexOf("/") + 1) : "";
		}

		// 排序形式，name or size or type
		String order = webCtx.getRequest().getParameter("order") != null ? webCtx
				.getRequest().getParameter("order").toLowerCase()
				: "name";

		// 不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			throw new RuntimeException("非法操作！");
		}
		// 最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			System.out.println("Parameter is not valid.");
			throw new RuntimeException("参数异常！");
		}
		// 目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			throw new RuntimeException("目录不存在！");
		}

		// 遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String> asList(imageTypes)
							.contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
								.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			// Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			// Collections.sort(fileList, new TypeComparator());
		} else {
			// Collections.sort(fileList, new NameComparator());
		}
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);

		webCtx.getResponse().getResponse()
				.setContentType("application/json; charset=UTF-8");
		// System.out.println(result.toJSONString());
		return result.toString();
	}

	public WebResponse deleteFile(WebContext webCtx) {
		Map<String, Object> file = JsonUtil.readValue(webCtx
				.getRequestParameter().getString("file"), Map.class);
		WebResponse resultMap = new WebResponse();
		try {
			String savePath = webCtx.getRequest().getSession()
					.getServletContext().getRealPath("/")
					+ "/" + fileUploadPath + "/image/";
			// 文件夹
			String fileName = file.get("filename").toString();
			if ((boolean) file.get("is_dir")) {
				FileUtil.deleteFile(new File(savePath + fileName));
			} else if ((boolean) file.get("is_photo")) {// 图片
				String folder = file.get("filename").toString().substring(0, 8);
				FileUtil.deleteFile(new File(savePath + folder + "/" + fileName));
			}
			resultMap.put("success", true);
			Log4jUtil.info(savePath + fileName+"---文件/目录删除成功");
			return resultMap;
		} catch (Exception e) {
			Log4jUtil.error("删除失败",e);
			resultMap.put("success", false);
		}
		return resultMap;
	}

}
