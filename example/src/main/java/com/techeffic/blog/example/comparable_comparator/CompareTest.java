package com.techeffic.blog.example.comparable_comparator;

import java.util.Arrays;

/**
 * 比较器示例
 * Created by liaoxudong on 2017/6/27.
 */
public class CompareTest {

    public static void main(String[] args) {
        DemoEntity de1 = new DemoEntity(3,"小明");
        DemoEntity de2 = new DemoEntity(1,"小红");
        DemoEntity de3 = new DemoEntity(5,"小王");
        DemoEntity de4 = new DemoEntity(0,"小二");

        DemoEntity[] des = {de1,de2,de3,de4};
        System.err.println("des:"+Arrays.asList(des));
        Arrays.sort(des);
        System.out.println("comparable sorted des:"+ Arrays.asList(des));
        Arrays.sort(des, new CustomerComparator());
        System.err.println("comparator sorted des:"+ Arrays.asList(des));

    }
}
