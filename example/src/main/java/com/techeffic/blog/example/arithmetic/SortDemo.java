package com.techeffic.blog.example.arithmetic;

/**
 * 演示常用排序算法
 * Created by liaoxudong on 2017/5/3.
 */
public class SortDemo {

    public static void main(String[] args) {
        int[] arrays = {9,89,23,53,1,2,5,12,64,24,11};
//        bubbleSort(arrays);
//        selectionSort(arrays);
//        insertSort(arrays);
//        insertedSort(arrays);
        sort(arrays,0,arrays.length-1);
        System.out.println(strUtil(arrays));
    }

    //冒泡排序
    public static int[] bubbleSort(int[] params){
        int[] arrays = params;
        for(int i=0;i<arrays.length-1;i++){
            for(int j=0;j<arrays.length -i - 1;j++){//每一轮都是前后两个元素对比 往后冒
                if(arrays[j]>arrays[j+1]){//每一轮排序将最大一个数放在最后面，之后的对比都只针对在这之前的的序列
                    int val = arrays[j];
                    arrays[j] = arrays[j+1];
                    arrays[j+1] = val;
                }
            }
            System.err.println("第"+(i+1)+"轮冒泡 ===>"+strUtil(arrays));
        }
        System.out.println("冒泡排序完成======>"+strUtil(arrays));
        return arrays;
    }

    //选择排序
    public static int[] selectionSort(int[] params){
        int[] arrays = params;
        int i;
        for(i=0;i<arrays.length-1;i++){
            for(int j=i+1;j<arrays.length;j++){//每一轮都是拿"第一个"元素与后面的对比
                if(arrays[i]>arrays[j]){//每一轮将最小的数放在最前面，之后的对比都只针对在这之后的序列
                    int val = arrays[i];
                    arrays[i]=arrays[j];
                    arrays[j]=val;
                }
            }
            System.err.println("第"+(i+1)+"轮选择 ===>"+strUtil(arrays));
        }
        System.out.println("选择排序完成 ======>"+strUtil(arrays));
        return arrays;
    }

    //插入排序
    public static void insertSort(int[] arrays){
        int i,j,k;
        for(i=1;i<arrays.length;i++){
            k = arrays[i];
            for(j=i-1;j>=0&&k<arrays[j];j--){
                arrays[j+1] = arrays[j];
            }
            arrays[j+1]=k;
            System.err.println("第"+(i+1)+"轮插入 ===>"+strUtil(arrays));
        }
        System.out.println("插入排序完成 ======>"+strUtil(arrays));
    }

    public static void insertedSort(int[] arrays){
        int i,j,k;
        for(i=1;i<arrays.length;i++){
            k = arrays[i];
            for(j=i-1;j>=0&&arrays[j]>k;j--){
                arrays[j+1] = arrays[j];
            }
            arrays[j+1]=k;
        }
        System.out.println("插入排序完成 ======>"+strUtil(arrays));
    }

    /**
     * 归并排序
     * 简介:将两个（或两个以上）有序表合并成一个新的有序表 即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列
     * 时间复杂度为O(nlogn)
     * 稳定排序方式
     * @param nums 待排序数组
     * @return 输出有序数组
     */
    public static int[] sort(int[] nums, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            sort(nums, low, mid);
            // 右边
            sort(nums, mid + 1, high);
            // 左右归并
            merge(nums, low, mid, high);
        }
        return nums;
    }

    public static void merge(int[] nums, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;

        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (nums[i] < nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = nums[i++];
        }

        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = nums[j++];
        }

        // 把新数组中的数覆盖nums数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            nums[k2 + low] = temp[k2];
        }
    }


    public static String strUtil(int[] arrays){
        StringBuilder sb = new StringBuilder("[");
        for(Object o:arrays){
            sb.append(o).append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(",")).append("]");
        return sb.toString();
    }
}
