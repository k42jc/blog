package com.techeffic.blog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class SitePushUtil {
	/*public static boolean push(String site){
		HttpClientUtil.doPostMap("http://data.zz.baidu.com/urls?site=www.techeffic.com&token=7SYdUICQSCH6FHZo");
		return false;
	}*/
	
	/**
     * 推送 Stiemap 到百度
     * @param url
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map<String,Object> push(String siteMapUrl,List<String> urls){
        String urlPath = siteMapUrl;
        try {
            String re = postHttp(urlPath, urls);
            //System.out.println(re);
            return JsonUtil.readValue(re, Map.class);
        } catch (Exception e) {
        	Log4jUtil.error(SitePushUtil.class.getName()+"-链接提交失败");
        }
        return null;
    }
	
	 /** 
     * 百度链接实时推送 
     * @param postUrl 
     * @param params 
     * @return 
     */  
    private static String postHttp(String url,List<String> params){  
        if(null == url || null == params || params.isEmpty()){  
            return null;  
        }  
        String result = "";  
        PrintWriter out = null;  
        BufferedReader in = null;  
        try {  
            URLConnection conn=new URL(url).openConnection();// 建立URL之间的连接  
            conn.setRequestProperty("Host","data.zz.baidu.com");// 设置通用的请求属性  
            conn.setRequestProperty("User-Agent", "curl/7.12.1");  
            conn.setRequestProperty("Content-Length", "83");  
            conn.setRequestProperty("Content-Type", "text/plain");  
            conn.setDoInput(true);// 发送POST请求必须设置如下两行
            conn.setDoOutput(true);  
            out = new PrintWriter(conn.getOutputStream());// 获取conn对应的输出流  
            String param = "";// 发送请求参数 
            for(String s : params){  
                param += s + "\n";
            }  
            out.print(param.trim());  
            out.flush();  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;  
            while((line=in.readLine())!= null){  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally{  
            try{  
                if(out != null) 
                    out.close();  
                if(in!= null) 
                    in.close();    
            }catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
}
