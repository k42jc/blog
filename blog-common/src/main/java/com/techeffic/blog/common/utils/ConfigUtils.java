package com.techeffic.blog.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 使用枚举单例特性用于加载配置文件
 * Created by liaoxudong on 2017/4/18.
 */
public enum ConfigUtils {
    // 加载propeties属性文件配置
    PROP(".properties"){
        @Override
        protected void init(String fileExt) {
            try {
                configuration  = new Properties();
                logger.info("clear configuration,current size:"+configuration.size());
                String pathName = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                File file = new File(pathName);
                loadFile(file);
            } catch (IOException e) {
                logger.error("加载配置文件失败："+e.getMessage());
                e.printStackTrace();
            }
        }
    },
    // 加载xml配置文件
    XML(".xml"){
        @Override
        protected void init(String fileExt) {
            // TODO 加载xml文件操作
        }
    };
    protected Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
//    private final static String SUFFIX = ".properties";


    protected Properties configuration;
    // 文件类型
    private String fileExt;

    ConfigUtils(String fileExt) {
        this.fileExt = fileExt;
        init(fileExt);
    }

    protected abstract void init(String fileExt);


    /**
     * 读取配置文件
     * @param file 项目根目录文件对应file对象
     * @throws IOException 加载文件失败
     */
    protected final void loadFile(File file) throws IOException{
        if (null == file) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null != files && files.length > 0) {
                for (File tmp : files) {
                    loadFile(tmp);
                }
            }
        } else if (file.isFile()) {
            String name = file.getPath();
            if (name.endsWith(this.fileExt)) {
                // 读取配置
                logger.debug("load prop path: " + name);
                Properties tmp = new Properties();
                tmp.load(new FileInputStream(file));
                Set<Map.Entry<Object, Object>> set = tmp.entrySet();
                if (null == set || set.size() <= 0) {
                    return;
                }
                for (Map.Entry<Object, Object> entry : set) {
                    String key = entry.getKey().toString();
                    String value = null == entry.getValue() ? "" : entry.getValue().toString();
                    logger.debug("put config key: " + key + " value: " + value);
                    configuration.put(key, value);
                }
                tmp = null;
            }
        }
    }

    /**
     * 获取对应key的属性值
     * @param key 属性key
     * @param defaultValue 指定默认值
     * @return 获取值
     */
    public String getProperty(String key, String defaultValue) {
        String value = configuration.getProperty(key, defaultValue);
        if(StringUtils.isEmpty(value)) {
            value = System.getProperty(key, defaultValue);
        }
        if(StringUtils.isEmpty(value)) {
            value = System.getenv(key);
        }
        return null == value ? defaultValue : value;
    }

    /**
     * 获取对应key的值 默认为空字符串
     * @param key 键
     * @return 值
     */
    public String getProperty(String key) {
        String result = getProperty(key, "");
        logger.info("获取配置文件： param:{}, result:{}", new Object[]{key, result});
        return result;
    }

    /**
     * 获取指定key的值并转换为指定类型 提供默认值
     * @param key 键
     * @param defaultValue 默认值
     * @param clazz 指定类型
     * @param <T> 泛型
     * @return 值
     */
    public <T> T getProperty(String key, String defaultValue, Class<T> clazz) {
        String str = getProperty(key, defaultValue);
        return StringUtils.strToObj(str, clazz);
    }

    /**
     * 获取指定key的值并转换为指定类型 默认为初始值
     * @param key 键
     * @param clazz 类型
     * @param <T> 泛型
     * @return 值
     */
    public <T> T getProperty(String key, Class<T> clazz) {
        String str = getProperty(key);
        return StringUtils.strToObj(str, clazz);
    }
}
