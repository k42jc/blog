package com.techeffic.blog.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.Log;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.ISitePushService;
import com.techeffic.blog.util.DateUtil;
import com.techeffic.blog.util.KeyUtil;
import com.techeffic.blog.util.SitePushUtil;
@Service
public class SitePushService extends BaseService implements ISitePushService{
	@Value("#{settings['baiduSitePushUrl']}")
	public String baiduSitePushUrl = "";
	@Override
	public Map<String,Object> scheduleSitePush() {
		//获取默认urls
		ArrayList<String> urls = getDefaultUrls();
		//得到文章的url
		List<Article> resultList = this.getDaoFactory().getArticleMongoDao().findAllIds();
		resultList.forEach(article ->{
			urls.add("http://www.techeffic.com/article/"+article.getId());
		});
		Log log = new Log();
		Date now = new Date();
		log.setId(KeyUtil.generate());
		log.setTime(now);
		log.setTitle("定时提交url到baidu"+DateUtil.toDateStr(now));
		StringBuilder sb = new StringBuilder();
		urls.trimToSize();
		sb.append("本次提交的链接条数为："+urls.size()+",分别为："+urls.toString()+".<br>");
		Map<String,Object> result = SitePushUtil.push(baiduSitePushUrl, urls);
		sb.append("其中成功提交条数为："+result.get("success"));
		log.setInfo(sb.toString());
		this.getDaoFactory().getLogDao().log(log);
		return result;
	}
	private ArrayList<String> getDefaultUrls() {
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("http://www.techeffic.com");
		urls.add("http://www.techeffic.com/about");
		urls.add("http://www.techeffic.com/class");
		urls.add("http://www.techeffic.com/write_md");
		urls.add("http://www.techeffic.com/write_kd");
		urls.add("http://www.techeffic.com/login");
		urls.add("http://www.techeffic.com/list/java");
		urls.add("http://www.techeffic.com/list/db");
		urls.add("http://www.techeffic.com/list/linux");
		urls.add("http://www.techeffic.com/list/js");
		urls.add("http://www.techeffic.com/list/os");
		urls.add("http://www.techeffic.com/list/dp");
		urls.add("http://www.techeffic.com/list/framework");
		urls.add("http://www.techeffic.com/list/log");
		urls.add("http://www.techeffic.com/list/python");
		return urls;
	}
	@Override
	public Map<String, Object> push(String url) {
		return SitePushUtil.push(baiduSitePushUrl, Arrays.asList(url));
	}

}
