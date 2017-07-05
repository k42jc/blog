package com.techeffic.blog.example.comparable_comparator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * comparable示例
 * 实现comparable接口用于使用内部Arrays.sort()、Collections.sort()排序
 * Created by liaoxudong on 2017/6/27.
 */
public class DemoEntity implements Comparable<DemoEntity> {
    private int id;
    private String name;

    public DemoEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(DemoEntity o) {
        if(this.getId() > o.getId()){
            return 1;
        }else if(this.getId() < o.getId()){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
