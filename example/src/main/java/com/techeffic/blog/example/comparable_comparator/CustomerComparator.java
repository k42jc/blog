package com.techeffic.blog.example.comparable_comparator;

import java.util.Comparator;

/**
 * 自定义比较器，作为参数传入Arrays.sort(param,comparator)
 * Created by liaoxudong on 2017/6/27.
 */
public class CustomerComparator implements Comparator<DemoEntity>{

    @Override
    public int compare(DemoEntity o1, DemoEntity o2) {
        if(o1.getId() < o2.getId()) return 1;
        else if(o1.getId() > o2.getId()) return -1;
        return 0;
    }
}
